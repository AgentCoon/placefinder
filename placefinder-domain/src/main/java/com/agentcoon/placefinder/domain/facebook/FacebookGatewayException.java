package com.agentcoon.placefinder.domain.facebook;

public class FacebookGatewayException extends Exception {

    public FacebookGatewayException(String message) {
        super(message);
    }

    public FacebookGatewayException(String message, Throwable cause) {
        super(message, cause);
    }
}
