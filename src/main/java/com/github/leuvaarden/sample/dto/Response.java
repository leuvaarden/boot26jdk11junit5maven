package com.github.leuvaarden.sample.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface Response<T> {
    @JsonProperty(index = 0)
    boolean getSuccess();

    default T getData() {
        return null;
    }

    default ErrorHolder getError() {
        return null;
    }
}
