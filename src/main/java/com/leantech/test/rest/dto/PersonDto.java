package com.leantech.test.rest.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PersonDto {
    private String name;
    private String lastName;
    private String address;
    private String cellphone;
    private String cityName;
}
