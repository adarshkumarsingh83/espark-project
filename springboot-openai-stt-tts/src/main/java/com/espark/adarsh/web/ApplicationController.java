package com.espark.adarsh.web;

import com.espark.adarsh.bean.SpeechToTextResponse;
import com.espark.adarsh.bean.TextToSpeechRequest;
import com.espark.adarsh.service.SpeechToTextService;
import com.espark.adarsh.service.TextToSpeechService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
@RestController
public class ApplicationController {

    private SpeechToTextService speechToTextService;
    private TextToSpeechService textToSpeechService;

    public ApplicationController(SpeechToTextService speechToTextService,
                                 TextToSpeechService textToSpeechService) {
        this.speechToTextService = speechToTextService;
        this.textToSpeechService = textToSpeechService;
    }


    @PostMapping(value = "/convert/audio", consumes = {"multipart/form-data"})
    public ResponseEntity<?> uploadAudioStream(@RequestParam("file") MultipartFile file) {

        if (file.isEmpty()) {
            log.info("Server info '{}'", "empty file");
            return ResponseEntity.badRequest().body("No file uploaded.");
        }

        try {
            InputStream inputStream = file.getInputStream();
            // send to transcript
            SpeechToTextResponse speechToTextResponse = speechToTextService.transcriberInMemory.apply(inputStream);
            return ResponseEntity.ok().body(speechToTextResponse.getText());

        } catch (IOException e) {
            log.error("Error processing file upload", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload the file due to an error: " + e.getMessage());
        }
    }

    @PostMapping(value = "/convert/text/audio", consumes = {"application/json"})
    public ResponseEntity<String> convertTextToAudio(@RequestBody TextToSpeechRequest textToSpeechRequest) {
        try {
            String response = textToSpeechService.convertTextToSpeech.apply(textToSpeechRequest);
            return ResponseEntity.ok()
                    .body(response);
        } catch (Exception e) {
            log.error("Error converting text to audio", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to convert text to audio: " + e.getMessage());
        }
    }

}
