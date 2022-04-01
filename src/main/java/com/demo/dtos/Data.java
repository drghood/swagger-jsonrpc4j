package com.demo.dtos;

import lombok.Builder;
import lombok.EqualsAndHashCode;

@lombok.Data
@Builder
@EqualsAndHashCode(callSuper = false)
public class Data {
    private String exceptionTypeName;
    private String message;

    public Data() {
        super();
    }

    public Data(String exceptionTypeName, String message) {
        super();
        this.exceptionTypeName = exceptionTypeName;
        this.message = message;
    }
}