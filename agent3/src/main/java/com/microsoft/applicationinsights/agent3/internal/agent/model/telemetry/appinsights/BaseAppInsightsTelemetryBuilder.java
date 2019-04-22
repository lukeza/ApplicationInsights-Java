package com.microsoft.applicationinsights.agent3.internal.agent.model.telemetry.appinsights;

import com.microsoft.applicationinsights.TelemetryClient;
import com.microsoft.applicationinsights.telemetry.BaseTelemetry;
import com.microsoft.applicationinsights.telemetry.TelemetryContext;

public abstract class BaseAppInsightsTelemetryBuilder<T extends BaseTelemetry<?>> implements AppInsightsTelemetryBuilder {
    private final T telemetry;

    protected BaseAppInsightsTelemetryBuilder(T telemetry) {
        this.telemetry = telemetry;
    }

    /**
     * @throws InvalidTelemetryException
     */
    protected abstract void validate();

    protected T getTelemetry() {
        return this.telemetry;
    }

    protected TelemetryContext getContext() {
        return getTelemetry().getContext();
    }

    protected T build() {
        validate();
        return telemetry;
    }

    public void track(TelemetryClient telemetryClient) {
        telemetryClient.track(build());
    }

    public ExceptionTelemetryBuilder generatedException(Throwable t) {
        final ExceptionTelemetryBuilder telemetryBuilder = new ExceptionTelemetryBuilder(t);
        AppInsightsTelemetryUtils.propagateOperationId(getContext(), telemetryBuilder.getContext());
        return telemetryBuilder;
    }
}
