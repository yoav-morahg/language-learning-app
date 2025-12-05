package com.yoavmorahg.learner_app.api.narakeet;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Service
public class NarakeetService {

//    private final String GET_AUDIO_URI = "https://api.narakeet.com/text-to-speech/mp3?voice=Lurdes&narration-language=pt";


    private String GET_AUDIO_URI = null;

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${narakeet.api.generate.url}")
    private String textToSpeechUrl;

    @Value("${narakeet.api.key}")
    private String apiKey;



    public NarakeetService() {
        System.out.println("key: " + apiKey);
    }


    // Initialize API attributes
    @PostConstruct
    public void apiInit() {
        if (GET_AUDIO_URI == null) {
            GET_AUDIO_URI = textToSpeechUrl;
        }
    }

    public String getKey() {
        return apiKey + " " + GET_AUDIO_URI;
    }


    public byte[] getAudioInfo(String term) {

        ResponseEntity<byte[]> response = restTemplate.exchange(GET_AUDIO_URI, HttpMethod.POST,
                getHttpEntityForTermAudio(term), byte[].class);

        if(response.getStatusCode().equals(HttpStatus.OK)) {
            return response.getBody();
        }

        return null;
    }

    private HttpEntity<String> getHttpEntityForTermAudio(String term) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM));
        headers.setContentType(MediaType.TEXT_PLAIN);
        headers.set("x-api-key", apiKey);

        HttpEntity<String> entity = new HttpEntity<String>(term, headers);

        return entity;
    }



}


