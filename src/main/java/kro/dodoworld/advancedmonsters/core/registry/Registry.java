package kro.dodoworld.advancedmonsters.core.registry;

import kro.dodoworld.advancedmonsters.AdvancedMonsters;
import kro.dodoworld.advancedmonsters.event.registry.RegistryInitializeEvent;
import kro.dodoworld.advancedmonsters.event.registry.RegistryRegisterObjectEvent;
import kro.dodoworld.advancedmonsters.event.registry.RegistryUnRegisterObjectEvent;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Class for handling the registry system
 */
public final class Registry {

    private Registry(){
    }

    private static final Set<Registrable> REGISTERED_OBJECTS = new HashSet<>();
    private static final Registry INSTANCE = new Registry();
    private static boolean isInitialized = false;

    public static Set<Registrable> getRegisteredObjects() {
        return Collections.unmodifiableSet(REGISTERED_OBJECTS);
    }

    /**
     * Initializes registry for this plugin.
     * Do NOT call this unless you know what you're doing.
     * @param plugin plugin instance
     */
    public static void init(Plugin plugin){
        if(!(plugin instanceof AdvancedMonsters)) throw new RuntimeException(new IllegalAccessException("Registry cannot be initialized on other plugins."));
        if(isInitialized) throw new RuntimeException(new IllegalAccessException("Registry has been already initialized."));
        isInitialized = true;
        Bukkit.getServer().getPluginManager().callEvent(new RegistryInitializeEvent(INSTANCE));
    }

    /**
     * Registers an object that implements {@link Registrable}.
     * @param registrable the registrable object
     * @see Registry#unRegister(Registrable registrable)
     */
    public void register(Registrable registrable){
        if(registrable.isRegistered()) throw new RuntimeException(new IllegalArgumentException("You cannot register an object with id that already exists"));
        if(registrable.init().equals(RegisterResult.FAIL)) throw new RuntimeException(new IllegalStateException("Registration failed"));
        RegistryRegisterObjectEvent event = new RegistryRegisterObjectEvent(registrable);
        Bukkit.getPluginManager().callEvent(event);
        REGISTERED_OBJECTS.add(registrable);
    }

    /**
     * Unregisters an object that implements {@link Registrable}.
     * This method is intended for addons to remove an ability that has been registered by other addons/main plugin.
     * @param registrable the registrable object
     * @see Registry#register(Registrable registrable)
     */
    public void unRegister(Registrable registrable){
        if(!registrable.isRegistered()) throw new RuntimeException(new IllegalArgumentException("You cannot unregister an object with id that doesn't exists"));
        RegistryUnRegisterObjectEvent event = new RegistryUnRegisterObjectEvent(registrable);
        Bukkit.getPluginManager().callEvent(event);
        REGISTERED_OBJECTS.remove(registrable);
    }
}
