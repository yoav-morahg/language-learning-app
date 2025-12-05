package com.yoavmorahg.learner_app.controller;


import com.yoavmorahg.learner_app.Exception.VocabItemNotFoundException;
import com.yoavmorahg.learner_app.entity.VocabItemDto;
import com.yoavmorahg.learner_app.service.DataLoaderService;
import com.yoavmorahg.learner_app.service.VocabService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/vocab")
@CrossOrigin
public class VocabController {

    private final VocabService vocabService;
    private final DataLoaderService dataLoaderService;

    public VocabController(VocabService vocabService,
                           DataLoaderService dataLoaderService) {
        this.vocabService = vocabService;
        this.dataLoaderService = dataLoaderService;
    }


    @GetMapping("")
    public List<VocabItemDto> list() {
        return vocabService.list();
    }

    @GetMapping("/term")
    public VocabItemDto getVocabItem(@RequestParam String term)  {
        try {
            return vocabService.getVocabItem(term);
        } catch (VocabItemNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/random")
    public VocabItemDto getRandomTerm() {
        return vocabService.getRandomVocabTerm();
    }

}
