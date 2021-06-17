package com.leantech.test.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Status {

    ACTIVE("active"),
    UNACTIVE("unactive");

    private final String status;
}
