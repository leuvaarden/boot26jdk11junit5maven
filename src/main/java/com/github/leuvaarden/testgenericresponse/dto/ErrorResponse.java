package com.github.leuvaarden.testgenericresponse.dto;

import org.springframework.util.Assert;

import javax.validation.constraints.NotNull;

public class ErrorResponse<T> implements Response<T> {

    @NotNull
    private final ErrorHolder error;

    public ErrorResponse(@NotNull ErrorHolder error) {
        Assert.notNull(error, "Error is null");
        this.error = error;
    }

    @Override
    public boolean getSuccess() {
        return false;
    }

    @Override
    public ErrorHolder getError() {
        return error;
    }

    public static class ErrorHolder {
        @NotNull
        private final String code;
        @NotNull
        private final String description;
        @NotNull
        private final String message;

        public ErrorHolder(@NotNull String code, @NotNull String description, @NotNull String message) {
            Assert.notNull(code, "Code is null");
            this.code = code;
            Assert.notNull(description, "Description is null");
            this.description = description;
            Assert.notNull(message, "Message is null");
            this.message = message;
        }

        public String getCode() {
            return code;
        }

        public String getDescription() {
            return description;
        }

        public String getMessage() {
            return message;
        }
    }
}
