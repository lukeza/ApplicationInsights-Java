package com.microsoft.applicationinsights.agent3.internal.agent;

import com.microsoft.applicationinsights.TelemetryClient;
import com.microsoft.applicationinsights.agent3.internal.agent.model.RootTraceEntryImpl;
import com.microsoft.applicationinsights.agent3.internal.agent.model.ThreadContextImpl;
import com.microsoft.applicationinsights.agent3.internal.agent.model.telemetry.spi.appinsights.AppInsightsSender;
import com.microsoft.applicationinsights.agent3.internal.agent.model.telemetry.spi.appinsights.AppInsightsTransactionBuilderProvider;
import com.microsoft.applicationinsights.agent3.internal.agent.model.telemetry.spi.appinsights.AppInsightsTransactionContext;
import com.microsoft.applicationinsights.agent3.internal.agent.model.telemetry.spi.appinsights.BaseAppInsightsTxBuilder;
import com.microsoft.applicationinsights.agent3.internal.agent.utils.dev.DevLogger;
import org.glowroot.engine.bytecode.api.ThreadContextThreadLocal.Holder;
import org.glowroot.engine.weaving.AgentSPI;
import org.glowroot.instrumentation.api.MessageSupplier;
import org.glowroot.instrumentation.api.TimerName;
import org.glowroot.instrumentation.api.TraceEntry;

public class ApplicationInsightsAgentImpl implements AgentSPI {

    private final AppInsightsSender sender;

    private static final DevLogger out = new DevLogger(ApplicationInsightsAgentImpl.class);

    ApplicationInsightsAgentImpl() {
        TelemetryClient client = new TelemetryClient();
        client.trackEvent("Glow Agent Init");
        out.info("tracked init event");
        sender = new AppInsightsSender(client);
    }

    @Override
    public TraceEntry startTransaction(String transactionType, String transactionName,
                                       final MessageSupplier messageSupplier, TimerName timerName, Holder threadContextHolder,
                                       int rootNestingGroupId, int rootSuppressionKeyId) {

        final BaseAppInsightsTxBuilder transactionBuilder = AppInsightsTransactionBuilderProvider.getInstance().getTransactionBuilder(transactionType);

        transactionBuilder.setTransactionContext(AppInsightsTransactionContext.createNew());
        transactionBuilder.setStartTime(System.currentTimeMillis());
        transactionBuilder.setName(transactionName);

        ThreadContextImpl mainThreadContext = new ThreadContextImpl(transactionBuilder, sender, rootNestingGroupId, rootSuppressionKeyId);
        threadContextHolder.set(mainThreadContext);

        return new RootTraceEntryImpl(transactionBuilder, sender, messageSupplier, threadContextHolder);
    }

}
