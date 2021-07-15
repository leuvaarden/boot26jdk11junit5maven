package com.github.leuvaarden.sample.dto;

import org.springframework.util.Assert;

import javax.validation.constraints.NotNull;

public class ErrorResponse<T> implements Response<T> {

    @NotNull
    private final ErrorHolder error;

    public ErrorResponse(@NotNull ErrorHolder error) {
        Assert.notNull(error, "Error is null");
        this.error = error;
    }

    @Override
    public boolean getSuccess() {
        return false;
    }

    @Override
    public ErrorHolder getError() {
        return error;
    }
}
