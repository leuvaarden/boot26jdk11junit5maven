package com.github.leuvaarden.sample.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class ErrorHolder {
    @NotNull
    @ApiModelProperty(value = "Error code", example = "400")
    private final String code;
    @NotNull
    @ApiModelProperty(value = "System description", example = "FileNotFoundException")
    private final String description;
    @NotNull
    @ApiModelProperty(value = "User description", example = "Requested file not found")
    private final String message;
}
