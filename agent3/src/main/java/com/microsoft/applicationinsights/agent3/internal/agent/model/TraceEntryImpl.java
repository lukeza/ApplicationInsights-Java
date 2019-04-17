package com.microsoft.applicationinsights.agent3.internal.agent.model;

import com.microsoft.applicationinsights.agent3.internal.agent.model.telemetry.AppInsightsTransactionBuilder;
import com.microsoft.applicationinsights.agent3.internal.agent.utils.DevLogger;
import org.glowroot.engine.impl.NopTransactionService;
import org.glowroot.instrumentation.api.Message;
import org.glowroot.instrumentation.api.MessageSupplier;
import org.glowroot.instrumentation.api.Timer;
import org.glowroot.instrumentation.api.TraceEntry;
import org.glowroot.instrumentation.api.internal.ReadableMessage;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class TraceEntryImpl implements TraceEntry {
    private final Object messageSupplier;
    private final AppInsightsTransactionBuilder tx;

    private static final DevLogger out = new DevLogger(TraceEntryImpl.class);

    public TraceEntryImpl(AppInsightsTransactionBuilder tx, Object messageSupplier) {
        this.messageSupplier = messageSupplier;
        this.tx = tx;
        out.info("<init>");
    }

    private ReadableMessage getMessage() {
        Object o = getMessageSupplier();
        if (o instanceof MessageSupplier) {
            MessageSupplier ms = (MessageSupplier) o;
            Message msg = ms.get();
            if (msg != null) {
                if (msg instanceof ReadableMessage) {
                    return (ReadableMessage) msg;
                } else {
                    out.info("UNEXPECTED MESSAGE TYPE: %s returned a %s", ms.getClass().getSimpleName(), msg.getClass().getSimpleName());
                }
            }
        } else {
            out.info("%s had a %s; but expected %s", TraceEntryImpl.class.getSimpleName(), o.getClass().getSimpleName(), MessageSupplier.class.getSimpleName());
        }
        return null;
    }

    protected void done() {
        // TODO
        final ReadableMessage msg = getMessage();
        if (msg == null) {
            out.info("done");
        } else {
            out.info("done; text=%s, detail=%s", msg.getText(), Arrays.toString(msg.getDetail().entrySet().toArray()));
        }
        cleanUp();
    }

    private void done(Throwable t) {
        out.info("done", t);
        // TODO
        done();
    }

    /**
     * Override to perform any cleanup operations after ending trace entry.
     */
    protected void cleanUp() {
        // default impl nop
    }

    @Override
    public void end() {
        // TODO
        out.info("end");
        done();
    }

    @Override
    public void endWithLocationStackTrace(long threshold, TimeUnit unit) {
        // TODO
        out.info("endWithLocationStackTrace: threshold=%d %s", threshold, unit.toString());
        done();
    }

    @Override
    public void endWithError(Throwable t) {
        // TODO
        out.info("endWithError: t=%s", t.getClass().getSimpleName());
        done(t);
    }

    @Override
    public void endWithError(String message) {
        // TODO
        out.info("endWithError: msg=%s", message);
        done();
    }

    @Override
    public void endWithError(String message, Throwable t) {
        // TODO
        out.info("endWithError: msg=%s, t=%s", message, t.getClass().getSimpleName());
        done(t);
    }

    @Override
    public void endWithInfo(Throwable t) {
        // TODO
        out.info("endWithInfo: t=%s", t.getClass().getSimpleName());
        done(); // chomp t ?
    }

    @Override
    public Timer extend() {
        out.info("extend");
        return NopTransactionService.TIMER;
    }

    @Override
    public Object getMessageSupplier() {
        return messageSupplier;
    }
}
