package kro.dodoworld.advancedmonsters.event.registry;

import kro.dodoworld.advancedmonsters.core.registry.Registry;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * Called when initializing registry.
 */
public class RegistryInitializeEvent extends Event {

    private final Registry registry;
    private static final HandlerList HANDLERS = new HandlerList();

    /**
     * Constructor for event.
     * @param registry {@link Registry} instance.
     */
    public RegistryInitializeEvent(Registry registry){
        this.registry = registry;
    }

    /**
     * Returns the registry instance.
     * @return instance
     */
    public Registry getRegistry() {
        return registry;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    /**
     * Returns HandlerList instance.
     * @return
     */
    public static HandlerList getHandlerList(){
        return HANDLERS;
    }
}
