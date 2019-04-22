package com.microsoft.applicationinsights.agent3.internal.agent.model.telemetry.spi;

import org.glowroot.instrumentation.api.ThreadContext.ServletRequestInfo;
import org.glowroot.instrumentation.api.TimerName;

/**
 *
 * @param <C> Context type
 * @param <P> Product type
 */
public interface TransactionBuilder<C extends TransactionContext, P> {
    void setTransactionContext(C context); // context will hold the time
    C getTransactionContext();
    void addTraceData(Object messageSupplier, TimerName timerName);
    void setName(String name);
    void setServletRequestInfo(ServletRequestInfo requestInfo);
    void finish(TransactionSender<P> sender);

    void setStartTime(long startTime);
    void setEndTime(long endTime);
}
