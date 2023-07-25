package kro.dodoworld.advancedmonsters.core.registry;

import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.NotNull;

/**
 * Groups registrable object.
 */
public interface Registrable {
    /**
     * Initializes the registrable object.
     * @return {@link RegisterResult} value
     */
    @NotNull
    RegisterResult init();

    /**
     * Checks this object is registered to registry.
     * @return true if registered, otherwise false
     */
    boolean isRegistered();

    /**
     * Returns id for this object.
     * @return NamespacedKey id
     */
    @NotNull
    NamespacedKey getId();
}
