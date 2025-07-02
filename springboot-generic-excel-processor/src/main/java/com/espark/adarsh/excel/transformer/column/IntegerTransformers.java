package com.espark.adarsh.excel.transformer.column;


import lombok.extern.slf4j.Slf4j;

@Slf4j
@com.espark.adarsh.excel.annotation.ColumnTransformer(name = "integer")
public class IntegerTransformers implements ColumnTransformers<String, Integer> {
    @Override
    public Integer transformColumn(String columnData) {
        log.debug("IntegerTransformer ::transformColumn : {}", columnData);
        if (columnData != null && !columnData.isBlank()) {
            return Integer.valueOf(columnData);
        }
        return null;
    }
}
