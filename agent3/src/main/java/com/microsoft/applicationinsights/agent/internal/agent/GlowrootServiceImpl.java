package com.microsoft.applicationinsights.agent.internal.agent;

import com.microsoft.applicationinsights.agent.internal.agent.utils.ConsoleOutputHelperForTesting;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.glowroot.engine.annotation.spi.GlowrootServiceSPI;

import java.util.concurrent.TimeUnit;

public class GlowrootServiceImpl implements GlowrootServiceSPI {

    private final ConsoleOutputHelperForTesting out = new ConsoleOutputHelperForTesting(GlowrootServiceImpl.class);

    GlowrootServiceImpl() {
        out.logMethod("<init>");
    }

    @Override
    public void setTransactionType(@Nullable String transactionType) {
        // TODO
        out.logMethod("setTransactionType", "type=%s", transactionType);
    }

    @Override
    public void setTransactionName(@Nullable String transactionName) {
        // TODO
        out.logMethod("setTransactionName", "name=%s", transactionName);
    }

    @Override
    public void setTransactionUser(@Nullable String user) {
        // TODO
        out.logMethod("setTransactionUser", "user=%s", user);
    }

    @Override
    public void addTransactionAttribute(String name, @Nullable String value) {
        // TODO
        out.logMethod("addTransactionAttribute", "name=%s, value=%s", name, value);
    }

    @Override
    public void setTransactionSlowThreshold(long threshold, TimeUnit unit) {
        // TODO
        out.logMethod("setTransactionUser", "threshold=%d %s", threshold, unit.toString());
    }

    @Override
    public void setTransactionOuter() {
        // TODO
        out.logMethod("setTransactionOuter");
    }
}
