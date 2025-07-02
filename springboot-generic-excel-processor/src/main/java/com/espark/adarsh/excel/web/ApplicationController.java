package com.espark.adarsh.excel.web;

import com.espark.adarsh.excel.bean.AbstractBean;
import com.espark.adarsh.excel.bean.ResponseBean;
import com.espark.adarsh.excel.config.MetaDataConfig;
import com.espark.adarsh.excel.service.ExcelReader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class ApplicationController {


    private MetaDataConfig metaDataConfig;

    public ApplicationController(MetaDataConfig metaDataConfig) {
        this.metaDataConfig = metaDataConfig;
    }

    private static final Map<String, String> dataStore = new HashMap<>(){
        {
            put("address","src/main/resources/store/address.xlsx");
            put("person","src/main/resources/store/person.xlsx");
            put("default","src/main/resources/store/default.xlsx");
        }
    };


    @GetMapping("/process/{name}")
    public ResponseBean<Map<String,List<AbstractBean>>> processExcel(@PathVariable("name") String name){
        String filePath = dataStore.getOrDefault(name,dataStore.get("default"));
        return new ExcelReader<AbstractBean>(metaDataConfig).readExcelFile(filePath,name);
    }


}
