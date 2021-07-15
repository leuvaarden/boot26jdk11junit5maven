package com.github.leuvaarden.sample.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class ErrorResponse<T> implements Response<T> {

    @NotNull
    private final ErrorHolder error;

    @Override
    public boolean getSuccess() {
        return false;
    }

    @Override
    public ErrorHolder getError() {
        return error;
    }
}
