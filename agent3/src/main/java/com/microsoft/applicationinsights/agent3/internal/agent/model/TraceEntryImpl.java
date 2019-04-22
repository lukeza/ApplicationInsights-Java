package com.microsoft.applicationinsights.agent3.internal.agent.model;

import com.microsoft.applicationinsights.agent3.internal.agent.model.telemetry.spi.appinsights.BaseAppInsightsTxBuilder;
import com.microsoft.applicationinsights.agent3.internal.agent.utils.dev.DevLogger;
import org.glowroot.engine.impl.NopTransactionService;
import org.glowroot.instrumentation.api.AsyncTraceEntry;
import org.glowroot.instrumentation.api.MessageSupplier;
import org.glowroot.instrumentation.api.ThreadContext;
import org.glowroot.instrumentation.api.Timer;
import org.glowroot.instrumentation.api.TimerName;

import java.util.concurrent.TimeUnit;

public class TraceEntryImpl implements AsyncTraceEntry {
    private final MessageSupplier messageSupplier;
    protected final BaseAppInsightsTxBuilder tx;

    private static final DevLogger out = new DevLogger(TraceEntryImpl.class);
    private final TimerName timerName;
    private boolean reportExceptionsOnSuccess = true;


    public TraceEntryImpl(BaseAppInsightsTxBuilder tx, MessageSupplier messageSupplier, TimerName timerName) {
        this.messageSupplier = messageSupplier;
        this.tx = tx;
        this.timerName = timerName;
    }

    protected void done(boolean success) {
        // TODO
        tx.addTraceData(getMessageSupplier(), timerName);
        tx.setEndTime(System.currentTimeMillis());
        cleanUp();
    }

    private void done(Throwable t) {
        out.info("done", t);
        // TODO
        done(t, false);
    }

    private void done(Throwable t, boolean success) {
        out.info("done", t);
        // TODO
        if (!success && reportExceptionsOnSuccess) {
            // TODO create exception telemetry; where to store it?
        }
        done(success);
    }

    /**
     * Override to perform any cleanup operations after ending trace entry.
     */
    protected void cleanUp() {
        // default impl nop
    }

    @Override
    public void end() {
        // TODO
        done(true);
    }

    @Override
    public void endWithLocationStackTrace(long threshold, TimeUnit unit) {
        // TODO
        out.info("endWithLocationStackTrace: threshold=%d %s", threshold, unit.toString());
        done(true); // TODO is this successful?
    }

    @Override
    public void endWithError(Throwable t) {
        // TODO
        out.info("endWithError: t=%s", t.getClass().getSimpleName());
        done(t);
    }

    @Override
    public void endWithError(String message) {
        // TODO
        out.info("endWithError: msg=%s", message);
        done(false);
    }

    @Override
    public void endWithError(String message, Throwable t) {
        // TODO
        out.info("endWithError: msg=%s, t=%s", message, t.getClass().getSimpleName());
        done(t);
    }

    @Override
    public void endWithInfo(Throwable t) {
        // TODO
        out.info("endWithInfo: t=%s", t.getClass().getSimpleName());
        done(t, true); // chomp t ?
    }

    @Override
    public Timer extend() {
        out.info("extend");
        return NopTransactionService.TIMER;
    }

    @Override
    public MessageSupplier getMessageSupplier() {
        return messageSupplier;
    }

    @Override
    public void stopSyncTimer() {
        // TODO
    }

    @Override
    public Timer extendSyncTimer(ThreadContext currThreadContext) {
        // TODO
        return NopTransactionService.TIMER;
    }
}
