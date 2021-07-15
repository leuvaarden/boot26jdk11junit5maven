package com.github.leuvaarden.sample.dto;

public interface Response<T> {
    boolean getSuccess();

    default T getData() {
        return null;
    }

    default ErrorResponse.ErrorHolder getError() {
        return null;
    }
}
