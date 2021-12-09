package com.github.leuvaarden.boot26jdk11junit5maven.dto.common;

import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
public class ErrorResponse<T> implements Response<T> {

    @NotNull
    ErrorHolder error;

    @Override
    public boolean getSuccess() {
        return false;
    }

    @Override
    public ErrorHolder getError() {
        return error;
    }
}
