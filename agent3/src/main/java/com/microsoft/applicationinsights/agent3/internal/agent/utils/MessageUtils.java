package com.microsoft.applicationinsights.agent3.internal.agent.utils;

import org.glowroot.instrumentation.api.Message;
import org.glowroot.instrumentation.api.MessageSupplier;
import org.glowroot.instrumentation.api.internal.ReadableMessage;

public class MessageUtils {
    private MessageUtils(){}

    /**
     * Convenient because it does all the casting for you
     * @param messageSupplier
     * @return The {@linkplain ReadableMessage} in the given {@linkplain MessageSupplier}
     * @throws IllegalStateException if messageSupplier is not a {@linkplain MessageSupplier} or if {@linkplain MessageSupplier#get()} did not return a {@linkplain ReadableMessage}
     */
    public static /*@Nullable*/ ReadableMessage getMessage(Object messageSupplier) {
        if (messageSupplier == null) {
            throw new NullPointerException();
        }
        if (!(messageSupplier instanceof MessageSupplier)) {
            throw new IllegalStateException("Parameter was not a "+MessageSupplier.class.getSimpleName()+"; it was a "+messageSupplier.getClass());
        }
        MessageSupplier ms = (MessageSupplier) messageSupplier;
        Message msg = ms.get();
        if (msg == null) {
            return null;
        }

        if (!(msg instanceof ReadableMessage)) {
            throw new IllegalStateException("Unexpected message type; expected "+ReadableMessage.class.getSimpleName()+" but was "+msg.getClass());
        }

        return (ReadableMessage) msg;
    }
}
