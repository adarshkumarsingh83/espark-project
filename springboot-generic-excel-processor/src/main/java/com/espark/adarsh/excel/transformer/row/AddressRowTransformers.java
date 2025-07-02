package com.espark.adarsh.excel.transformer.row;

import com.espark.adarsh.excel.bean.AddressBean;
import com.espark.adarsh.excel.bean.PersonBean;
import com.espark.adarsh.excel.config.MetaDataConfig;
import com.espark.adarsh.excel.processor.ColumnTransformerProcessor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@com.espark.adarsh.excel.annotation.RowTransformer(name = "address-transformer")
public class AddressRowTransformers implements RowTransformers<Row, MetaDataConfig.RowMetaData, AddressBean> {

    @Override
    public AddressBean transformRow(Row row, MetaDataConfig.RowMetaData rowMetaData) {
        log.debug("AddressRowTransformers::transformRow : {}", row.getRowNum());
        AddressBean addressBean = new AddressBean();
        List<MetaDataConfig.Column> columnMeta = rowMetaData.getColumns();
        for (MetaDataConfig.Column column : columnMeta) {
            int columnIndex = column.getColumnIndex();
            try {
                java.lang.reflect.Method setterMethod = AddressBean.class.getMethod(column.getSetterMethod(), column.getColumnMeta().getTypeClass().get());
                AtomicReference<Object> value = new AtomicReference<>(getCellValue.apply(row.getCell(columnIndex)));
                if (column.getTransformers() != null) {
                    column.getTransformers().forEach(transformer -> {
                        value.set(ColumnTransformerProcessor.getColumnTransformer.apply(transformer).transformColumn(value.get()));
                    });
                }
                setterMethod.invoke(addressBean, value.get());
            } catch (IllegalAccessException | java.lang.reflect.InvocationTargetException | NoSuchMethodException e) {
                log.error("Error while setting value to AddressBean {} ", e.getMessage());
            }
        }
        return addressBean;
    }
}
