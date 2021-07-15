package com.github.leuvaarden.sample.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class ErrorHolder {
    @NotNull
    private final String code;
    @NotNull
    private final String description;
    @NotNull
    private final String message;
}
