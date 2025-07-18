package com.espark.adarsh.web;

import com.espark.adarsh.bean.TranscriptText;
import com.espark.adarsh.service.AudioToTextService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
@RestController
public class ApplicationController {

    private AudioToTextService audioToTextService;

    public ApplicationController(AudioToTextService audioToTextService) {
        this.audioToTextService = audioToTextService;
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
            TranscriptText transcriptText = audioToTextService.transcriberInMemory.apply(inputStream);
            return ResponseEntity.ok().body(transcriptText.getText());

        } catch (IOException e) {
            log.error("Error processing file upload", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload the file due to an error: " + e.getMessage());
        }
    }

}
