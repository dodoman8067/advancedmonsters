package kro.dodoworld.advancedmonsters.core.registry;

import kro.dodoworld.advancedmonsters.AdvancedMonsters;
import kro.dodoworld.advancedmonsters.event.registry.RegistryInitializeEvent;
import kro.dodoworld.advancedmonsters.modifier.ability.Ability;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginEnableEvent;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Class for handling the registry system
 */
public class Registry implements Listener {

    private Registry(){
        Bukkit.getServer().getPluginManager().registerEvents(this, AdvancedMonsters.getPlugin(AdvancedMonsters.class));
    }

    private static final Set<Ability> REGISTERED_ABILITIES = new HashSet<>();
    private static final Registry INSTANCE = new Registry();

    public static Set<Ability> getRegisteredAbilities() {
        return Collections.unmodifiableSet(REGISTERED_ABILITIES);
    }

    public void registerAbility(Ability ability){
        if(ability.isRegistered()) throw new IllegalArgumentException("You cannot register the ability with id that already exists");
        REGISTERED_ABILITIES.add(ability);
        ability.init();
        if(ability.getRunnable() != null) ability.getRunnable().runTaskTimer(AdvancedMonsters.getPlugin(AdvancedMonsters.class), 0L, 1L);
    }

    @EventHandler
    private void onEnable(PluginEnableEvent event){
        if(!(event.getPlugin() instanceof AdvancedMonsters)) return;
        Bukkit.getServer().getPluginManager().callEvent(new RegistryInitializeEvent(INSTANCE));
    }
}
