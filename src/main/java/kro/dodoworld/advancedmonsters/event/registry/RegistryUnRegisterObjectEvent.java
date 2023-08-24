package kro.dodoworld.advancedmonsters.event.registry;

import kro.dodoworld.advancedmonsters.core.registry.Registrable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * Called when an object is unregistered in the registry.
 * @see RegistryRegisterObjectEvent
 */
public class RegistryUnRegisterObjectEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private final Registrable registeredObject;

    /**
     * Constructor for this class.
     * @param registeredObject the unregistered object
     */
    public RegistryUnRegisterObjectEvent(@NotNull Registrable registeredObject){
        this.registeredObject = registeredObject;
    }

    /**
     * Returns unregistered object.
     * @return the {@link Registrable} interface
     */
    @NotNull
    public Registrable getUnRegisteredObject() {
        return registeredObject;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    /**
     * Returns HandlerList instance.
     * @return instance
     */
    @NotNull
    public static HandlerList getHandlerList(){
        return HANDLERS;
    }
}
