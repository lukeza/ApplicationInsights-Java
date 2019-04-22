package com.microsoft.applicationinsights.agent3.internal.agent.model.telemetry.appinsights;

import com.microsoft.applicationinsights.TelemetryClient;
import com.microsoft.applicationinsights.telemetry.BaseTelemetry;
import com.microsoft.applicationinsights.telemetry.TelemetryContext;

import static com.microsoft.applicationinsights.agent3.internal.agent.model.telemetry.appinsights.AppInsightsTelemetryUtils.propagateOperationId;

public class AppInsightsTransactionBuilder {
    private TelemetryContext txContext;
    private final TelemetryAccumulator accumulator;
    private String transactionName;

    private AppInsightsTransactionBuilder() {
        this.accumulator = new TelemetryAccumulator();
    }

    public static AppInsightsTransactionBuilder startOperation() {
        return new AppInsightsTransactionBuilder();
    }

    public RequestTelemetryBuilder startRequest() {
        final RequestTelemetryBuilder telemetryBuilder = new RequestTelemetryBuilder(System.currentTimeMillis());
        txContext = telemetryBuilder.getContext();
        return telemetryBuilder;
    }

    public DependencyTelemetryBuilder startDependency() {
        final DependencyTelemetryBuilder telemetryBuilder = new DependencyTelemetryBuilder(System.currentTimeMillis());
        propagateOperationId(txContext, telemetryBuilder.getContext());
        return telemetryBuilder;
    }

    public ExceptionTelemetryBuilder startException() {
        final ExceptionTelemetryBuilder telemetryBuilder = new ExceptionTelemetryBuilder();
        propagateOperationId(txContext, telemetryBuilder.getContext());
        return telemetryBuilder;
    }

    public void trackAll(TelemetryClient telemetryClient) {
        for (BaseAppInsightsTelemetryBuilder<BaseTelemetry<?>> telemetry : accumulator) {
            telemetry.track(telemetryClient);
        }
    }

    public AppInsightsTransactionBuilder setTransactionName(String transactionName) {
        this.transactionName = transactionName;
        return this;
    }

    public String getTransactionName() {
        return this.transactionName;
    }

}
