package com.espark.adarsh.excel.validator;

import com.espark.adarsh.excel.annotation.RowValidator;
import com.espark.adarsh.excel.bean.AddressBean;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RowValidator(name = "address-validator")
public class AddressRowValidator implements RowValidators<AddressBean>{
    @Override
    public boolean validateRow(AddressBean rowData) {
        log.debug("AddressRowValidator :: validateRow : {}", rowData);
        return true;
    }
}
