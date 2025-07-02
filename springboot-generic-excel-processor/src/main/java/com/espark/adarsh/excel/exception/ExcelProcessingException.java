package com.espark.adarsh.excel.exception;

public class ExcelProcessingException extends RuntimeException {
    public ExcelProcessingException(String msg, Exception e) {
        super(msg,e);
    }
}
