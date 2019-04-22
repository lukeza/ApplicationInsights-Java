package com.microsoft.applicationinsights.agent3.internal.agent.model.telemetry.spi.appinsights;

import com.microsoft.applicationinsights.agent3.internal.agent.utils.MessageUtils;
import com.microsoft.applicationinsights.agent3.internal.agent.utils.TimerUtil;
import com.microsoft.applicationinsights.agent3.internal.agent.utils.dev.DevLogger;
import com.microsoft.applicationinsights.telemetry.RequestTelemetry;
import org.apache.commons.lang3.StringUtils;
import org.glowroot.instrumentation.api.ThreadContext.ServletRequestInfo;
import org.glowroot.instrumentation.api.TimerName;
import org.glowroot.instrumentation.api.internal.ReadableMessage;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class RequestTelemetryTransactionBuilder extends BaseAppInsightsTxBuilder {

    private static final DevLogger out = new DevLogger(RequestTelemetryTransactionBuilder.class);

    private final RequestTelemetry telemetry;
    private ServletRequestInfo servletRequestInfo;

    public RequestTelemetryTransactionBuilder() {
        telemetry = new RequestTelemetry();
    }

    @Override
    protected void beforeSend() {
        telemetry.setDuration(computeDuration());
        if (servletRequestInfo != null) {
            if (StringUtils.isNotBlank(getTelemetry().getName())) {
                getTelemetry().getProperties().put("Tx Name", getTelemetry().getName());
            }
            setName(servletRequestInfo.getMethod() + " " + servletRequestInfo.getUri());
        }
    }

    @Override
    protected RequestTelemetry getTelemetry() {
        return this.telemetry;
    }

    @Override
    protected boolean isValid() {
        return true;
    }

    @Override
    public void addTraceData(Object messageSupplier, TimerName timerName) {
        try {
            final ReadableMessage message = MessageUtils.getMessage(messageSupplier);
            if (message == null) {
                return;
            }
            final String text = message.getText();
            final Map<String, ?> details = message.getDetail();
            out.info("addTraceData ["+ TimerUtil.getTimerName(timerName)+"]: "+text+" "+ Arrays.toString(details.entrySet().toArray()));
        } catch (Exception e) {
            out.info("error getting message: "+e);
            e.printStackTrace();
        }
    }


    @Override
    public void setServletRequestInfo(ServletRequestInfo requestInfo) {
        this.servletRequestInfo = requestInfo;
        out.info("got request info: "+toString(requestInfo));
    }

    private String toString(ServletRequestInfo requestInfo) {
        if (requestInfo == null) {
            return "null";
        }
        final List<String> jaxRsParts = requestInfo.getJaxRsParts();

        return String.format("m=%s, sp=%s, cp=%s, pi=%s, jp=%s, u=%s",
                requestInfo.getMethod(), requestInfo.getServletPath(),
                requestInfo.getContextPath(), requestInfo.getPathInfo(),
                Arrays.toString(jaxRsParts == null ? null : jaxRsParts.toArray()), requestInfo.getUri());
    }

    @Override
    public void setName(String name) {
        this.telemetry.setName(name);
    }

}
