package com.yoavmorahg.learner_app.service;

import com.yoavmorahg.learner_app.Exception.InvalidDataException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Service
public class InitializationService {


    private final static String AUDIO_DIRS_LIST_FILE = "data/initialization/AudioDirectoriesToLoad.txt";
    private final static String VOCAB_COLLECTION_LIST_FILE = "data/initialization/VocabCollectionsToCreate.txt";
    private final static String VOCAB_FILE_LIST_FILE = "data/initialization/VocabFilesToLoad.txt";


    private final DataLoaderService dataLoaderService;

    public InitializationService(DataLoaderService dataLoaderService) {
        this.dataLoaderService = dataLoaderService;
    }

    //TODO collection mapping wrong
    public String loadAllData() throws IOException {
//        loadAudioData();
        createCollections();
        return loadVocabData();
    }

    public String loadVocabData() {
        return dataLoaderService.loadVocabFiles();
    }

    //TODO response for IOExceptioon
    public String loadAudioData() throws IOException {
        //TODO loading starts string
        try {
            dataLoaderService.loadAudioFiles();
        } catch (InvalidDataException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }

        return "success";

    }

    public String createCollections() {
        return dataLoaderService.createCollections();
    }

    public static List<String> getCollectionsToCreate() {
        String collectionNames =  VOCAB_COLLECTION_LIST_FILE;

        List<String> collectionNamesList = new ArrayList<>();

        try {
            File listFile = DataLoaderService.readResourceAsFile(collectionNames);
            try (Scanner reader = new Scanner(listFile)) {
                while(reader.hasNextLine()) {
                    String line = reader.nextLine();
//                    System.out.println(line);
                    collectionNamesList.add(line);
                }
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        return collectionNamesList;
    }

    public static List<String> getAudioDirectoriesToLoad() {

        String dirListFilePath =  AUDIO_DIRS_LIST_FILE;

        List<String> audioDirList = new ArrayList<>();

        try {
            File listFile = DataLoaderService.readResourceAsFile(dirListFilePath);
            try (Scanner reader = new Scanner(listFile)) {
                while(reader.hasNextLine()) {
                    String line = reader.nextLine();
//                    System.out.println(line);
                    audioDirList.add(line);
                }
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        return audioDirList;
    }

    //TODO refactor to avoid duplicated code
    public  static List<String> getVocabFilesToLoad() {

        String vocabFileListPath = VOCAB_FILE_LIST_FILE;

        List<String> vocabFileList = new ArrayList<>();

        try {
            File listFile = DataLoaderService.readResourceAsFile(vocabFileListPath);
            try (Scanner reader = new Scanner(listFile)) {
                while(reader.hasNextLine()) {
                    String line = reader.nextLine();
                    System.out.println(line);
                    vocabFileList.add(line);
                }
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        return vocabFileList;
    }


}
