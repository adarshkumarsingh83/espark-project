package com.espark.adarsh.excel.transformer.column;


import lombok.extern.slf4j.Slf4j;

@Slf4j
@com.espark.adarsh.excel.annotation.ColumnTransformer(name= "phone")
public class PhoneTransformers implements ColumnTransformers<String, String> {
    @Override
    public String transformColumn(String columnData) {
        log.debug("PhoneTransformer ::transformColumn : {}", columnData);
        if (columnData != null && !columnData.isBlank()) {
            return columnData;
        }
        return null;
    }
}
