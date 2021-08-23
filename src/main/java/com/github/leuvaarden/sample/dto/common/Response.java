package com.github.leuvaarden.sample.dto.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Contains data if success is true, otherwise contains error")
public interface Response<T> {

    @ApiModelProperty(value = "Success flag", required = true)
    @JsonProperty(index = 0)
    boolean getSuccess();

    default T getData() {
        return null;
    }

    default ErrorHolder getError() {
        return null;
    }
}
