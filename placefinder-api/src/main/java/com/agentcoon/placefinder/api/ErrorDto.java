package com.agentcoon.placefinder.api;

public class ErrorDto {

    private String message;

    private ErrorDto(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public static final class Builder {

        private String message;

        public static Builder anError() {
            return new Builder();
        }

        public Builder withMessage(String message) {
            this.message = message;
            return this;
        }

        public ErrorDto build() {
            return new ErrorDto(message);
        }
    }
}
