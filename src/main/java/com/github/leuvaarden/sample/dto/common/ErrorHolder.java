package com.github.leuvaarden.sample.dto.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
public class ErrorHolder {
    @NotNull
    @ApiModelProperty(value = "Error code", example = "400")
    int code;
    @NotNull
    @ApiModelProperty(value = "System description", example = "FileNotFoundException")
    String description;
    @NotNull
    @ApiModelProperty(value = "User description", example = "Requested file not found")
    String message;
}
