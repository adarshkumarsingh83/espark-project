package com.espark.adarsh.excel.service;

import com.espark.adarsh.excel.bean.ResponseBean;
import com.espark.adarsh.excel.config.MetaDataConfig;
import com.espark.adarsh.excel.exception.ExcelProcessingException;
import com.espark.adarsh.excel.exception.WorkBookNotLoadedException;
import com.espark.adarsh.excel.processor.RowTransformerProcessor;
import com.espark.adarsh.excel.processor.RowValidatorProcessor;
import com.espark.adarsh.excel.transformer.row.RowTransformers;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

@Slf4j
public class ExcelReader<T> {

    private MetaDataConfig metaDataConfig;

    public ExcelReader(MetaDataConfig metaDataConfig) {
        this.metaDataConfig = metaDataConfig;
    }

    public ResponseBean<Map<String,List<T>>> readExcelFile(String filePath, String rowMetaDataIdentifier) {
        log.info("ExcelReader :: readExcelFile :: filePath :: " + filePath);
        Workbook workbook = this.readWorkBook.apply(filePath);
        ResponseBean<Map<String,List<T>>> responseBean = new ResponseBean<>();
        List<T> response = new LinkedList<>();
        List<T> failed = new LinkedList<>();
        MetaDataConfig.RowMetaData rowMetaData = this.metaDataConfig.rowMetaData.apply(rowMetaDataIdentifier);

        if (rowMetaData.getSheet().size() <= workbook.getNumberOfSheets()) {

            for (Integer sheetIndex : rowMetaData.getSheet()) {
                log.info("Processing sheet :: {} ", sheetIndex);
                Sheet sheet = this.getSheet.apply(workbook, sheetIndex);
                try {
                    for (Row row : sheet) {
                        if (row.getRowNum() != 0) {
                            if (rowMetaData.getRowTransformer() != null && !rowMetaData.getRowTransformer().isBlank()
                                    && idDefaultTransformer.test(rowMetaData)) {
                                RowTransformers<Row, MetaDataConfig.RowMetaData, T> rowTransformers = RowTransformerProcessor.getRowTransformer.apply(rowMetaData.getRowTransformer());
                                T data = rowTransformers.transformRow(row, rowMetaData);
                                if (rowMetaData.getValidators() != null) {
                                    Optional<Boolean> result = rowMetaData.getValidators()
                                            .stream()
                                            .map(validator -> RowValidatorProcessor.getRowValidator.apply(validator).validateRow(data))
                                            .filter(isValid -> isValid != true)
                                            .findFirst();
                                    if (result.isEmpty()) {
                                        response.add(data);
                                    } else {
                                        log.warn("Row :: {} is skipped due to validation failure", row.getRowNum());
                                        failed.add(data);
                                    }

                                } else {
                                    response.add(data);
                                }
                            }  else {
                                RowTransformers<Row, MetaDataConfig.RowMetaData, T> rowTransformers = RowTransformerProcessor.getRowTransformer.apply("default");
                                response.add((T) rowTransformers.transformRow(row, rowMetaData));
                            }
                        }
                    }

                } catch (Exception e) {
                    log.error("Error while processing sheet :: {} ", sheetIndex, e);
                    throw new ExcelProcessingException("Error while processing sheet :: " + sheetIndex + " :: ", e);
                }
            }

        }
        responseBean.setMessage((!failed.isEmpty() ? "Excel file is processed with failed Data " : "Excel file is processed successfully"));
        responseBean.setData(new HashMap<>(){
            {
                put("success", response);
                put("failed", failed);
            }
        });
        return responseBean;
    }


    public final Function<String, Workbook> readWorkBook = (filePath) -> {
        log.debug("ExcelReader :: getWorkbook :: filePath :: " + filePath);
        try (FileInputStream fileInputStream = new FileInputStream(filePath)) {
            XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
            log.info("Workbook is loaded with on path {}  {} sheets", filePath, workbook.getNumberOfSheets());
            return workbook;
        } catch (Exception e) {
            log.error("Error while reading workbook :: " + e.getMessage());
            throw new WorkBookNotLoadedException("Error while reading workbook :: ",  e);
        }
    };

    public final BiFunction<Workbook, Integer, Sheet> getSheet = (workbook, sheetIndex) -> {
        log.debug("ExcelReader :: getSheet :: sheetIndex :: " + sheetIndex);
        return workbook.getSheetAt(sheetIndex);
    };

    public final Function<List<MetaDataConfig.Column>, List<MetaDataConfig.Column>> sortColumns = (columns) -> {
        log.debug("ExcelReader :: sortColumns :: columns :: {}", columns);
        columns.sort((c1, c2) -> {
            if (c1.getColumnIndex() == null || c2.getColumnIndex() == null) {
                return 0;
            }
            return Integer.compare(c1.getColumnIndex(), c2.getColumnIndex());
        });
        return columns;
    };

    public final Predicate<MetaDataConfig.RowMetaData> idDefaultTransformer = (rowMetaData) -> {
       return !rowMetaData.getRowTransformer().equals("default-transformer");
    };

}
