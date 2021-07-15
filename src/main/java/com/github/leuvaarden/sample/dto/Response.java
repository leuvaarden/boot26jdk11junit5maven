package com.github.leuvaarden.sample.dto;

public interface Response<T> {
    boolean getSuccess();

    default T getData() {
        return null;
    }

    default ErrorHolder getError() {
        return null;
    }
}
