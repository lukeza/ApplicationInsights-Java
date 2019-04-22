package com.microsoft.applicationinsights.agent3.internal.agent.model.telemetry.appinsights;

public class InvalidTelemetryException extends RuntimeException {

    public InvalidTelemetryException(String s) {
        super(s);
    }
}
