package com.microsoft.applicationinsights.agent.internal.agent.model;

import com.microsoft.applicationinsights.agent.internal.agent.model.telemetry.AppInsightsTransactionBuilder;
import com.microsoft.applicationinsights.agent.internal.agent.model.telemetry.AppInsightsTransactionBuilder.DependencyTelemetryBuilder;
import com.microsoft.applicationinsights.agent.internal.agent.utils.ConsoleOutputHelperForTesting;
import com.microsoft.applicationinsights.telemetry.Telemetry;
import org.glowroot.instrumentation.api.QueryEntry;
import org.glowroot.instrumentation.api.QueryMessageSupplier;
import org.glowroot.instrumentation.api.Timer;

import java.util.concurrent.TimeUnit;

public class QueryEntryImpl extends TraceEntryImpl implements QueryEntry {

    private final ConsoleOutputHelperForTesting out = new ConsoleOutputHelperForTesting(QueryEntryImpl.class);

    public QueryEntryImpl(AppInsightsTransactionBuilder tx, Object queryMessageSupplier) {
        super(tx, queryMessageSupplier);
    }

    @Override
    protected ConsoleOutputHelperForTesting out() {
        return this.out;
    }

    @Override
    public void rowNavigationAttempted() {
        // TODO
        out().logMethod("rowNavigationAttempted");
    }

    @Override
    public void incrementCurrRow() {
        // TODO
        out().logMethod("incrementCurrRow");
    }

    @Override
    public void setCurrRow(long row) {
        // TODO
        out().logMethod("setCurrRow", "row=%d", row);
    }
}
