package com.espark.adarsh.excel.transformer.column;

public interface ColumnTransformers<T,R> {
    R transformColumn(T columnData);
}
