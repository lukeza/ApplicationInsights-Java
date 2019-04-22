package com.microsoft.applicationinsights.agent3.internal.agent;

import com.microsoft.applicationinsights.agent3.internal.agent.utils.dev.DevLogger;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.glowroot.engine.annotation.spi.GlowrootServiceSPI;

import java.util.concurrent.TimeUnit;

public class GlowrootServiceImpl implements GlowrootServiceSPI {

    private static final DevLogger out = new DevLogger(GlowrootServiceImpl.class);

    GlowrootServiceImpl() {
        out.info("<init>");
    }

    @Override
    public void setTransactionType(@Nullable String transactionType) {
        // TODO
        out.info("setTransactionType: type=%s", transactionType);
    }

    @Override
    public void setTransactionName(@Nullable String transactionName) {
        // TODO
        out.info("setTransactionName: name=%s", transactionName);
    }

    @Override
    public void setTransactionUser(@Nullable String user) {
        // TODO
        out.info("setTransactionUser: user=%s", user);
    }

    @Override
    public void addTransactionAttribute(String name, @Nullable String value) {
        // TODO
        out.info("addTransactionAttribute: name=%s, value=%s", name, value);
    }

    @Override
    public void setTransactionSlowThreshold(long threshold, TimeUnit unit) {
        // TODO
        out.info("setTransactionUser: threshold=%d %s", threshold, unit.toString());
    }

    @Override
    public void setTransactionOuter() {
        // TODO
        out.info("setTransactionOuter");
    }
}
