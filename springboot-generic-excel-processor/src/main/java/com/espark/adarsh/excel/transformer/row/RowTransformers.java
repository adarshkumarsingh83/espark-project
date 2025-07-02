package com.espark.adarsh.excel.transformer.row;

import org.apache.poi.ss.usermodel.Cell;

import java.util.function.Function;

public interface RowTransformers<T1, T2, R> {

    R transformRow(T1 t1, T2 t2);

    public final Function<Cell, Object> getCellValue = cell -> {
        if (cell != null) {
            return switch (cell.getCellType()) {
                case STRING -> cell.getStringCellValue();
                case NUMERIC -> {
                    String value = cell.getNumericCellValue() + "";
                    yield value.contains(".") ? value.substring(0, value.indexOf(".")) : value;
                }
                case BOOLEAN -> cell.getBooleanCellValue();
                case FORMULA -> cell.getCellFormula();
                case BLANK -> "";
                default -> null;
            };
        }
        return null;
    };

}
