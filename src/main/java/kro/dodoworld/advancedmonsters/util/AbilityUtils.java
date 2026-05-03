package kro.dodoworld.advancedmonsters.util;

import kro.dodoworld.advancedmonsters.AdvancedMonsters;
import kro.dodoworld.advancedmonsters.core.registry.Registrable;
import kro.dodoworld.advancedmonsters.core.registry.Registry;
import kro.dodoworld.advancedmonsters.modifier.ability.Ability;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Monster;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashSet;
import java.util.Set;

/**
 * Utility class for the ability system.
 */
public class AbilityUtils {
    public static boolean hasAbility(Monster monster, Ability ability){
        if(!ability.isRegistered()) throw new RuntimeException(new IllegalArgumentException("Ability has to be registered"));
        if(!monster.getPersistentDataContainer().has(new NamespacedKey(AdvancedMonsters.getPlugin(AdvancedMonsters.class), "ability"), PersistentDataType.LIST.strings())) return false;
        return monster.getPersistentDataContainer().get(new NamespacedKey(AdvancedMonsters.getPlugin(AdvancedMonsters.class), "ability"), PersistentDataType.LIST.strings()).contains(ability.getId().asString());
    }

    public static Set<Ability> getRegisteredAbilities(){
        Set<Ability> abilitySet = new HashSet<>();
        for(Registrable r : Registry.getRegisteredObjects()){
            if(!(r instanceof Ability a)) continue;
            abilitySet.add(a);
        }
        return abilitySet;
    }
}
