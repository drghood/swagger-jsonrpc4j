package com.demo.dtos;

import lombok.Builder;
import lombok.EqualsAndHashCode;

@lombok.Data
@Builder
@EqualsAndHashCode(callSuper = false)
public class Error {
    @Builder.Default
    private String code = "1";
    private String message;
    private Data data;

    public Error() {
        super();
    }

    public Error(String code, String message, Data data) {
        super();
        this.code = code;
        this.message = message;
        this.data = data;
    }
}