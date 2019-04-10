package com.microsoft.applicationinsights.agent.internal.agent.utils;

/**
 * This is just for POC testing
 */
public class ConsoleOutputHelperForTesting {
    private final String className;

    public ConsoleOutputHelperForTesting(Class<?> clazz) {
        className = clazz.getSimpleName();
    }

    public void logMethod(String methodName) {
        logMethod(methodName, null);
    }

    public void logMethod(String methodName, String messageFormat, Object... args) {
        if (messageFormat == null) {
            System.out.printf("[%s.%s]%n", className, methodName);
        } else {
            System.out.printf("[%s.%s]: %s%n", className, methodName, String.format(messageFormat, args));
        }
    }
}
