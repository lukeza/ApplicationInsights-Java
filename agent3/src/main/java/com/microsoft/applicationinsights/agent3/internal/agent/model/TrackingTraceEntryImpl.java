package com.microsoft.applicationinsights.agent3.internal.agent.model;

import com.microsoft.applicationinsights.agent3.internal.agent.model.telemetry.spi.TransactionSender;
import com.microsoft.applicationinsights.agent3.internal.agent.model.telemetry.spi.appinsights.BaseAppInsightsTxBuilder;
import com.microsoft.applicationinsights.agent3.internal.agent.utils.dev.DevLogger;
import com.microsoft.applicationinsights.telemetry.Telemetry;
import org.glowroot.instrumentation.api.MessageSupplier;
import org.glowroot.instrumentation.api.TimerName;

public class TrackingTraceEntryImpl extends TraceEntryImpl {

    private static final DevLogger out = new DevLogger(TrackingTraceEntryImpl.class);
    private final TransactionSender<Telemetry> sender;

    public TrackingTraceEntryImpl(BaseAppInsightsTxBuilder tx, TransactionSender<Telemetry> sender, MessageSupplier messageSupplier,
                                  /*@Nullable*/ TimerName timerName) {
        super(tx, messageSupplier, timerName);
        this.sender = sender;
    }

    @Override
    protected void done(boolean success) {
        super.done(success);
        // TODO superclass calls cleanUp; is that ok?
        out.info("done "+success);
        tx.finish(this.sender);
    }
}
