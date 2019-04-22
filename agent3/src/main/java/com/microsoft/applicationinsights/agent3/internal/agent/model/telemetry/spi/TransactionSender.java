package com.microsoft.applicationinsights.agent3.internal.agent.model.telemetry.spi;

public interface TransactionSender<P> {
    void send(P product);
}
