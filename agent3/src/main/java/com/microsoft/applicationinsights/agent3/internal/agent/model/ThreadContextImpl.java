package com.microsoft.applicationinsights.agent3.internal.agent.model;

import com.microsoft.applicationinsights.agent3.internal.agent.model.telemetry.spi.appinsights.AppInsightsSender;
import com.microsoft.applicationinsights.agent3.internal.agent.model.telemetry.spi.appinsights.AppInsightsTransactionBuilderProvider;
import com.microsoft.applicationinsights.agent3.internal.agent.model.telemetry.spi.appinsights.AppInsightsTransactionBuilderProvider.NopTxBuilder;
import com.microsoft.applicationinsights.agent3.internal.agent.model.telemetry.spi.appinsights.BaseAppInsightsTxBuilder;
import com.microsoft.applicationinsights.agent3.internal.agent.utils.dev.DevLogger;
import org.glowroot.engine.bytecode.api.ThreadContextPlus;
import org.glowroot.engine.impl.NopTransactionService;
import org.glowroot.instrumentation.api.AsyncQueryEntry;
import org.glowroot.instrumentation.api.AsyncTraceEntry;
import org.glowroot.instrumentation.api.AuxThreadContext;
import org.glowroot.instrumentation.api.MessageSupplier;
import org.glowroot.instrumentation.api.QueryEntry;
import org.glowroot.instrumentation.api.QueryMessageSupplier;
import org.glowroot.instrumentation.api.Timer;
import org.glowroot.instrumentation.api.TimerName;
import org.glowroot.instrumentation.api.TraceEntry;

import java.util.concurrent.TimeUnit;

import static com.microsoft.applicationinsights.agent3.internal.agent.utils.TimerUtil.getTimerName;

public class ThreadContextImpl implements ThreadContextPlus {

    private final BaseAppInsightsTxBuilder rootTx;
    private final AppInsightsSender sender;
    private int currentNestingGroupId; // TODO what to do with this?
    private int currentSuppressionKeyId; // TODO what to do with this?
    private ServletRequestInfo servletRequestInfo;

    private static final DevLogger out = new DevLogger(ThreadContextImpl.class);

    public ThreadContextImpl(BaseAppInsightsTxBuilder rootTx, AppInsightsSender sender, int rootNestingGroupId, int rootSuppressionKeyId) {
        this.rootTx = rootTx;
        this.sender = sender;
        setCurrentNestingGroupId(rootNestingGroupId);
        setCurrentSuppressionKeyId(rootSuppressionKeyId);
    }

    @Override
    public int getCurrentNestingGroupId() {
        return currentNestingGroupId;
    }

    @Override
    public void setCurrentNestingGroupId(int nestingGroupId) {
        currentNestingGroupId = nestingGroupId;
    }

    @Override
    public int getCurrentSuppressionKeyId() {
        return currentSuppressionKeyId;
    }

    @Override
    public void setCurrentSuppressionKeyId(int suppressionKeyId) {
        currentSuppressionKeyId = suppressionKeyId;
    }

    @Override
    public boolean isInTransaction() {
        // TODO always true?
        return true;
    }

    @Override
    public TraceEntry startTransaction(String transactionType, String transactionName, MessageSupplier messageSupplier, TimerName timerName) {
        // TODO
        out.info("startTransaction$0: txType=%s, txName=%s, timerName=%s", transactionType, transactionName, getTimerName(timerName));
        return createChildTraceEntry(transactionType, transactionName, messageSupplier, timerName);
    }

    @Override
    public TraceEntry startTransaction(String transactionType, String transactionName, MessageSupplier messageSupplier, TimerName timerName, AlreadyInTransactionBehavior alreadyInTransactionBehavior) {
        // TODO
        out.info("startTransaction$1: txType=%s, txName=%s, timerName=%s", transactionType, transactionName, getTimerName(timerName));
        return createChildTraceEntry(transactionType, transactionName, messageSupplier, timerName);
    }

    @Override
    public TraceEntry startTraceEntry(MessageSupplier messageSupplier, TimerName timerName) {
        // TODO
        out.info("startTraceEntry; timerName=%s", getTimerName(timerName));
        return new TraceEntryImpl(rootTx, messageSupplier, timerName);
    }

    @Override
    public AsyncTraceEntry startAsyncTraceEntry(MessageSupplier messageSupplier, TimerName timerName) {
        // TODO
        out.info("startAsyncTraceEntry; timerName=%s", getTimerName(timerName));
        return new TraceEntryImpl(rootTx, messageSupplier, timerName);
    }

    @Override
    public QueryEntry startQueryEntry(String queryType, String queryText, QueryMessageSupplier queryMessageSupplier, TimerName timerName) {
        // TODO
        out.info("startQueryEntry$1: qType=%s, qText=%s, timerName=%s", queryType, queryText, getTimerName(timerName));
        return NopTransactionService.QUERY_ENTRY;
    }

    @Override
    public QueryEntry startQueryEntry(String queryType, String queryText, long queryExecutionCount, QueryMessageSupplier queryMessageSupplier, TimerName timerName) {
        // TODO
        out.info("startQueryEntry$2: qType=%s, qText=%s, timerName=%s", queryType, queryText, getTimerName(timerName));
        return NopTransactionService.QUERY_ENTRY;
    }

