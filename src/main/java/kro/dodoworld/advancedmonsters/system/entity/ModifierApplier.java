package kro.dodoworld.advancedmonsters.system.entity;

import kro.dodoworld.advancedmonsters.event.ability.AbilityApplyEvent;
import kro.dodoworld.advancedmonsters.modifier.ability.Ability;
import kro.dodoworld.advancedmonsters.util.AbilityUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Monster;
import org.bukkit.entity.SpawnCategory;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ModifierApplier implements Listener {
    @EventHandler
    public void onSpawn(CreatureSpawnEvent event){
        if(event.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.CUSTOM)) return;
        if(!event.getEntity().getSpawnCategory().equals(SpawnCategory.MONSTER)) return;
        if((Math.random() * 100) <= 50){
            if(!(event.getEntity() instanceof Monster monster)) return;
            applyAbility(monster, getRandomAbility());
        }
    }

    private void applyAbility(Monster monster, Ability ability){
        if(!ability.isRegistered()) throw new RuntimeException(new IllegalArgumentException("You cannot apply an unregistered ability to a monster. id : " + ability.getId().asString()));
        AbilityApplyEvent event = new AbilityApplyEvent(ability, monster);
        Bukkit.getServer().getPluginManager().callEvent(event);
        if(event.isCancelled()) return;
        if(!event.getAbility().canSpawn(event.getMonster())) return;
        event.getAbility().onSpawn(event.getMonster());
    }


    private Ability getRandomAbility(Monster monster){
        List<Ability> abilities = AbilityUtils.getRegisteredAbilities().stream()
                .filter(ability -> ability.canSpawn(monster))
                .toList();

        if(abilities.isEmpty()) return null;

        int totalWeight = abilities.stream().mapToInt(ability -> ability.getSpawnWeight(monster.getLocation())).sum();
        int randomWeight = new Random().nextInt(totalWeight);

        for(Ability ability : abilities){
            randomWeight -= ability.getSpawnWeight(monster.getLocation());
            if(randomWeight < 0){
                return ability;
            }
        }

        return null; // Should not reach here
    }

    private Ability getRandomAbility(){
        List<Ability> abilitySet = new ArrayList<>(AbilityUtils.getRegisteredAbilities());
        Collections.shuffle(abilitySet);
        return abilitySet.get(0);
    }
}
