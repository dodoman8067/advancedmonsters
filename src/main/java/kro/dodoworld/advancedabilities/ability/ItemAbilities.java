package kro.dodoworld.advancedabilities.ability;

import kro.dodoworld.advancedabilities.ability.custom.TestAbility;
import kro.dodoworld.advancedmonsters.AdvancedMonsters;
import kro.dodoworld.advancedmonsters.core.registry.Registry;
import kro.dodoworld.advancedmonsters.event.registry.RegistryInitializeEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ItemAbilities implements Listener {
    private static ItemAbility test;

    @EventHandler
    public void onRegistryInit(RegistryInitializeEvent event){
        Registry registry = event.getRegistry();

        test = test();
        registry.register(test);
    }

    private ItemAbility createExtraDamageUndead(){
        return null;
    }

    private ItemAbility test(){
        return new TestAbility(
                new NamespacedKey(AdvancedMonsters.getPlugin(AdvancedMonsters.class), "test"),
                Component.text("\ud83d\udde1", NamedTextColor.DARK_RED),
                5
        );
    }
}
