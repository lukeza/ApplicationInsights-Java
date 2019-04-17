package com.microsoft.applicationinsights.agent3.internal.agent.model;

import com.microsoft.applicationinsights.agent3.internal.agent.model.telemetry.AppInsightsTransactionBuilder;
import com.microsoft.applicationinsights.agent3.internal.agent.utils.DevLogger;
import org.glowroot.engine.impl.NopTransactionService;
import org.glowroot.instrumentation.api.AsyncQueryEntry;
import org.glowroot.instrumentation.api.QueryMessageSupplier;
import org.glowroot.instrumentation.api.ThreadContext;
import org.glowroot.instrumentation.api.Timer;

public class AsyncQueryEntryImpl extends QueryEntryImpl implements AsyncQueryEntry {

    private static final DevLogger out = new DevLogger(AsyncQueryEntryImpl.class);

    public AsyncQueryEntryImpl(AppInsightsTransactionBuilder tx, QueryMessageSupplier queryMessageSupplier) {
        super(tx, queryMessageSupplier);
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
