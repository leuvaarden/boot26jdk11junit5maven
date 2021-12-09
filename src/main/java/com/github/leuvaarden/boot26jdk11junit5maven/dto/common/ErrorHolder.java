package com.github.leuvaarden.boot26jdk11junit5maven.dto.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
public class ErrorHolder {
    @NotNull
    @Schema(description = "Error code", example = "400")
    int code;
    @NotNull
    @Schema(description = "System description", example = "FileNotFoundException")
    String description;
    @NotNull
    @Schema(description = "User description", example = "Requested file not found")
    String message;
}
