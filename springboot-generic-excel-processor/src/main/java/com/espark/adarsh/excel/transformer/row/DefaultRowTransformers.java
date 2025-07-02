package com.espark.adarsh.excel.transformer.row;

import com.espark.adarsh.excel.bean.DefaultBean;
import com.espark.adarsh.excel.config.MetaDataConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;

@Slf4j
@com.espark.adarsh.excel.annotation.RowTransformer(name = "default")
public class DefaultRowTransformers implements RowTransformers<Row, MetaDataConfig.RowMetaData, DefaultBean<String, Object>> {

    @Override
    public DefaultBean<String, Object> transformRow(Row row, MetaDataConfig.RowMetaData rowMetaData) {
        log.debug("DefaultRowTransformer::transformRow : {}", row.getRowNum());
        DefaultBean<String, Object> bean = new DefaultBean<>();
        DefaultBean.DataRow<String, Object> dataRow = bean.createDataRow();
        for (int i = 0; i < row.getLastCellNum(); i++) {
            String columnName = row.getSheet().getRow(0).getCell(i).getStringCellValue();
            Object columnValue = row.getCell(i) != null ? row.getCell(i).toString() : null;
            dataRow.put(columnName, columnValue);
        }
        bean.addEntry(dataRow);
        return bean;
    }
}
