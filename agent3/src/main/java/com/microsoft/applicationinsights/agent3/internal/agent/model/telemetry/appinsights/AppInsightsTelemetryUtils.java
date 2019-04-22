package com.microsoft.applicationinsights.agent3.internal.agent.model.telemetry.appinsights;

import com.microsoft.applicationinsights.telemetry.TelemetryContext;

public class AppInsightsTelemetryUtils {
    private AppInsightsTelemetryUtils(){}
    static void propagateOperationId(TelemetryContext parent, TelemetryContext child) {
        child.getOperation().setParentId(parent.getOperation().getId());
    }
}
