package kro.dodoworld.advancedmonsters.event.registry;

import kro.dodoworld.advancedmonsters.core.registry.Registry;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class RegistryInitializeEvent extends Event {

    private final Registry registry;
    private static final HandlerList HANDLERS = new HandlerList();

    public RegistryInitializeEvent(Registry registry){
        this.registry = registry;
    }

    public Registry getRegistry() {
        return registry;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList(){
        return HANDLERS;
    }
}
