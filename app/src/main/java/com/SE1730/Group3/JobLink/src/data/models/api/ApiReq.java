package com.SE1730.Group3.JobLink.src.data.models.api;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ApiReq<T> {
    private T data;
    private final Long timeStamp = System.currentTimeMillis() / 1000;

    public ApiReq(T data) {
        this.data = data;
    }


}
