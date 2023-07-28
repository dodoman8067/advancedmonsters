package kro.dodoworld.advancedmonsters.event.registry;

import kro.dodoworld.advancedmonsters.core.registry.Registrable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * Called when some object was registered on the registry.
 */
public class RegistryRegisterObjectEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private final Registrable registeredObject;

    /**
     * Constructor for this class.
     * @param registeredObject the registered object
     */
    public RegistryRegisterObjectEvent(@NotNull Registrable registeredObject){
        this.registeredObject = registeredObject;
    }

    /**
     * Returns registered object.
     * @return the {@link Registrable} interface
     */
    @NotNull
    public Registrable getRegisteredObject() {
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
