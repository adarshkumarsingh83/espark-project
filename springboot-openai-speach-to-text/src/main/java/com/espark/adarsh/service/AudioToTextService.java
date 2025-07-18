package com.espark.adarsh.service;

import com.espark.adarsh.bean.TranscriptText;
import com.google.gson.Gson;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.function.Function;

@Slf4j
@Service
public class AudioToTextService {

    @Value("${espark.openai.apikey}")
    private String OPENAI_API_KEY;

    @Value("${espark.openai.apiUrl}")
    private String API_URL;

    @PostConstruct
    public void init() {
        log.info("OPENAI_API_KEY : " + OPENAI_API_KEY);
        log.info("API_URL : " + API_URL);
    }

    public final Function<InputStream, TranscriptText> transcriberInMemory = (InputStream audioFile) -> {

        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            HttpPost httpPost = new HttpPost(API_URL);
            httpPost.setHeader("Authorization", "Bearer " + OPENAI_API_KEY);

            HttpEntity multiPartEntity = MultipartEntityBuilder.create()
                    .addBinaryBody("file", audioFile, ContentType.create("audio/mpeg"), "audio.webm")
                    .addTextBody("model", "whisper-1")
                    .build();

            httpPost.setEntity(multiPartEntity);

            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {

                String result = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
                System.out.println(result);
                Gson gson = new Gson();
                response.close();
                return gson.fromJson(result, TranscriptText.class);
            }
        } catch (Exception e) {
            log.error("exception occurred while calling openai api", e);
        }
        return null;
    };
}
