package com.yoavmorahg.learner_app.controller;

import com.yoavmorahg.learner_app.Exception.AudioDataNotFoundException;
import com.yoavmorahg.learner_app.entity.AudioData;
import com.yoavmorahg.learner_app.service.AudioDataService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/audio")
@CrossOrigin
public class AudioController {

    private final AudioDataService audioDataService;

    public AudioController(AudioDataService audioDataService) {
        this.audioDataService = audioDataService;
    }


    @GetMapping("")
    public AudioData getAudioDataForTerm(@RequestParam String phrase) {
        try {
            return audioDataService.getAudioDataForTerm(phrase);
        } catch (AudioDataNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/play")
    public byte[] getAudioPlayDataForTerm(@RequestParam String phrase) {
        try {
            return audioDataService.getAudioDataForTerm(phrase).getData();
        } catch (AudioDataNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping("")
    public Long insertNewAudioData(@RequestParam String phrase) {
        AudioData audioData = audioDataService.createAudioDataForTerm(phrase);
        return audioData.getId();
    }

    @GetMapping("/download/all")
    public String downloadAllAudioFiles(@RequestParam String dirpath) throws IOException {
        return audioDataService.downloadAllAudioFiles(dirpath);
    }

    @GetMapping("/download/{id}")
    public String downloadAudioFileById(@RequestParam String containingFolderPath, @PathVariable Long id) throws IOException {
        audioDataService.downloadAudioFileById(containingFolderPath, id);
        return "Download complete.";
    }



}
