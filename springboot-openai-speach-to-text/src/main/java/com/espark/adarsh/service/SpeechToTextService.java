package com.espark.adarsh.service;

import com.espark.adarsh.bean.SpeechToTextResponse;
import com.espark.adarsh.config.ApplicationConfig;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;


import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.espark.adarsh.util.ApplicationConstant.*;

@Slf4j
@Service
public class SpeechToTextService {

    private ObjectMapper objectMapper;

    private ApplicationConfig applicationConfig;

    private ApplicationConfig.ApiConfig apiConfig;

    public SpeechToTextService(ApplicationConfig applicationConfig, ObjectMapper objectMapper) {
        this.applicationConfig = applicationConfig;
        this.objectMapper = objectMapper;
        this.apiConfig = applicationConfig.getApiConfigByKey.apply(SPEECH_TO_TEXT);
    }

    @PostConstruct
    public void init() {
        log.info("Application Config : " + applicationConfig);
    }

    private Supplier<List<BasicHeader>> apiHeaders = () -> {
        return apiConfig.getHeaders()
                .entrySet()
                .stream()
                .map(entry -> {
                    return new BasicHeader(entry.getKey(), entry.getValue());
                })
                .toList();
    };


    public final Function<InputStream, SpeechToTextResponse> transcriberInMemory = (InputStream audioFile) -> {
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            HttpPost httpPost = new HttpPost(apiConfig.getApiBaseUrl() + apiConfig.getApiUrl());
            apiHeaders.get().forEach(header -> httpPost.setHeader(header));
            MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
            multipartEntityBuilder.addBinaryBody(FILE, audioFile, ContentType.create(FILE_CONTENT_TYPE), FILE_NAME);
            apiConfig.getBodyConfig().entrySet().forEach(entry -> {
                multipartEntityBuilder.addTextBody(entry.getKey(), entry.getValue());
            });
            HttpEntity multiPartEntity = multipartEntityBuilder.build();
            httpPost.setEntity(multiPartEntity);
            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                String result = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
                log.info("Response From OpenApi Server {}", result);
                response.close();
                return objectMapper.readValue(result, new TypeReference<SpeechToTextResponse>() {
                });
            }
        } catch (Exception e) {
            log.error("exception occurred while calling openai api", e);
        }
        return null;
    };
}
