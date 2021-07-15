package com.github.leuvaarden.sample.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class SuccessResponse<T> implements Response<T> {

    @NotNull
    private final T data;

    @Override
    public boolean getSuccess() {
        return true;
    }

    @Override
    public T getData() {
        return data;
    }
}
