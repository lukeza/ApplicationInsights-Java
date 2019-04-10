package com.microsoft.applicationinsights.agent.internal.agent.model;

import com.microsoft.applicationinsights.agent.internal.agent.model.telemetry.AppInsightsTransactionBuilder;
import com.microsoft.applicationinsights.agent.internal.agent.utils.ConsoleOutputHelperForTesting;
import org.glowroot.engine.impl.NopTransactionService;
import org.glowroot.instrumentation.api.Timer;
import org.glowroot.instrumentation.api.TraceEntry;

import java.util.concurrent.TimeUnit;

public class TraceEntryImpl implements TraceEntry {
    private final Object messageSupplier;
    private final AppInsightsTransactionBuilder tx;

    private final ConsoleOutputHelperForTesting out = new ConsoleOutputHelperForTesting(TraceEntryImpl.class);

    public TraceEntryImpl(AppInsightsTransactionBuilder tx, Object messageSupplier) {
        out().logMethod("<init>");
        this.messageSupplier = messageSupplier;
        this.tx = tx;
    }

    protected ConsoleOutputHelperForTesting out() {
        return this.out;
    }

    private void done() {
        // TODO
        cleanUp();
    }

    private void done(Throwable t) {
        // TODO
        cleanUp();
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
        out().logMethod("end");
        done();
    }

    @Override
    public void endWithLocationStackTrace(long threshold, TimeUnit unit) {
        // TODO
        out().logMethod("endWithLocationStackTrace", "threshold=%d %s", threshold, unit.toString());
        done();
    }

    @Override
    public void endWithError(Throwable t) {
        // TODO
        out().logMethod("endWithError", "t=%s", t.getClass().getSimpleName());
        done(t);
    }

    @Override
    public void endWithError(String message) {
        // TODO
        out().logMethod("endWithError", "msg=%s", message);
        done();
    }

    @Override
    public void endWithError(String message, Throwable t) {
        // TODO
        out().logMethod("endWithError", "msg=%s, t=%s", message, t.getClass().getSimpleName());
        done(t);
    }

    @Override
    public void endWithInfo(Throwable t) {
        // TODO
        out().logMethod("endWithInfo", "t=%s", t.getClass().getSimpleName());
        done(); // chomp t ?
    }

    @Override
    public Timer extend() {
        out().logMethod("extend");
        return NopTransactionService.TIMER;
    }

    @Override
    public Object getMessageSupplier() {
        return messageSupplier;
    }
}
