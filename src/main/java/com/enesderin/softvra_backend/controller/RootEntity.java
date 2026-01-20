package com.enesderin.softvra_backend.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RootEntity<T> {

    private Integer status;
    private T data;
    private String errorMessage;

    public static <T> RootEntity<T> ok(T payload) {
        RootEntity<T> rootEntity = new RootEntity<>();
        rootEntity.setData(payload);
        rootEntity.setStatus(200);
        rootEntity.setErrorMessage(null);
        return rootEntity;
    }

    public static <T> RootEntity<T> error(String errorMessage) {
        RootEntity<T> rootEntity = new RootEntity<>();
        rootEntity.setErrorMessage(errorMessage);
        rootEntity.setStatus(500);
        rootEntity.setData(null);
        return rootEntity;
    }
}
