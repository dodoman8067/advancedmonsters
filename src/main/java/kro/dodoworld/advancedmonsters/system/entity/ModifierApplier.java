package kro.dodoworld.advancedmonsters.system.entity;

import kro.dodoworld.advancedmonsters.core.registry.Registrable;
import kro.dodoworld.advancedmonsters.core.registry.Registry;
import kro.dodoworld.advancedmonsters.modifier.ability.Ability;
import org.bukkit.entity.Monster;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        if(!ability.isRegistered()) throw new RuntimeException(new IllegalArgumentException("You cannot apply unregistered ability to monster. id : " + ability.getId().asString()));
        ability.onSpawn(monster);
    }

    private Ability getRandomAbility(){
        List<Ability> abilitySet = new ArrayList<>(getRegisteredAbilities());
        Collections.shuffle(abilitySet);
        return abilitySet.get(0);
    }

    private Set<Ability> getRegisteredAbilities(){
        Set<Ability> abilitySet = new HashSet<>();
        for(Registrable r : Registry.getRegisteredObjects()){
            if(!(r instanceof Ability a)) continue;
            abilitySet.add(a);
        }
        return abilitySet;
    }
}
