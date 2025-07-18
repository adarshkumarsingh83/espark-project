package com.espark.adarsh.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TextToSpeechRequest {
    String requestId;
    String input;
    String voice;
    String model;
}
