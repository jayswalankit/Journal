package com.ankit.journalApp.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ElevenLabsService {

    @Autowired
    private RestTemplate restTemplate;

    private final String API_KEY = "sk_03ab0ffb9590a67e507890d2b477826b86b71b987a01fd66"; // apni new key
    private final String VOICE_ID = "hpp4J3VqNfWAUOO0d1Us";

    public byte[] generateSpeech(String text) {

        String url = "https://api.elevenlabs.io/v1/text-to-speech/" + VOICE_ID;

        HttpHeaders headers = new HttpHeaders();
        headers.set("xi-api-key", API_KEY);
        headers.setContentType(MediaType.APPLICATION_JSON);

        String body = """
        {
          "text": "%s",
          "voice_settings": {
            "stability": 0.5,
            "similarity_boost": 0.75
          }
        }
        """.formatted(text);

        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        ResponseEntity<byte[]> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                entity,
                byte[].class
        );

        return response.getBody();
    }
}