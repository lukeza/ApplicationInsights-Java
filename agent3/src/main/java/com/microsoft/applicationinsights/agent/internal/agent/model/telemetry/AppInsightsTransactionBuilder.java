package com.microsoft.applicationinsights.agent.internal.agent.model.telemetry;

import com.microsoft.applicationinsights.TelemetryClient;
import com.microsoft.applicationinsights.telemetry.BaseTelemetry;
import com.microsoft.applicationinsights.telemetry.Duration;
import com.microsoft.applicationinsights.telemetry.ExceptionTelemetry;
import com.microsoft.applicationinsights.telemetry.RemoteDependencyTelemetry;
import com.microsoft.applicationinsights.telemetry.RequestTelemetry;
import com.microsoft.applicationinsights.telemetry.TelemetryContext;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AppInsightsTransactionBuilder {
    private TelemetryContext requestContext;
    private final TelemetryAccumulator accumulator;
    private String transactionName;

    private AppInsightsTransactionBuilder() {
        this.accumulator = new TelemetryAccumulator();
    }

    public static AppInsightsTransactionBuilder startOperation() {
        return new AppInsightsTransactionBuilder();
    }

    public RequestTelemetryBuilder startRequest() {
        final RequestTelemetryBuilder telemetryBuilder = new RequestTelemetryBuilder(System.currentTimeMillis());
        requestContext = telemetryBuilder.getContext();
        return telemetryBuilder;
    }

    public DependencyTelemetryBuilder startDependency() {
        final DependencyTelemetryBuilder telemetryBuilder = new DependencyTelemetryBuilder(System.currentTimeMillis());
        propagateOperationId(requestContext, telemetryBuilder.getContext());
        return telemetryBuilder;
    }

    public void trackAll(TelemetryClient telemetryClient) {
        for (BaseAppInsightsTelemetryBuilder<BaseTelemetry<?>> telemetry : accumulator) {
            telemetry.track(telemetryClient);
        }
    }

    private static void propagateOperationId(TelemetryContext parent, TelemetryContext child) {
        child.getOperation().setParentId(parent.getOperation().getId());
    }

    public AppInsightsTransactionBuilder setTransactionName(String transactionName) {
        this.transactionName = transactionName;
        return this;
    }

    public String getTransactionName() {
        return this.transactionName;
    }

    private static class TelemetryAccumulator implements Iterable<BaseAppInsightsTelemetryBuilder<BaseTelemetry<?>>> {
        private final List<BaseAppInsightsTelemetryBuilder<BaseTelemetry<?>>> telemetries;
        private TelemetryAccumulator() {
            this.telemetries = new ArrayList<>();
        }

        public boolean add(BaseAppInsightsTelemetryBuilder<BaseTelemetry<?>> telemetry) {
            return telemetries.add(telemetry);
        }

        @Override
        public Iterator<BaseAppInsightsTelemetryBuilder<BaseTelemetry<?>>> iterator() {
            return telemetries.iterator();
        }
    }

    public interface AppInsightsTelemetryBuilder {
        void track(TelemetryClient telemetryClient);
    }

    public abstract class BaseAppInsightsTelemetryBuilder<T extends BaseTelemetry<?>> implements AppInsightsTelemetryBuilder {
        private final T telemetry;

        protected BaseAppInsightsTelemetryBuilder(T telemetry) {
            this.telemetry = telemetry;
        }

        /**
         * @throws InvalidTelemetryException
         */
        protected abstract void validate();

        protected T getTelemetry() {
            return this.telemetry;
        }

        protected TelemetryContext getContext() {
            return getTelemetry().getContext();
        }

        protected T build() {
            validate();
            return telemetry;
        }

        public void track(TelemetryClient telemetryClient) {
            telemetryClient.track(build());
        }

        public ExceptionTelemetryBuilder generatedException(Throwable t) {
            final ExceptionTelemetryBuilder telemetryBuilder = new ExceptionTelemetryBuilder(t);
            propagateOperationId(getContext(), telemetryBuilder.getContext());
            return telemetryBuilder;
        }
    }

    public abstract class TimedTelemetryBuilder<T extends BaseTelemetry<?>> extends BaseAppInsightsTelemetryBuilder<T> {
        private long startTickMs;
        private long endTickMs;

        protected TimedTelemetryBuilder(T telemetry, long startTickMs) {
            super(telemetry);
            setStartTick(startTickMs);
        }

        protected abstract void setDuration(T telemetry, Duration duration);

        protected abstract void doValidate();

        public void setStartTick(long startTickMs) {
            this.startTickMs = startTickMs;
        }

        public void setEndTick(long endTimeMs) {
            this.endTickMs = endTimeMs;
        }

        @Override
        public T build() {
            setDuration(getTelemetry(), new Duration(endTickMs - startTickMs));
            return super.build();
        }

        @Override
        protected final void validate() {
            if (endTickMs < 0) {
                throw new InvalidTelemetryException("endTime must be set/positive");
            }
            doValidate();
        }
    }

    public class RequestTelemetryBuilder extends TimedTelemetryBuilder<RequestTelemetry> {
        private RequestTelemetryBuilder(long startTickMs) {
            super(new RequestTelemetry(), startTickMs);
        }

        @Override
        protected void setDuration(RequestTelemetry telemetry, Duration duration) {
            telemetry.setDuration(duration);
        }

        @Override
        protected void doValidate() {
            // TODO
        }
    }

    public class DependencyTelemetryBuilder extends TimedTelemetryBuilder<RemoteDependencyTelemetry> {

        private DependencyTelemetryBuilder(long startTickMs) {
            super(new RemoteDependencyTelemetry(), startTickMs);
        }

        @Override
        protected void setDuration(RemoteDependencyTelemetry telemetry, Duration duration) {
            telemetry.setDuration(duration);
        }

        @Override
        protected void doValidate() {
            // TODO
        }
    }

    public class ExceptionTelemetryBuilder extends BaseAppInsightsTelemetryBuilder<ExceptionTelemetry> {

        protected ExceptionTelemetryBuilder(Throwable t) {
            super(new ExceptionTelemetry(t));
        }

        @Override
        protected void validate() {
            // TODO
        }
    }

    public class InvalidTelemetryException extends RuntimeException {

        public InvalidTelemetryException(String s) {
            super(s);
        }
    }
}
