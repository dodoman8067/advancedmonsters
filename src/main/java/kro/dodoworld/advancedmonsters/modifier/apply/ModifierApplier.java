package kro.dodoworld.advancedmonsters.modifier.apply;

import kro.dodoworld.advancedmonsters.AdvancedMonsters;
import kro.dodoworld.advancedmonsters.core.registry.Registry;
import kro.dodoworld.advancedmonsters.modifier.ability.Ability;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Monster;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ModifierApplier implements Listener {
    @EventHandler
    public void onSpawn(CreatureSpawnEvent event){
        if(event.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.CUSTOM)) return;
        if(!(event.getEntity() instanceof Monster monster)) return;
        if((Math.random() * 100) <= 50){
            applyAbility(monster, getRandomAbility());
        }
    }

    private void applyAbility(Monster monster, Ability ability){
        if(!ability.isRegistered()) AdvancedMonsters.getPlugin(AdvancedMonsters.class).getLogger().severe("Plugin tried to add unknown / unregistered ability : " + ability.getId().asString());
        ability.onSpawn(monster);
    }

    private Ability getRandomAbility(){
        List<Ability> abilitySet = new ArrayList<>(Registry.getRegisteredAbilities());
        Collections.shuffle(abilitySet);
        return abilitySet.get(0);
    }
}
