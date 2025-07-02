package com.espark.adarsh.excel.bean;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressBean extends AbstractBean {

    private Integer addressId;
    private String street;
    private String city;
    private String state;
    private String country;
    private String zipCode;
}
