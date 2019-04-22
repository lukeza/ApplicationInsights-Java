package com.microsoft.applicationinsights.agent3.internal.agent.model;

import com.microsoft.applicationinsights.agent3.internal.agent.model.telemetry.spi.appinsights.AppInsightsSender;
import com.microsoft.applicationinsights.agent3.internal.agent.model.telemetry.spi.appinsights.BaseAppInsightsTxBuilder;
import com.microsoft.applicationinsights.agent3.internal.agent.utils.dev.DevLogger;
import org.glowroot.engine.bytecode.api.ThreadContextThreadLocal;
import org.glowroot.engine.bytecode.api.ThreadContextThreadLocal.Holder;
import org.glowroot.instrumentation.api.MessageSupplier;

public class RootTraceEntryImpl extends TrackingTraceEntryImpl {
    private final ThreadContextThreadLocal.Holder tctlHolder;

    private static final DevLogger out = new DevLogger(RootTraceEntryImpl.class);

    public RootTraceEntryImpl(BaseAppInsightsTxBuilder tx, AppInsightsSender sender, MessageSupplier messageSupplier,
                              Holder tctlHolder) {
        super(tx, sender, messageSupplier, null);
        this.tctlHolder = tctlHolder;
    }

    @Override
    protected void cleanUp() {
        tctlHolder.set(null);
    }
}
