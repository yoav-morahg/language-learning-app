package com.yoavmorahg.learner_app.service;

import com.yoavmorahg.learner_app.Exception.AudioDataNotFoundException;
import com.yoavmorahg.learner_app.Exception.InvalidDataException;
import com.yoavmorahg.learner_app.Exception.ResourceExistsException;
import com.yoavmorahg.learner_app.Exception.ResourceNotFoundException;
import com.yoavmorahg.learner_app.entity.AudioData;
import com.yoavmorahg.learner_app.entity.VocabCollection;
import com.yoavmorahg.learner_app.entity.VocabItem;
import com.yoavmorahg.learner_app.entity.VocabItemDto;
import com.yoavmorahg.learner_app.repository.VocabItemRepository;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class DataLoaderService {

    private final static String AUDIO_DATA_PATH = "data/audio/";
    private final static String VOCAB_DATA_PATH = "data/vocab/";

    private final AudioDataService audioDataService;
    private final VocabItemRepository vocabItemRepository;
    private final VocabService vocabService;

    public DataLoaderService(AudioDataService audioDataService,
                             VocabService vocabService, VocabItemRepository vocabItemRepositor)  {
        this.audioDataService = audioDataService;
        this.vocabService = vocabService;
        this.vocabItemRepository = vocabItemRepositor;

    }

    public static File readResourceAsFile (String path)
            throws IOException {

        ClassPathResource resource = new ClassPathResource(path);
        return resource.getFile();
    }

    //TODO stats
    public String createCollections() {
        List<String> collectionName = InitializationService.getCollectionsToCreate();
        collectionName.forEach( name -> {
            try {
                vocabService.createCollection(name);
            } catch (InvalidDataException e) {
//                throw new RuntimeException(e);
                System.out.println(e.getMessage());
            }
        });
        return "success";
    }

    public String loadVocabFiles() {
        List<String> filenames = InitializationService.getVocabFilesToLoad();
        StringBuilder builder = new StringBuilder();
        filenames.stream()
                .forEach( filename -> {
                    String path = VOCAB_DATA_PATH + filename;

                    int lastDot = filename.lastIndexOf(".");
                    String collection = filename.substring(0, lastDot);
                    try {
                        String result = loadFromFile(path, collection);
                        builder.append(filename);
                        builder.append(": ");
                        builder.append(result);
                        builder.append(System.lineSeparator());
                    } catch (ResourceNotFoundException | IOException ex) {
                        System.out.println(ex.getMessage());
                        builder.append(filename);
                        builder.append(" FAILED");
                        builder.append(System.lineSeparator());
                    }
                });
        return builder.toString();
    }
    //TODO stats
    public String loadAudioFiles()  throws IOException, InvalidDataException {
        List<String> filenames = InitializationService.getAudioDirectoriesToLoad();

       List<File> dirs = filenames.stream()
                .map ( path -> {
                    try {
                         File newFile = readResourceAsFile(AUDIO_DATA_PATH + path);
                         return newFile;
                    } catch (IOException e) {
                        return null;
                    }

                })
                .filter(
                        file -> file.isDirectory())
                .collect(Collectors.toList());

       dirs.stream()
               .forEach(dir -> {
                   try {
                       loadAudioFilesInDir(dir.getAbsolutePath());
                   } catch (IOException ex) {
                       System.out.println(ex.getMessage());

                   } catch (InvalidDataException ex) {
                       //TODO handle better
                       System.out.println(ex);
                   }
               });


        return "success";
    }

    //TODO stats
    public String loadAudioFilesInDir(String path) throws IOException, InvalidDataException {
        if (path == null) {
            throw new InvalidDataException("Missing directory path parameter.");
        }
        StringBuilder builder = new StringBuilder();
        try (Stream<Path> stream = Files.list(Paths.get(path))) {
            stream
                    .filter(dirPath -> !Files.isDirectory(dirPath))
                    .forEach(dirPath -> {
                        try {
                            File file = dirPath.toFile();
                            if (file.getName().endsWith(".mp3")) {
                                audioDataService.loadAudioFromFile(file);
                                builder.append(file);
                                builder.append(System.lineSeparator());
                            }
                        } catch (IOException ex) {
                            System.out.println(ex.getMessage());
                        }
                    });
        }
        return  builder.toString();


    }

    public AudioData loadAudioDataFromFile(String path) throws InvalidDataException, IOException {
        if (path == null) {
            throw new InvalidDataException("Missing path parameter.");
        }
        File audioFile = new File(path);
        return audioDataService.loadAudioFromFile(audioFile);
    }

    public String loadVocabFromCsvFile(@RequestParam String path, @RequestParam(required = false) String collection)
        throws IOException {
        try {
            return loadFromFile(path, collection);
        } catch (ResourceNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }

    public VocabItem loadNewVocabItem(VocabItemDto newItemDto,
                                      String collectionName) throws ResourceExistsException {

        AudioData audioData = null;

        checkIfExists(newItemDto.getSideA());

        try {
            audioData = audioDataService.getAudioDataForTerm(newItemDto.getSideB().toLowerCase());
        } catch (AudioDataNotFoundException e) {
            // Does not yet exist so create
            audioData =audioDataService.createAudioDataForTerm(newItemDto.getSideB());
        }

        VocabItem newItem = new VocabItem(newItemDto.getSideA(), newItemDto.getSideB());
        newItem.setAudioData(audioData);
        if (collectionName != null) {
            try {
                VocabCollection collection = vocabService.getCollectionByName(collectionName);
                newItem.getCollections().add(collection);
            } catch (ResourceNotFoundException ex) {
                System.out.println("Unable to find collection.");
            }
        }
        return vocabItemRepository.save(newItem);
    }

    public String loadFromFile(String filepath, String collection) throws ResourceNotFoundException, IOException {


        String resultMessage = "Loaded: %d  Skipped: %d Failed: ";

        StringBuilder loadedLines = new StringBuilder();
        StringBuilder skippedLines = new StringBuilder();
        StringBuilder failedLines = new StringBuilder();

        File input = readResourceAsFile(filepath);
        int loaded = 0;
        int skipped = 0;
        int failed = 0;
        try (Scanner reader = new Scanner(input)) {
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                if (line != "") {
                    String[] terms = line.split(",");
                    if (terms.length == 2) {
                        try {
                            VocabItemDto dto = new VocabItemDto();
                            dto.setSideA(terms[0]);
                            dto.setSideB(terms[1]);
                            checkIfExists(dto.getSideA());


                            VocabItem vocabItem = loadNewVocabItem(dto, collection);
                            if (vocabItem != null && vocabItem.getId() != null && vocabItem.getId() > 0) {
                                loaded++;
                                System.out.println("Saved: " + vocabItem);
                                loadedLines.append(line);
                                loadedLines.append(System.lineSeparator());
                            } else {
                                skipped++;
                                skippedLines.append(line.substring(0, line.length() - 1));
                                skippedLines.append(System.lineSeparator());
                            }

                        } catch (ResourceExistsException e) {
                            skipped++;
                            skippedLines.append(line.substring(0, line.length() - 1));
                            skippedLines.append(System.lineSeparator());
                        } catch (Exception ex) {
                            System.out.println("Error loading term '" + terms[0] + "': " + ex.getMessage());
                            failed++;
                            failedLines.append(line.substring(0, line.length() - 1));
                            failedLines.append(System.lineSeparator());
                        }
                    }
                }
            }
            String stats = String.format(resultMessage, loaded, skipped, failed);
            return createResultMessage(stats, loadedLines.toString(),
                    skippedLines.toString(), failedLines.toString());
        } catch (FileNotFoundException ex) {
            throw new ResourceNotFoundException(ex.getMessage());
        }
    }

    private String createResultMessage(String resultMessage, String loadedLines,
                                       String skippedLines, String failedLines) {
        StringBuilder builder = new StringBuilder();
        builder.append(resultMessage);
        builder.append("\n\n\n");
        builder.append("LOADED:");
        builder.append("\n\n");
        builder.append(loadedLines);
        builder.append("\n\n");
        builder.append("SKIPPED:");
        builder.append("\n\n");
        builder.append(skippedLines);
        builder.append("\n\n");
        builder.append("FAILED:");
        builder.append("\n\n");
        builder.append(failedLines);
        builder.append("\n");
        return builder.toString();
    }

    private void checkIfExists(String term) throws ResourceExistsException {
        Optional<VocabItem> item = vocabItemRepository.findBySideA(term);
        if (item.isPresent()) {
            throw new ResourceExistsException();
        }
    }

}
