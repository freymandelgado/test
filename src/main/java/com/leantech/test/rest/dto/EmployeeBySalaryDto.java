package com.leantech.test.rest.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class EmployeeBySalaryDto {

    private Long id;
    private BigDecimal salary;
    private PersonDto person;
}
