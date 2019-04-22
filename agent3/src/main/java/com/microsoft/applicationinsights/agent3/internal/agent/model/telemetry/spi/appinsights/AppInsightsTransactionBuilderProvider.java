package com.microsoft.applicationinsights.agent3.internal.agent.model.telemetry.spi.appinsights;

import com.microsoft.applicationinsights.agent3.internal.agent.model.telemetry.spi.TransactionBuilder;
import com.microsoft.applicationinsights.agent3.internal.agent.model.telemetry.spi.TransactionBuilderProvider;
import com.microsoft.applicationinsights.agent3.internal.agent.model.telemetry.spi.TransactionSender;
import com.microsoft.applicationinsights.agent3.internal.agent.utils.dev.DevLogger;
import com.microsoft.applicationinsights.telemetry.Duration;
import com.microsoft.applicationinsights.telemetry.Telemetry;
import org.glowroot.instrumentation.api.ThreadContext.ServletRequestInfo;
import org.glowroot.instrumentation.api.TimerName;

import java.util.HashMap;
import java.util.Map;

public class AppInsightsTransactionBuilderProvider implements TransactionBuilderProvider<AppInsightsTransactionContext, Telemetry> {
    private static final AppInsightsTransactionBuilderProvider INSTANCE = new AppInsightsTransactionBuilderProvider();

    private final Map<String, BaseAppInsightsTxBuilder> builders = new HashMap<>();

    private static final DevLogger out = new DevLogger(AppInsightsTransactionBuilderProvider.class);

    private AppInsightsTransactionBuilderProvider() {
        registerBuilder("web", new RequestTelemetryTransactionBuilder());
        registerBuilder("http", new DependencyTelemetryTransactionBuilder());
    }



    public static AppInsightsTransactionBuilderProvider getInstance() {
        return INSTANCE;
    }

    @Override
    public BaseAppInsightsTxBuilder getTransactionBuilder(String txName) {
        BaseAppInsightsTxBuilder builder = builders.get(txName.toLowerCase());
        if (builder == null) {
            builder = NOP_BUILDER;
        }
        out.info("getBuilder '"+txName+"' -> "+builder.getClass().getSimpleName());
        return builder;
    }

    // TODO find a better option than this
    private static final BaseAppInsightsTxBuilder NOP_BUILDER = new NopTxBuilder();

    @Override
    public BaseAppInsightsTxBuilder unregisterBuilder(String txName) {
        return builders.remove(txName);
    }

    @Override
    public BaseAppInsightsTxBuilder registerBuilder(String txName, TransactionBuilder<AppInsightsTransactionContext, Telemetry> builder) {
        // TODO avoid cast?
        return builders.put(txName.toLowerCase(), (BaseAppInsightsTxBuilder) builder);
    }

    public static class NopTxBuilder extends BaseAppInsightsTxBuilder {

        private NopTxBuilder() {}

        @Override
        public void addTraceData(Object messageSupplier, TimerName timerName) {

        }

        @Override
        public void setName(String name) {

        }

        @Override
        protected void beforeSend() {

        }

        @Override
        protected Telemetry getTelemetry() {
            return null;
        }

        @Override
        public void finish(TransactionSender<Telemetry> sender) {

        }

        @Override
        protected Duration computeDuration() {
            return super.computeDuration();
        }

        @Override
        public void setServletRequestInfo(ServletRequestInfo requestInfo) {
        }

        @Override
        protected boolean isValid() {
            return true;
        }
    }
}
