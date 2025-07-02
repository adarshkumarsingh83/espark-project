package com.espark.adarsh.excel.transformer.column;


import lombok.extern.slf4j.Slf4j;

@Slf4j
@com.espark.adarsh.excel.annotation.ColumnTransformer(name= "email")
public class EmailTransformers implements ColumnTransformers<String, String> {
    @Override
    public String transformColumn(String columnData) {
        log.debug("EmailTransformer ::transformColumn : {}", columnData);
        if (columnData != null && !columnData.isBlank()) {
            return columnData;
        }
        return null;
    }
}
