package com.microsoft.applicationinsights.agent3.internal.agent.model.telemetry.appinsights;

import com.microsoft.applicationinsights.telemetry.ExceptionTelemetry;

public class ExceptionTelemetryBuilder extends BaseAppInsightsTelemetryBuilder<ExceptionTelemetry> {

    public ExceptionTelemetryBuilder() {
        super(new ExceptionTelemetry());
    }

    public ExceptionTelemetryBuilder(Throwable t) {
        super(new ExceptionTelemetry(t));
    }

    @Override
    protected void validate() {
        // TODO
    }
}
