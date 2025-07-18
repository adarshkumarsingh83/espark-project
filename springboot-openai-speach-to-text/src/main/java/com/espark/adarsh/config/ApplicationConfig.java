package com.espark.adarsh.config;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "espark.openai")
public class ApplicationConfig {

    private String apikey;
     Map< String, ApiConfig> apiConfigs = new HashMap<>();

    @PostConstruct
    public void init() {
        System.out.println("Application Config : " + apiConfigs);
    }

    @Getter
    @Setter
    public static class ApiConfig {
        private String apiBaseUrl;
        private String apiUrl;
        private Map<String, String> bodyConfig = new HashMap<>();
        private Map<String, String> headers = new HashMap<>();
    }

    public final Function< String,ApiConfig> getApiConfigByKey = (String key) -> {
       return apiConfigs.get(key);
    };


}
