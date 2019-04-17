package com.microsoft.applicationinsights.agent3.internal.agent.model;

import com.microsoft.applicationinsights.agent3.internal.agent.model.telemetry.AppInsightsTransactionBuilder;
import com.microsoft.applicationinsights.agent3.internal.agent.utils.DevLogger;
import org.glowroot.instrumentation.api.QueryEntry;
import org.glowroot.instrumentation.api.QueryMessage;
import org.glowroot.instrumentation.api.QueryMessageSupplier;
import org.glowroot.instrumentation.api.internal.ReadableQueryMessage;

import java.util.Arrays;

public class QueryEntryImpl extends TraceEntryImpl implements QueryEntry {

    private static final DevLogger out = new DevLogger(QueryEntryImpl.class);

    public QueryEntryImpl(AppInsightsTransactionBuilder tx, Object queryMessageSupplier) {
        super(tx, queryMessageSupplier);
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

    @Override
    protected void done() {
        ReadableQueryMessage msg = getQueryMessage();
        if (msg == null) {
            out.info("done");
        } else {
            out.info("done; prefix=%s, suffix=%s, detail=%s", msg.getPrefix(), msg.getSuffix(), Arrays.toString(msg.getDetail().entrySet().toArray()));
        }
        cleanUp();
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
}
