package kro.dodoworld.advancedmonsters.core.registry;

import org.bukkit.NamespacedKey;

/**
 * Groups registrable object.
 */
public interface Registrable {
    RegisterResult init();
    boolean isRegistered();
    NamespacedKey getId();
}
