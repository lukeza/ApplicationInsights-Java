package com.microsoft.applicationinsights.agent3.internal.agent.model.telemetry.appinsights;

import com.microsoft.applicationinsights.TelemetryClient;

public interface AppInsightsTelemetryBuilder {
    void track(TelemetryClient telemetryClient);
}
