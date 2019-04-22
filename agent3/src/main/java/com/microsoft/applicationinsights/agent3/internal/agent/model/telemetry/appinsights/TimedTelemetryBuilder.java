package com.microsoft.applicationinsights.agent3.internal.agent.model.telemetry.appinsights;

import com.microsoft.applicationinsights.telemetry.BaseTelemetry;
import com.microsoft.applicationinsights.telemetry.Duration;

public abstract class TimedTelemetryBuilder<T extends BaseTelemetry<?>> extends BaseAppInsightsTelemetryBuilder<T> {
    private long startTickMs;
    private long endTickMs;

    protected TimedTelemetryBuilder(T telemetry, long startTickMs) {
        super(telemetry);
        setStartTick(startTickMs);
    }

    protected abstract void setDuration(T telemetry, Duration duration);

    protected abstract void doValidate();

    public void setStartTick(long startTickMs) {
        this.startTickMs = startTickMs;
    }

    public void setEndTick(long endTimeMs) {
        this.endTickMs = endTimeMs;
    }

    @Override
    public T build() {
        setDuration(getTelemetry(), new Duration(endTickMs - startTickMs));
        return super.build();
    }

    @Override
    protected final void validate() {
        if (endTickMs < 0) {
            throw new InvalidTelemetryException("endTime must be set/positive");
        }
        doValidate();
    }
}