    @Override
    public AsyncQueryEntry startAsyncQueryEntry(String queryType, String queryText, QueryMessageSupplier queryMessageSupplier, TimerName timerName) {
        // TODO
        out.info("startAsyncQueryEntry: qType=%s, qText=%s, timerName=%s", queryType, queryText, getTimerName(timerName));
        return NopTransactionService.ASYNC_QUERY_ENTRY;
    }

    @Override
    public TraceEntry startServiceCallEntry(String type, String text, MessageSupplier messageSupplier, TimerName timerName) {
        // TODO
        // TODO will this always be a child/remote dependency?
        return createChildTraceEntry(type, text, messageSupplier, timerName);
    }

    private TraceEntry createChildTraceEntry(String type, String text, MessageSupplier messageSupplier, TimerName timerName) {
        final BaseAppInsightsTxBuilder nextTx = AppInsightsTransactionBuilderProvider.getInstance().getTransactionBuilder(type);
        if (nextTx instanceof NopTxBuilder) {
            return NopTransactionService.TRACE_ENTRY;
        }
        nextTx.setName(text);
        nextTx.setTransactionContext(rootTx.getTransactionContext().nextChild());
        nextTx.setStartTime(System.currentTimeMillis());
        return new TrackingTraceEntryImpl(nextTx, sender, messageSupplier, timerName);
    }

    @Override
    public AsyncTraceEntry startAsyncServiceCallEntry(String type, String text, MessageSupplier messageSupplier, TimerName timerName) {
        // TODO
        out.info("startAsyncServiceCallEntry: type=%s, text=%s, timerName=%s", type, text, getTimerName(timerName));
        final BaseAppInsightsTxBuilder nextTx = AppInsightsTransactionBuilderProvider.getInstance().getTransactionBuilder(type);
        if (nextTx instanceof NopTxBuilder) {
            return NopTransactionService.ASYNC_TRACE_ENTRY;
        }
        nextTx.setName(text);
        nextTx.setTransactionContext(rootTx.getTransactionContext().nextChild());
        nextTx.setStartTime(System.currentTimeMillis());
        // TODO tracking trace entry
        return new TraceEntryImpl(nextTx, messageSupplier, timerName);
    }

    @Override
    public Timer startTimer(TimerName timerName) {
        // TODO
        out.info("startTimer %s", getTimerName(timerName));
        return NopTransactionService.TIMER;
    }

    @Override
    public AuxThreadContext createAuxThreadContext() {
        // TODO
        out.info("createAuxThreadContext");
        return NopTransactionService.AUX_THREAD_CONTEXT;
    }

    @Override
    public void setTransactionAsync() {
        // TODO
        out.info("setTransactionAsync");
    }

    @Override
    public void setTransactionAsyncComplete() {
        // TODO
        out.info("setTransactionAsyncComplete");
    }

    @Override
    public void setTransactionOuter() {
        // TODO
        out.info("setTransactionOuter");
    }

    @Override
    public void setTransactionType(String transactionType, int priority) {
        // TODO
        // FIXME this would change the type of Telemetry object...not sure what to do here yet
        out.info("setTransactionType: type=%s, p=%s", transactionType, priority);
    }

    @Override
    public void setTransactionName(String transactionName, int priority) {
        // TODO
        out.info("setTransactionName: name=%s, p=%s", transactionName, priority);
        // TODO this should change the name of rootTx; how to use priority?
    }

    @Override
    public void setTransactionUser(String user, int priority) {
        // TODO
        out.info("setTransactionUser: user=%s, p=%s", user, priority);
        // TODO this would add dimension/properties
    }

    @Override
    public void addTransactionAttribute(String name, String value) {
        // TODO
        out.info("addTransactionAttribute: name=%s, value=%s", name, value);
        // TODO should this add a property to rootTx, or does it have a different purpose
    }

    @Override
    public void setTransactionSlowThreshold(long threshold, TimeUnit unit, int priority) {
        // TODO
//        out.info("setTransactionSlowThreshold: threshold=%d %s, p=%d", threshold, unit.toString(), priority);
    }

    @Override
    public void setTransactionError(Throwable t) {
        // TODO
        out.info("setTransactionError: t=%s", t.getClass().getSimpleName());
    }

    @Override
    public void setTransactionError(String message) {
        // TODO
        out.info("setTransactionError: msg=%s", message);
    }

    @Override
    public void setTransactionError(String message, Throwable t) {
        // TODO
        out.info("setTransactionError: msg=%s, t=%s", message, t.getClass().getSimpleName());
    }

    @Override
    public void addErrorEntry(Throwable t) {
        // TODO
        out.info("addErrorEntry: t=%s", t.getClass().getSimpleName());
    }

    @Override
    public void addErrorEntry(String message) {
        // TODO
        out.info("addErrorEntry: msg=%s", message);
    }

    @Override
    public void addErrorEntry(String message, Throwable t) {
        // TODO
        out.info("addErrorEntry: msg=%s, t=%s", message, t.getClass().getSimpleName());
    }

    @Override
    public ServletRequestInfo getServletRequestInfo() {
        return servletRequestInfo;
    }

    @Override
    public void setServletRequestInfo(ServletRequestInfo servletRequestInfo) {
        rootTx.setServletRequestInfo(servletRequestInfo);
        this.servletRequestInfo = servletRequestInfo;
    }
}
