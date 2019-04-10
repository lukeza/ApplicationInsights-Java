package com.microsoft.applicationinsights.agent.internal.agent.utils;

import org.glowroot.engine.bytecode.api.ThreadContextThreadLocal;

public class Global {
    private Global() {}

    private static final ThreadContextThreadLocal TCTL = new ThreadContextThreadLocal();

    public static ThreadContextThreadLocal getThreadContextThreadLocal() {
        return TCTL;
    }

    public static ThreadContextThreadLocal.Holder getThreadContextHolder() {
        return TCTL.getHolder();
    }



}
