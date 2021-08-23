package com.github.leuvaarden.sample.dto.common;

import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
public class SuccessResponse<T> implements Response<T> {

    @NotNull
    T data;

    @Override
    public boolean getSuccess() {
        return true;
    }

    @Override
    public T getData() {
        return data;
    }
}
