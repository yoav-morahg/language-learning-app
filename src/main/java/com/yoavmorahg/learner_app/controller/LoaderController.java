package com.yoavmorahg.learner_app.controller;

import com.yoavmorahg.learner_app.Exception.InvalidDataException;
import com.yoavmorahg.learner_app.entity.AudioData;
import com.yoavmorahg.learner_app.service.AudioDataService;
import com.yoavmorahg.learner_app.service.DataLoaderService;
import com.yoavmorahg.learner_app.service.InitializationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/loader")
@CrossOrigin
public class LoaderController {

    private final InitializationService initializationService;
    private final DataLoaderService dataLoaderService;
    private final AudioDataService audioDataService;

    public LoaderController(InitializationService initializationService,
                            DataLoaderService dataLoaderService,
                            AudioDataService audioDataService) {
        this.initializationService = initializationService;
        this.dataLoaderService = dataLoaderService;
        this.audioDataService = audioDataService;
    }

    @PostMapping("/loadAllData")
    public String loadAllAudioData() throws IOException {
//        return initializationService.loadAudioData();
//        return initializationService.createCollections();
        return initializationService.loadAllData();
    }


    @PostMapping("/audio/from-file")
    @ResponseStatus(HttpStatus.CREATED)
    public AudioData loadAudioFromFile(@RequestParam String filepath) throws IOException {
        try {
            return dataLoaderService.loadAudioDataFromFile(filepath);
        } catch (InvalidDataException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }

    @PostMapping("/audio/load-from-directory")
    public String loadAudioFilesFromDirectory(@RequestParam String dirpath) {
        try {
            return dataLoaderService.loadAudioFilesInDir(dirpath);
        } catch (IOException ex) {
            throw new RuntimeException(ex.getMessage());
        } catch (InvalidDataException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }


}
