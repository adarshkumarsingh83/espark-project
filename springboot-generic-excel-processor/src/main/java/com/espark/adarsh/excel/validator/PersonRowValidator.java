package com.espark.adarsh.excel.validator;

import com.espark.adarsh.excel.annotation.RowValidator;
import com.espark.adarsh.excel.bean.PersonBean;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RowValidator(name = "person-validator")
public class PersonRowValidator  implements RowValidators<PersonBean>{
    @Override
    public boolean validateRow(PersonBean rowData) {
        log.debug("PersonRowValidator :: validateRow : {}", rowData);
        return true;
    }
}
