package com.SE1730.Group3.JobLink.src.data.models.api;

import lombok.Data;

@Data
public class ApiResp<T> {
    private Integer status;
    private String message;
    private T data;
    private final Long timeStamp = System.currentTimeMillis() / 1000;

    public ApiResp(String message, T data) {
        this.message = message;
        this.data = data;
    }
}
