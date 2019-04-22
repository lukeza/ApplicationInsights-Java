package com.microsoft.applicationinsights.agent3.internal.agent.model.telemetry.spi.appinsights;

import com.microsoft.applicationinsights.TelemetryClient;
import com.microsoft.applicationinsights.agent3.internal.agent.model.telemetry.spi.TransactionSender;
import com.microsoft.applicationinsights.agent3.internal.agent.utils.dev.DevLogger;
import com.microsoft.applicationinsights.telemetry.Telemetry;

public class AppInsightsSender implements TransactionSender<Telemetry> {
    private final TelemetryClient client;

    private static final DevLogger out = new DevLogger(AppInsightsSender.class);


    public AppInsightsSender(TelemetryClient client) {
        out.info("<init>");
        this.client = client;
    }

    @Override
    public void send(Telemetry product) {
        out.info("send "+product.getClass().getSimpleName());
        client.track(product);
    }
}
