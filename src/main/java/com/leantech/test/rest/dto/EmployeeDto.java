package com.leantech.test.rest.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class EmployeeDto {

    private String name;
    private String lastName;
    private String address;
    private String cellphone;
    private String cityName;
    private String positionName;
    private BigDecimal salary;
}
