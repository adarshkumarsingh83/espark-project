package com.espark.adarsh.service;


import com.espark.adarsh.bean.TextToSpeechRequest;
import com.espark.adarsh.config.ApplicationConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpClient;

import org.springframework.stereotype.Service;

import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.espark.adarsh.util.ApplicationConstant.SPEECH_TO_TEXT;
import static com.espark.adarsh.util.ApplicationConstant.TEXT_TO_SPEECH;

@Slf4j
@Service
public class TextToSpeechService {

    private ObjectMapper objectMapper;

    private ApplicationConfig applicationConfig;

    private ApplicationConfig.ApiConfig apiConfig;


    public TextToSpeechService(ObjectMapper objectMapper ,ApplicationConfig applicationConfig) {
        this.applicationConfig = applicationConfig;
        this.objectMapper = objectMapper;
        this.apiConfig = applicationConfig.getApiConfigByKey.apply(TEXT_TO_SPEECH);
    }

    @PostConstruct
    public void init() {
        log.info("Application Config : " + applicationConfig);
    }

    private Function<TextToSpeechRequest,TextToSpeechRequest> fixMissingValues = (TextToSpeechRequest textToSpeechRequest) -> {
        if(textToSpeechRequest.getModel() == null) {
            textToSpeechRequest.setModel(apiConfig.getBodyConfig().get("model"));
        }
        if(textToSpeechRequest.getVoice() == null) {
            textToSpeechRequest.setVoice(apiConfig.getBodyConfig().get("voice"));
        }
        if(textToSpeechRequest.getInput() == null) {
            throw new RuntimeException("Input Text is required");
        }
        return textToSpeechRequest;
    };

    public final Supplier<String> fetchFileName = () -> {
        return apiConfig.getBodyConfig().get("fileName") +"_"+ System.currentTimeMillis() +"."+ apiConfig.getBodyConfig().get("format");
    };

    public final Function<TextToSpeechRequest,String> convertTextToSpeech = (TextToSpeechRequest textToSpeechRequest) -> {
        log.info("Text to Speech Request : " + textToSpeechRequest);
        String response = null;
        try {
            String fileName = textToSpeechRequest.getRequestId()+"_"+fetchFileName.get();
            textToSpeechRequest.setRequestId(null);
            String bodyString = objectMapper.writeValueAsString(fixMissingValues.apply(textToSpeechRequest));

            HttpClient client = HttpClient.newHttpClient();

            HttpRequest.Builder httpRequestBuilder = HttpRequest.newBuilder();
            httpRequestBuilder.uri(new URI(apiConfig.getApiBaseUrl() + apiConfig.getApiUrl()));

            apiConfig.getHeaders()
                    .entrySet()
                    .stream()
                    .forEach(entry -> {
                        httpRequestBuilder.header(entry.getKey(), entry.getValue());
                    });

            HttpRequest request = httpRequestBuilder.POST(HttpRequest.BodyPublishers.ofString(bodyString)).build();

            HttpResponse<Path> httpResponse = client.send(request,
                    HttpResponse.BodyHandlers.ofFile(Paths.get(fileName),
                            StandardOpenOption.CREATE, StandardOpenOption.WRITE));

            if (httpResponse.statusCode() == 200) {
                log.info("Speech Audio was saved to =>  {}", fileName);
                response =  "Speech Audio was saved to => "+fileName;
            } else {
                log.info("Failed to retrieve audio: {}" , httpResponse.statusCode());
                response = "Failed to retrieve audio: " + httpResponse.statusCode();
            }
        } catch (Exception e) {
            log.error("Exception Occurred : ", e);
        }
        return response ;
    };

}
