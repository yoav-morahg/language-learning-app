package com.yoavmorahg.learner_app.service;

import com.yoavmorahg.learner_app.Exception.AudioDataNotFoundException;
import com.yoavmorahg.learner_app.api.narakeet.NarakeetService;
import com.yoavmorahg.learner_app.entity.AudioData;
import com.yoavmorahg.learner_app.repository.AudioDataRepository;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class AudioDataService {

    private final String datePattern = "yyyy-MM-dd-HHmmss.S";
    private final DateTimeFormatter dateTimestampFormatter = DateTimeFormatter.ofPattern(datePattern);

    private final NarakeetService narakeetSvc;

    private final AudioDataRepository audioDataRepository;

    public AudioDataService(NarakeetService narakeetService,
                            AudioDataRepository audioDataRepository) {
        this.narakeetSvc = narakeetService;
        this.audioDataRepository = audioDataRepository;
    }

    public AudioData createAudioDataForTerm(String term) {

        byte[] data = narakeetSvc.getAudioInfo(term);

        AudioData audioData = createAudioDataForTerm(term, data);

        return audioDataRepository.save(audioData);
    }

    public String getKey() {
        return narakeetSvc.getKey();
    }

    public AudioData createAudioDataForTerm(String term, byte[] data) {
        AudioData audioData = new AudioData();
        audioData.setData(data);
        audioData.setPtTerm(term.toLowerCase());
        audioData.setFilename(term.replace(" ", "-") + ".mp3");

        return audioDataRepository.save(audioData);
    }

    public AudioData createAudioDataForTerm(String term, String filename, byte[] data) {
        AudioData audioData = new AudioData();
        audioData.setData(data);
        audioData.setPtTerm(term.toLowerCase());
        audioData.setFilename(filename);
        return audioData;
    }

    public AudioData getAudioDataForTerm(String term) throws AudioDataNotFoundException {
        Optional<AudioData> audioData = audioDataRepository.findByPtTerm(term.toLowerCase());
        if (audioData.isPresent()) {
            try {
                FileOutputStream output = new FileOutputStream(new File(String.format("/Users/yoavmorahg/Documents/pronounciations/%s",
                        audioData.get().getFilename())));
                IOUtils.write(audioData.get().getData(), output);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return audioData.orElseThrow(AudioDataNotFoundException::new);

    }

    public Optional<AudioData> findById(Long id) {
        return audioDataRepository.findById(id);
    }

    public String loadAudioFiles(String dirPath) throws IOException {
        StringBuilder builder = new StringBuilder();
        try (Stream<Path> stream = Files.list(Paths.get(dirPath))) {
             stream
                    .filter(path -> !Files.isDirectory(path))
                    .forEach(path -> {
                        try {
                            File file = path.toFile();
                            if (file.getName().endsWith(".mp3")) {
                                loadAudioFromFile(file);
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

    public AudioData loadAudioFromFile(File audioFile) throws IOException {
        byte[] data = FileUtils.readFileToByteArray(audioFile);
        String term = termFromFilename(audioFile.getName());

        AudioData audioData = createAudioDataForTerm(term, audioFile.getName(), data);

        return audioDataRepository.save(audioData);

    }

    public String termFromFilename(String filename) {
        if (filename == null) {
            return null;
        }

        String term = filename.replace("-", " ");
        int lastDotIndex = term.lastIndexOf(".mp3");
        if (lastDotIndex > 0) {
            return term.substring(0, lastDotIndex);
        }
        return term;
    }

    public void narakeetTest(String term) {
        byte[] data = narakeetSvc.getAudioInfo(term);

        if (data.length > 0) {
            try {
                FileOutputStream output = new FileOutputStream(new File("/Users/yoavmorahg/Documents/batatas2.mp3"));
                IOUtils.write(data, output);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public AudioData saveTest()  throws IOException  {
        Path p = FileSystems.getDefault().getPath("/Users", "yoavmorahg", "Documents"
                , "palito-pt.mp3");
        byte [] fileData = Files.readAllBytes(p);
        System.out.println("Length: "+ fileData.length);
        AudioData audioData = createAudioDataForTerm("palito", fileData);

        return audioDataRepository.save(audioData);
    }

    public List<Long> getIds() {
        return audioDataRepository.getIds();
    }


    public String downloadAllAudioFiles(String containingFolderPath) throws IOException {
        List<Long> ids = getIds();
        String dateTimestamp = dateTimestampFormatter.format(LocalDateTime.now());

        File path = new File(containingFolderPath, dateTimestamp);
        path.mkdir();
        for (Long  id : ids) {
            Optional<AudioData> audioData = findById(id);
            if (audioData.isPresent()) {
//                if (audioData.isPresent() && audioData.get().getId().equals(13L)) {

                String outFileName = audioData.get().getFilename();
                System.out.println(outFileName);
                File outFile = new File(path.toString() + "/" + outFileName);
                Path outPath = Path.of(path.toString() + "/" + outFileName);
                Files.write(outPath, audioData.get().getData());
            }
        }
        return "Download complete.";
    }

    public Optional<AudioData> downloadAudioFileById(String containingFolderPath, Long id) throws IOException {
        Optional<AudioData> audioData = findById(id);
        String dateTimestamp = dateTimestampFormatter.format(LocalDateTime.now());

        File path = new File(containingFolderPath, dateTimestamp);
        path.mkdir();
        if (audioData.isPresent()) {

            String outFileName = audioData.get().getFilename();
            System.out.println(outFileName);
            File outFile = new File(path.toString() + "/" + outFileName);
            Path outPath = Path.of(path.toString() + "/" + outFileName);
            Files.write(outPath, audioData.get().getData());
        }
        return audioData;
    }


}
