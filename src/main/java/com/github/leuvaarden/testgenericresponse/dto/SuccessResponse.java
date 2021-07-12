package com.github.leuvaarden.testgenericresponse.dto;

import org.springframework.util.Assert;

import javax.validation.constraints.NotNull;

public class SuccessResponse<T> implements Response<T> {

    @NotNull
    private final T data;

    public SuccessResponse(@NotNull T data) {
        Assert.notNull(data, "Data is null");
        this.data = data;
    }

    @Override
    public boolean getSuccess() {
        return true;
    }

    @Override
    public T getData() {
        return data;
    }
}
