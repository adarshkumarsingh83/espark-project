package com.espark.adarsh.excel.bean;


import lombok.Data;

@Data
public class ResponseBean <T>{

    private T data;
    private String message;

}
