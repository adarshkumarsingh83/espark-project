package com.espark.adarsh.excel.exception;

public class MappingNotFoundException extends RuntimeException {
    public MappingNotFoundException(String msg,Exception e) {
        super(msg,e);
    }
}
