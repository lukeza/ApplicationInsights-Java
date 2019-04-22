package com.microsoft.applicationinsights.agent3.internal.agent.model;

import com.microsoft.applicationinsights.agent3.internal.agent.model.telemetry.spi.appinsights.BaseAppInsightsTxBuilder;
import com.microsoft.applicationinsights.agent3.internal.agent.utils.dev.DevLogger;
import org.glowroot.engine.impl.NopTransactionService;
import org.glowroot.instrumentation.api.AsyncQueryEntry;
import org.glowroot.instrumentation.api.QueryMessage;
import org.glowroot.instrumentation.api.QueryMessageSupplier;
import org.glowroot.instrumentation.api.ThreadContext;
import org.glowroot.instrumentation.api.Timer;
import org.glowroot.instrumentation.api.internal.ReadableQueryMessage;

import java.util.concurrent.TimeUnit;

public class QueryEntryImpl implements AsyncQueryEntry {

    private static final DevLogger out = new DevLogger(QueryEntryImpl.class);
    private final QueryMessageSupplier messageSupplier;
    private final BaseAppInsightsTxBuilder tx;

    public QueryEntryImpl(BaseAppInsightsTxBuilder tx, QueryMessageSupplier queryMessageSupplier) {
        // TODO
        out.info("<init>");
        this.messageSupplier = queryMessageSupplier;
        this.tx = tx;
    }

    @Override
    public void rowNavigationAttempted() {
        // TODO
        out.info("rowNavigationAttempted");
    }

    @Override
    public void incrementCurrRow() {
        // TODO
        out.info("incrementCurrRow");
    }

    @Override
    public void setCurrRow(long row) {
        // TODO
        out.info("setCurrRow: row=%d", row);
    }

    private ReadableQueryMessage getQueryMessage() {
        Object o = getMessageSupplier();
        if (o instanceof QueryMessageSupplier) {
            QueryMessageSupplier qms = (QueryMessageSupplier) o;
            QueryMessage qmsg = qms.get();
            if (qmsg != null) {
                if (qmsg instanceof ReadableQueryMessage) {
                    return (ReadableQueryMessage) qmsg;
                } else {
                    out.info("UNEXPECTED MESSAGE TYPE: %s returned a %s", qms.getClass().getSimpleName(), qmsg.getClass().getSimpleName());
                }
            }
        } else {
            out.info("%s had a %s; but expected %s", QueryEntryImpl.class.getSimpleName(), o.getClass().getSimpleName(), QueryMessageSupplier.class.getSimpleName());
        }
        return null;
    }

    @Override
    public void stopSyncTimer() {

    }

    @Override
    public Timer extendSyncTimer(ThreadContext currThreadContext) {
        return null;
    }

    @Override
    public void end() {

    }

    @Override
    public void endWithLocationStackTrace(long threshold, TimeUnit unit) {

    }

    @Override
    public void endWithError(Throwable t) {

    }

    @Override
    public void endWithError(String message) {

    }

    @Override
    public void endWithError(String message, Throwable t) {

    }

    @Override
    public void endWithInfo(Throwable t) {

    }

    @Override
    public Timer extend() {
        return NopTransactionService.TIMER;
    }

    @Override
    public QueryMessageSupplier getMessageSupplier() {
        return this.messageSupplier;
    }
}
