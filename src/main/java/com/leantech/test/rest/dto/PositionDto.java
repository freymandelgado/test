package com.leantech.test.rest.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PositionDto {

    private Long id;
    private String name;
    private List<EmployeeBySalaryDto> employees;
}
