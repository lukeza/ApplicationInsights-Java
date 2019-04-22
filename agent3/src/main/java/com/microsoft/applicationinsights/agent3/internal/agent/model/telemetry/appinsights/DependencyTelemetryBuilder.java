package com.microsoft.applicationinsights.agent3.internal.agent.model.telemetry.appinsights;

import com.microsoft.applicationinsights.telemetry.Duration;
import com.microsoft.applicationinsights.telemetry.RemoteDependencyTelemetry;

public class DependencyTelemetryBuilder extends TimedTelemetryBuilder<RemoteDependencyTelemetry> {

    DependencyTelemetryBuilder(long startTickMs) {
        super(new RemoteDependencyTelemetry(), startTickMs);
    }

    @Override
    protected void setDuration(RemoteDependencyTelemetry telemetry, Duration duration) {
        telemetry.setDuration(duration);
    }

    @Override
    protected void doValidate() {
        // TODO
    }
}
