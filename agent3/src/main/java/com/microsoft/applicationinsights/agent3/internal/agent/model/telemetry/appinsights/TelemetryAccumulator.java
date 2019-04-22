package com.microsoft.applicationinsights.agent3.internal.agent.model.telemetry.appinsights;

import com.microsoft.applicationinsights.telemetry.BaseTelemetry;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class TelemetryAccumulator implements Iterable<BaseAppInsightsTelemetryBuilder<BaseTelemetry<?>>> {
    private final List<BaseAppInsightsTelemetryBuilder<BaseTelemetry<?>>> telemetries;
    TelemetryAccumulator() {
        this.telemetries = new ArrayList<>();
    }

    public boolean add(BaseAppInsightsTelemetryBuilder<BaseTelemetry<?>> telemetry) {
        return telemetries.add(telemetry);
    }

    @Override
    public Iterator<BaseAppInsightsTelemetryBuilder<BaseTelemetry<?>>> iterator() {
        return telemetries.iterator();
    }
}
