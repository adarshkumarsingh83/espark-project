package com.espark.adarsh.excel.exception;

public class WorkBookNotLoadedException extends RuntimeException {
    public WorkBookNotLoadedException(String msg,Exception e)  {
        super(msg,e);
    }
}
