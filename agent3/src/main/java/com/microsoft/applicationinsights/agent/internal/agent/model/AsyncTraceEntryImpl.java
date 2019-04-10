package com.microsoft.applicationinsights.agent.internal.agent.model;

import com.microsoft.applicationinsights.agent.internal.agent.model.telemetry.AppInsightsTransactionBuilder;
import com.microsoft.applicationinsights.agent.internal.agent.utils.ConsoleOutputHelperForTesting;
import org.glowroot.engine.impl.NopTransactionService;
import org.glowroot.instrumentation.api.AsyncTraceEntry;
import org.glowroot.instrumentation.api.ThreadContext;
import org.glowroot.instrumentation.api.Timer;

public class AsyncTraceEntryImpl extends TraceEntryImpl implements AsyncTraceEntry {

    private final ConsoleOutputHelperForTesting out = new ConsoleOutputHelperForTesting(AsyncTraceEntryImpl.class);

    public AsyncTraceEntryImpl(AppInsightsTransactionBuilder mainBuilder, Object messageSupplier) {
        super(mainBuilder, messageSupplier);
    }

    @Override
    protected ConsoleOutputHelperForTesting out() {
        return this.out;
    }

    @Override
    public void stopSyncTimer() {
        // TODO
        out().logMethod("stopSyncTimer");
    }

    @Override
    public Timer extendSyncTimer(ThreadContext currThreadContext) {
        // TODO
        out().logMethod("extendSyncTimer");
        return NopTransactionService.TIMER;
    }
}
