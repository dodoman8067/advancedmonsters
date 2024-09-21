package kro.dodoworld.advancedabilities.ability;

import kro.dodoworld.advancedmonsters.core.registry.Registry;
import kro.dodoworld.advancedmonsters.event.registry.RegistryInitializeEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ItemAbilities implements Listener {
    @EventHandler
    public void onRegistryInit(RegistryInitializeEvent event){
        Registry registry = event.getRegistry();
    }

    private ItemAbility createExtraDamageUndead(){
        return null;
    }
}
