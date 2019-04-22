package com.microsoft.applicationinsights.agent3.internal.agent.model.telemetry.spi;

public interface TransactionContext {
    TransactionContext nextChild();
}
