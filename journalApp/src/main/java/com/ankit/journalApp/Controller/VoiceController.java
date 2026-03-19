package com.ankit.journalApp.Controller;

import com.ankit.journalApp.Service.ElevenLabsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/voice")
public class VoiceController {

    @Autowired
    private ElevenLabsService service;

    @GetMapping
    public ResponseEntity<byte[]> getVoice(@RequestParam String text) {

        byte[] audio = service.generateSpeech(text);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "audio/mpeg")
                .body(audio);
    }
}
