package com.espark.adarsh.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SpeechToTextResponse {

    private String text;
    private Usage usage;

    @Getter
    @Setter
    public static final class Usage {
        private String type;
        private Integer seconds;
    }
}