package kro.dodoworld.advancedmonsters.core.registry;

import kro.dodoworld.advancedmonsters.AdvancedMonsters;
import kro.dodoworld.advancedmonsters.event.registry.RegistryInitializeEvent;
import kro.dodoworld.advancedmonsters.modifier.ability.Ability;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.plugin.Plugin;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Class for handling the registry system
 */
public final class Registry implements Listener {

    private Registry(){
    }

    private static final Set<Registrable> REGISTERED_OBJECTS = new HashSet<>();
    private static final Registry INSTANCE = new Registry();
    private static boolean isInitialized = false;

    public static Set<Registrable> getRegisteredObjects() {
        return Collections.unmodifiableSet(REGISTERED_OBJECTS);
    }

    /**
     * Do NOT call this.
     * @param plugin plugin instance
     * @throws IllegalAccessException if already initialized or other plugin is trying to call this method
     */
    public static void init(Plugin plugin) throws IllegalAccessException {
        if(!(plugin instanceof AdvancedMonsters)) throw new IllegalAccessException("Other plugin tried to call this method");
        if(isInitialized) throw new IllegalAccessException("Registry has been already initialized");
        isInitialized = true;
        Bukkit.getServer().getPluginManager().registerEvents(INSTANCE, AdvancedMonsters.getPlugin(AdvancedMonsters.class));
    }

    /**
     * Registers the ability.
     * @param registrable the registrable object
     */
    public void register(Registrable registrable){
        if(registrable.isRegistered()) throw new IllegalArgumentException("You cannot register an object with id that already exists");
        if(registrable.init().equals(RegisterResult.FAIL)) throw new IllegalStateException("Registration failed");
        REGISTERED_OBJECTS.add(registrable);
        if(registrable instanceof Ability ability){
            if(ability.getAbilityTask() != null) ability.getAbilityTask().runTaskTimer(AdvancedMonsters.getPlugin(AdvancedMonsters.class), 0L, 1L);
        }
    }

    @EventHandler
    private void onEnable(PluginEnableEvent event){
        if(!(event.getPlugin() instanceof AdvancedMonsters)) return;
        Bukkit.getServer().getPluginManager().callEvent(new RegistryInitializeEvent(INSTANCE));
    }
}
