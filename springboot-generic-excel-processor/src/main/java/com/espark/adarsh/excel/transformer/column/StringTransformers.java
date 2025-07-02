package com.espark.adarsh.excel.transformer.column;


import lombok.extern.slf4j.Slf4j;

@Slf4j
@com.espark.adarsh.excel.annotation.ColumnTransformer(name = "string")
public class StringTransformers implements ColumnTransformers<String, String> {
    @Override
    public String transformColumn(String columnData) {
        log.debug("StringTransformer ::transformColumn : {}", columnData);
        if (columnData != null) {
            return columnData.toString();
        }
        return null;
    }
}
