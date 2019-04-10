package com.microsoft.applicationinsights.agent.internal.agent.model;

import com.microsoft.applicationinsights.agent.internal.agent.model.telemetry.AppInsightsTransactionBuilder;
import com.microsoft.applicationinsights.agent.internal.agent.utils.ConsoleOutputHelperForTesting;
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

public class ThreadContextImpl implements ThreadContextPlus {

    private final AppInsightsTransactionBuilder transactionBuilder;
    private int currentNestingGroupId; // TODO what to do with this?
    private int currentSuppressionKeyId; // TODO what to do with this?
    private ServletRequestInfo servletRequestInfo;

    private final ConsoleOutputHelperForTesting out = new ConsoleOutputHelperForTesting(ThreadContextImpl.class);

    public ThreadContextImpl(AppInsightsTransactionBuilder transactionBuilder) {
        out.logMethod("<init>");
        this.transactionBuilder = transactionBuilder;
    }

    @Override
    public int getCurrentNestingGroupId() {
        return currentNestingGroupId;
    }

    @Override
    public void setCurrentNestingGroupId(int nestingGroupId) {
        out.logMethod("setCurrentNestingGroupId", "current=%s, new=%s", currentNestingGroupId, nestingGroupId);
        currentNestingGroupId = nestingGroupId;
    }

    @Override
    public int getCurrentSuppressionKeyId() {
        return currentSuppressionKeyId;
    }

    @Override
    public void setCurrentSuppressionKeyId(int suppressionKeyId) {
        out.logMethod("setCurrentSuppressionKeyId", "current=%s, new=%s", currentSuppressionKeyId, suppressionKeyId);
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
        out.logMethod("startTransaction$1", "txType=%s, txName=%s", transactionType, transactionName);
        return NopTransactionService.TRACE_ENTRY;
    }

    @Override
    public TraceEntry startTransaction(String transactionType, String transactionName, MessageSupplier messageSupplier, TimerName timerName, AlreadyInTransactionBehavior alreadyInTransactionBehavior) {
        // TODO
        out.logMethod("startTransaction$1", "txType=%s, txName=%s", transactionType, transactionName);
        return NopTransactionService.TRACE_ENTRY;
    }

    @Override
    public TraceEntry startTraceEntry(MessageSupplier messageSupplier, TimerName timerName) {
        // TODO
        out.logMethod("startTraceEntry");
        return NopTransactionService.TRACE_ENTRY;
    }

    @Override
    public AsyncTraceEntry startAsyncTraceEntry(MessageSupplier messageSupplier, TimerName timerName) {
        // TODO
        out.logMethod("startAsyncTraceEntry");
        return NopTransactionService.ASYNC_TRACE_ENTRY;
    }

    @Override
    public QueryEntry startQueryEntry(String queryType, String queryText, QueryMessageSupplier queryMessageSupplier, TimerName timerName) {
        // TODO
        out.logMethod("startQueryEntry$1", "qType=%s, qText=%s", queryType, queryText);
        return new QueryEntryImpl(transactionBuilder, queryMessageSupplier);
    }

    @Override
    public QueryEntry startQueryEntry(String queryType, String queryText, long queryExecutionCount, QueryMessageSupplier queryMessageSupplier, TimerName timerName) {
        // TODO
        out.logMethod("startQueryEntry$2", "qType=%s, qText=%s", queryType, queryText);
        return new QueryEntryImpl(transactionBuilder, queryMessageSupplier);
    }

    @Override
    public AsyncQueryEntry startAsyncQueryEntry(String queryType, String queryText, QueryMessageSupplier queryMessageSupplier, TimerName timerName) {
        // TODO
        out.logMethod("startAsyncQueryEntry", "qType=%s, qText=%s", queryType, queryText);
        return new AsyncQueryEntryImpl(transactionBuilder, queryMessageSupplier);
    }

    @Override
    public TraceEntry startServiceCallEntry(String type, String text, MessageSupplier messageSupplier, TimerName timerName) {
        // TODO
        out.logMethod("startServiceCallEntry", "type=%s, text=%s", type, text);
        return NopTransactionService.TRACE_ENTRY;
    }

    @Override
    public AsyncTraceEntry startAsyncServiceCallEntry(String type, String text, MessageSupplier messageSupplier, TimerName timerName) {
        // TODO
        out.logMethod("startAsyncServiceCallEntry", "type=%s, text=%s", type, text);
        return NopTransactionService.ASYNC_TRACE_ENTRY;
    }

    @Override
    public Timer startTimer(TimerName timerName) {
        // TODO
        out.logMethod("startTimer");
        return NopTransactionService.TIMER;
    }

    @Override
    public AuxThreadContext createAuxThreadContext() {
        // TODO
        out.logMethod("createAuxThreadContext");
        return NopTransactionService.AUX_THREAD_CONTEXT;
    }

    @Override
    public void setTransactionAsync() {
        // TODO
        out.logMethod("setTransactionAsync");
    }

    @Override
    public void setTransactionAsyncComplete() {
        // TODO
        out.logMethod("setTransactionAsyncComplete");
    }

    @Override
    public void setTransactionOuter() {
        // TODO
        out.logMethod("setTransactionOuter");
    }

    @Override
    public void setTransactionType(String transactionType, int priority) {
        // TODO
        out.logMethod("setTransactionType", "type=%s, p=%d", transactionType, priority);
    }

    @Override
    public void setTransactionName(String transactionName, int priority) {
        // TODO
        out.logMethod("setTransactionName", "name=%s, p=%d", transactionName, priority);
    }

    @Override
    public void setTransactionUser(String user, int priority) {
        // TODO
        out.logMethod("setTransactionUser", "user=%s, p=%d", user, priority);
    }

    @Override
    public void addTransactionAttribute(String name, String value) {
        // TODO
        out.logMethod("addTransactionAttribute", "name=%s, value=%s", name, value);
    }

    @Override
    public void setTransactionSlowThreshold(long threshold, TimeUnit unit, int priority) {
        // TODO
        out.logMethod("setTransactionSlowThreshold", "threshold=%d %s, p=%d", threshold, unit.toString(), priority);
    }

    @Override
    public void setTransactionError(Throwable t) {
        // TODO
        out.logMethod("setTransactionError", "t=%s", t.getClass().getSimpleName());
    }

    @Override
    public void setTransactionError(String message) {
        // TODO
        out.logMethod("setTransactionError", "msg=%s", message);
    }

    @Override
    public void setTransactionError(String message, Throwable t) {
        // TODO
        out.logMethod("setTransactionError", "msg=%s, t=%s", message, t.getClass().getSimpleName());
    }

    @Override
    public void addErrorEntry(Throwable t) {
        // TODO
        out.logMethod("addErrorEntry", "t=%s", t.getClass().getSimpleName());
    }

    @Override
    public void addErrorEntry(String message) {
        // TODO
        out.logMethod("addErrorEntry", "msg=%s", message);
    }

    @Override
    public void addErrorEntry(String message, Throwable t) {
        // TODO
        out.logMethod("addErrorEntry", "msg=%s, t=%s", message, t.getClass().getSimpleName());
    }

    @Override
    public ServletRequestInfo getServletRequestInfo() {
        return servletRequestInfo;
    }

    @Override
    public void setServletRequestInfo(ServletRequestInfo servletRequestInfo) {
        out.logMethod("setServletRequestInfo", "method=%s, contextPath=%s, servletPath=%s", servletRequestInfo.getMethod(), servletRequestInfo.getContextPath(), servletRequestInfo.getServletPath());
        this.servletRequestInfo = servletRequestInfo;
    }
}
