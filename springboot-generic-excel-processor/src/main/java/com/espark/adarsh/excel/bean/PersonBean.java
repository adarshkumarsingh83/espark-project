package com.espark.adarsh.excel.bean;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonBean  extends AbstractBean {

    private Integer id;
    private String personName;
    private String email;
    private String phoneNumber;

}
