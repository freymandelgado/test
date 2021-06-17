package com.leantech.test.domain.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Position {

    private String id;
    private String name;

}
