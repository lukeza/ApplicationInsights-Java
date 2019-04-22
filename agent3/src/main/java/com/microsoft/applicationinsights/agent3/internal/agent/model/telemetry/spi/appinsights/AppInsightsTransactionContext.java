package com.microsoft.applicationinsights.agent3.internal.agent.model.telemetry.spi.appinsights;

import com.microsoft.applicationinsights.agent3.internal.agent.model.telemetry.spi.TransactionContext;

import java.util.UUID;

public class AppInsightsTransactionContext implements TransactionContext {
    private UUID parentId; // TODO if root, should parentId == null or parentId == traceId?
    private UUID traceId;

    private AppInsightsTransactionContext(UUID parentId, UUID traceId) {
        this.parentId = parentId;
        this.traceId = traceId;
    }

    public static AppInsightsTransactionContext createNew() {
        final UUID uuid = UUID.randomUUID();
        return new AppInsightsTransactionContext(uuid, uuid);
    }

    public static AppInsightsTransactionContext createChild(UUID parentId) {
        return new AppInsightsTransactionContext(parentId, UUID.randomUUID());
    }

    public UUID getParentId() {
        return parentId;
    }

    public void setParentId(UUID parentId) {
        this.parentId = parentId;
    }

    public UUID getTraceId() {
        return traceId;
    }

    public void setTraceId(UUID traceId) {
        this.traceId = traceId;
    }

    @Override
    public AppInsightsTransactionContext nextChild() {
        return createChild(getTraceId());
    }
}
