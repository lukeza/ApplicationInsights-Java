package com.microsoft.applicationinsights.agent3.internal.agent.model.telemetry.appinsights;

import com.microsoft.applicationinsights.telemetry.Duration;
import com.microsoft.applicationinsights.telemetry.RequestTelemetry;

public class RequestTelemetryBuilder extends TimedTelemetryBuilder<RequestTelemetry> {
    RequestTelemetryBuilder(long startTickMs) {
        super(new RequestTelemetry(), startTickMs);
    }

    @Override
    protected void setDuration(RequestTelemetry telemetry, Duration duration) {
        telemetry.setDuration(duration);
    }

    @Override
    protected void doValidate() {
        // TODO
    }
}
