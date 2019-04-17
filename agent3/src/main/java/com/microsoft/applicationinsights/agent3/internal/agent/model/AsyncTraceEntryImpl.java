package com.microsoft.applicationinsights.agent3.internal.agent.model;

import com.microsoft.applicationinsights.agent3.internal.agent.model.telemetry.AppInsightsTransactionBuilder;
import com.microsoft.applicationinsights.agent3.internal.agent.utils.DevLogger;
import org.glowroot.engine.impl.NopTransactionService;
import org.glowroot.instrumentation.api.AsyncTraceEntry;
import org.glowroot.instrumentation.api.ThreadContext;
import org.glowroot.instrumentation.api.Timer;

public class AsyncTraceEntryImpl extends TraceEntryImpl implements AsyncTraceEntry {

    private static final DevLogger out = new DevLogger(AsyncTraceEntryImpl.class);

    public AsyncTraceEntryImpl(AppInsightsTransactionBuilder mainBuilder, Object messageSupplier) {
        super(mainBuilder, messageSupplier);
    }

    @Override
    public void stopSyncTimer() {
        // TODO
        out.info("stopSyncTimer");
    }

    @Override
    public Timer extendSyncTimer(ThreadContext currThreadContext) {
        // TODO
        out.info("extendSyncTimer");
        return NopTransactionService.TIMER;
    }
}
