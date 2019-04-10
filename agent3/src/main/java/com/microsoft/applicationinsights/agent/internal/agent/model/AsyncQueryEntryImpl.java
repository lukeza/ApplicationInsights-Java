package com.microsoft.applicationinsights.agent.internal.agent.model;

import com.microsoft.applicationinsights.agent.internal.agent.model.telemetry.AppInsightsTransactionBuilder;
import com.microsoft.applicationinsights.agent.internal.agent.model.telemetry.AppInsightsTransactionBuilder.DependencyTelemetryBuilder;
import com.microsoft.applicationinsights.agent.internal.agent.utils.ConsoleOutputHelperForTesting;
import com.microsoft.applicationinsights.telemetry.Telemetry;
import org.glowroot.engine.impl.NopTransactionService;
import org.glowroot.instrumentation.api.AsyncQueryEntry;
import org.glowroot.instrumentation.api.QueryMessageSupplier;
import org.glowroot.instrumentation.api.ThreadContext;
import org.glowroot.instrumentation.api.Timer;

public class AsyncQueryEntryImpl extends QueryEntryImpl implements AsyncQueryEntry {

    private final ConsoleOutputHelperForTesting out = new ConsoleOutputHelperForTesting(AsyncQueryEntryImpl.class);

    public AsyncQueryEntryImpl(AppInsightsTransactionBuilder tx, QueryMessageSupplier queryMessageSupplier) {
        super(tx, queryMessageSupplier);
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
