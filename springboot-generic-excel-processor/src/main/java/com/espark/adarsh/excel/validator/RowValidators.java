package com.espark.adarsh.excel.validator;

public interface RowValidators <T> {
    boolean validateRow(T rowData);
}
