package com.github.leuvaarden.boot26jdk11junit5maven.dto.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Contains data if success is true, otherwise contains error")
public interface Response<T> {

    @Schema(description = "Success flag", required = true)
    @JsonProperty(index = 0)
    boolean getSuccess();

    default T getData() {
        return null;
    }

    default ErrorHolder getError() {
        return null;
    }
}
