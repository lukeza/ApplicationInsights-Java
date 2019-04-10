package com.microsoft.applicationinsights.agent.internal.agent;

import com.microsoft.applicationinsights.TelemetryClient;
import com.microsoft.applicationinsights.agent.internal.agent.model.ThreadContextImpl;
import com.microsoft.applicationinsights.agent.internal.agent.model.TraceEntryImpl;
import com.microsoft.applicationinsights.agent.internal.agent.model.telemetry.AppInsightsTransactionBuilder;
import com.microsoft.applicationinsights.agent.internal.agent.utils.ConsoleOutputHelperForTesting;
import org.glowroot.engine.bytecode.api.ThreadContextThreadLocal;
import org.glowroot.engine.bytecode.api.ThreadContextThreadLocal.Holder;
import org.glowroot.engine.weaving.AgentSPI;
import org.glowroot.instrumentation.api.MessageSupplier;
import org.glowroot.instrumentation.api.TimerName;
import org.glowroot.instrumentation.api.TraceEntry;

public class ApplicationInsightsAgentImpl implements AgentSPI {

//    private TelemetryClient telemetryClient;

    private final ConsoleOutputHelperForTesting out = new ConsoleOutputHelperForTesting(ApplicationInsightsAgentImpl.class);

    ApplicationInsightsAgentImpl() {
        out.logMethod("<init>");
//        telemetryClient = new TelemetryClient();
    }

    @Override
    public TraceEntry startTransaction(String transactionType, String transactionName,
                                       MessageSupplier messageSupplier, TimerName timerName, Holder threadContextHolder,
                                       int rootNestingGroupId, int rootSuppressionKeyId) {
        out.logMethod("startTransaction", "type=%s, name=%s", transactionType, transactionName);
        final AppInsightsTransactionBuilder operationBuilder =
                AppInsightsTransactionBuilder.startOperation()
                .setTransactionName(transactionName);
        ThreadContextImpl mainThreadContext = new ThreadContextImpl(operationBuilder);
        threadContextHolder.set(mainThreadContext);

        return new RootTraceEntryImpl(operationBuilder, messageSupplier, threadContextHolder);
    }

    private static class RootTraceEntryImpl extends TraceEntryImpl {
        private final ThreadContextThreadLocal.Holder tctlHolder;

        public RootTraceEntryImpl(AppInsightsTransactionBuilder tx, Object messageSupplier,
                                  ThreadContextThreadLocal.Holder tctlHolder) {
            super(tx, messageSupplier);
            this.tctlHolder = tctlHolder;
        }

        @Override
        protected void cleanUp() {
            tctlHolder.set(null);
        }
    }

}
