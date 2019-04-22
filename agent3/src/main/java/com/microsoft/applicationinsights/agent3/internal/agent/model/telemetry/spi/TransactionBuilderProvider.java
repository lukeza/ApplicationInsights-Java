package com.microsoft.applicationinsights.agent3.internal.agent.model.telemetry.spi;

public interface TransactionBuilderProvider<T extends TransactionContext, P> {
    /**
     * Returns the {@linkplain TransactionBuilder} registered for the given transaction name.
     * @param txName The name of registered TransactionBuilder
     * @return The TransactionBuilder registered for that name
     * @throws NullPointerException if {@code txName} is null
     */
    TransactionBuilder<T, P> getTransactionBuilder(String txName);

    /**
     * Registers a transaction builder with the given transaction name.
     * @param txName The name of the builder
     * @param builder The instance of the builder
     * @return The previous {@linkplain TransactionBuilder} registered for the given {@code txName} or null if there was no previous registration.
     * @throws NullPointerException if either {@code txName} or {@code builder} are null
     */
    TransactionBuilder<T, P> registerBuilder(String txName, TransactionBuilder<T, P> builder);

    /**
     * Removes a {@linkplain TransactionBuilder} from the registered builders.
     * @param txName The name of the builder to remove
     * @return The previous {@linkplain TransactionBuilder} registered for the given {@code txName} or null if there was no previous registration.
     * @throws NullPointerException if {@code txName} is null
     */
    TransactionBuilder<T, P> unregisterBuilder(String txName);
}
