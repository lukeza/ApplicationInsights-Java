package com.microsoft.applicationinsights.agent3.internal.agent.model.telemetry.spi.appinsights;

import com.microsoft.applicationinsights.agent3.internal.agent.model.telemetry.spi.TransactionBuilder;
import com.microsoft.applicationinsights.agent3.internal.agent.model.telemetry.spi.TransactionSender;
import com.microsoft.applicationinsights.telemetry.Duration;
import com.microsoft.applicationinsights.telemetry.Telemetry;
import org.glowroot.instrumentation.api.MessageSupplier;
import org.glowroot.instrumentation.api.ThreadContext.ServletRequestInfo;
import org.glowroot.instrumentation.api.internal.ReadableMessage;

import java.util.Date;

public abstract class BaseAppInsightsTxBuilder implements TransactionBuilder<AppInsightsTransactionContext, Telemetry> {

    protected AppInsightsTransactionContext context;
    private long startTime = -1;
    private long endTime = -1;

    @Override
    public void setTransactionContext(AppInsightsTransactionContext context) {
        this.context = context;
    }

    @Override
    public AppInsightsTransactionContext getTransactionContext() {
        return this.context;
    }

    @Override
    public void finish(TransactionSender<Telemetry> sender) {
        if (context == null) {
            throw new IllegalStateException("null context");
        }
        addTimestamp();
        beforeSend();
        if (isValid()) {
            sender.send(getTelemetry());
        }
    }

    protected static ReadableMessage getTraceMessage(Object messageSupplier) {
        if (messageSupplier instanceof MessageSupplier) {

        }
        throw new IllegalStateException("unknown messageSupplier type: "+messageSupplier.getClass());
    }

    protected void addTimestamp() {
        getTelemetry().setTimestamp(new Date(getStartTime()));
    }

    protected Duration computeDuration() {
        return new Duration(getEndTime() - getStartTime());
    }

    @Override
    public void setServletRequestInfo(ServletRequestInfo requestInfo) {
        // NOP
    }

    protected abstract void beforeSend();

    protected abstract Telemetry getTelemetry();

    protected abstract boolean isValid();

    @Override
    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    @Override
    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    protected long getStartTime() {
        return startTime;
    }

    protected long getEndTime() {
        return endTime;
    }
}
