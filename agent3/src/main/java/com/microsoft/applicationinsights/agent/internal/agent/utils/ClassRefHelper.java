package com.microsoft.applicationinsights.agent.internal.agent.utils;

public class ClassRefHelper {

    private ClassRefHelper() {}

    public static <T> /*@Initialized*/ T castInitialized(/*@UnderInitialization*/ T obj) {
        return obj;
    }

    public static <T> /*@Untainted*/ T castUntainted(T obj) {
        return obj;
    }
}
