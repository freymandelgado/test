package com.leantech.test.domain.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Person {

    private String id;
    private String name;
    private String lastName;
    private String address;
    private String cellphone;
    private String cityName;

}
