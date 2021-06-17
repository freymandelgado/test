package com.leantech.test.rest.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Optional;

@Data
@Builder
public class UpdateEmployeeDto {

    private String id;
    private Optional<String> name = Optional.empty();
    private Optional<String> lastName = Optional.empty();
    private Optional<String> address = Optional.empty();
    private Optional<String> cellphone = Optional.empty();
    private Optional<String> cityName = Optional.empty();
    private Optional<String> positionName = Optional.empty();
    private Optional<BigDecimal> salary = Optional.empty();
}
