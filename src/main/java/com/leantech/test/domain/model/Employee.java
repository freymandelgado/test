package com.leantech.test.domain.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class Employee {

    private String id;
    private Person person;
    private Position position;
    private BigDecimal salary;
}
