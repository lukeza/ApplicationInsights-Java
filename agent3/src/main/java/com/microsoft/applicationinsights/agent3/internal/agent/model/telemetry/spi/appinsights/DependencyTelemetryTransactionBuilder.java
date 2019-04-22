package com.microsoft.applicationinsights.agent3.internal.agent.model.telemetry.spi.appinsights;

import com.microsoft.applicationinsights.agent3.internal.agent.utils.MessageUtils;
import com.microsoft.applicationinsights.agent3.internal.agent.utils.TimerUtil;
import com.microsoft.applicationinsights.agent3.internal.agent.utils.dev.DevLogger;
import com.microsoft.applicationinsights.telemetry.RemoteDependencyTelemetry;
import org.apache.commons.lang3.StringUtils;
import org.glowroot.instrumentation.api.TimerName;
import org.glowroot.instrumentation.api.internal.ReadableMessage;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Map;

public class DependencyTelemetryTransactionBuilder extends BaseAppInsightsTxBuilder {

    private static final DevLogger out = new DevLogger(DependencyTelemetryTransactionBuilder.class);

    private final RemoteDependencyTelemetry telemetry;
    private String httpMethod;
    private URI uri;
    private String host;

    public DependencyTelemetryTransactionBuilder() {
        this.telemetry = new RemoteDependencyTelemetry();
    }

    @Override
    protected void beforeSend() {
        telemetry.setDuration(computeDuration());
        telemetry.setType("Http (tracked component)");
        if (uri != null) {
            if (httpMethod != null) {
                setName(httpMethod + " " + uri.getPath());
            } else if (StringUtils.isNotEmpty(uri.getPath())) {
                setName(uri.getPath());
            }
        }
        addTargetData();
    }

    private void addTargetData() {
        String target = host;
        if (StringUtils.isEmpty(target)) {
            if (uri == null) {
                return;
            }
            target = uri.getPath();
        }
        target = createTarget(this.uri, target);
        if (telemetry.getTarget() == null) {
            telemetry.setTarget(target);
        } else {
            telemetry.setTarget(telemetry.getTarget() + " | " + target);
        }
    }

    @Override
    protected RemoteDependencyTelemetry getTelemetry() {
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
            if ("http client request".equals(TimerUtil.getTimerName(timerName))) {
                addResultCode(details);
                addUriData(details);
                addHttpMethod(details);
                addHostData(details);
            }
        } catch (Exception e) {
            out.info("error getting message: "+e);
            e.printStackTrace();
        }
    }

    /**
     * This is used to create Target string to be set in the RDD Telemetry
     * According to spec, we do not include port 80 and 443 in target
     * @param uriObject
     * @return
     */
    private String createTarget(URI uriObject, String incomingTarget) {
        if (uriObject == null) {
            throw new NullPointerException("uriObject");
        }
        String target = uriObject.getHost();
        if (uriObject.getPort() != 80 && uriObject.getPort() != 443) {
            target += ":" + uriObject.getPort();
        }
        target += " | " + incomingTarget;
        return target;
    }


    private void addHttpMethod(Map<String, ?> details) {
        final Object httpMethod = (String) details.get("Method");
        if (httpMethod != null) {
            this.httpMethod = (String) httpMethod;
            telemetry.getProperties().put("Method", this.httpMethod);
        }
    }

    private void addUriData(Map<String,?> details) {
        final Object uri = details.get("URI");
        if (uri != null) {
            final String uriStr = (String) uri;
            try {
                this.uri = new URI(uriStr);
                telemetry.setCommandName(uriStr);
            } catch (URISyntaxException e) {
                out.info("malformed uri: '" + uriStr + "'");
            }
        }
    }

    private void addHostData(Map<String, ?> details) {
        final Object host = details.get("Host");
        if (host != null) {
            this.host = (String) host;
        }
    }

    private void addResultCode(Map<String, ?> details) {
        final Object result = details.get("Result");
        if (result != null) {
            Integer statusCode = (Integer) result;
            telemetry.setSuccess(statusCode < 400);
            telemetry.setResultCode(result.toString());
        }
    }

    @Override
    public void setName(String name) {
        this.telemetry.setName(name);
    }
}
