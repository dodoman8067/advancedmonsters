package kro.dodoworld.advancedmonsters.util;

import kro.dodoworld.advancedmonsters.AdvancedMonsters;
import kro.dodoworld.advancedmonsters.modifier.ability.Ability;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Monster;
import org.bukkit.persistence.PersistentDataType;

/**
 * Utility class for the ability system.
 */
public class AbilityUtils {
    public static boolean hasAbility(Monster monster, Ability ability){
        if(!ability.isRegistered()) throw new RuntimeException(new IllegalArgumentException("Ability has to be registered"));
        if(!monster.getPersistentDataContainer().has(new NamespacedKey(AdvancedMonsters.getPlugin(AdvancedMonsters.class), "ability"), PersistentDataType.STRING)) return false;
        return monster.getPersistentDataContainer().get(new NamespacedKey(AdvancedMonsters.getPlugin(AdvancedMonsters.class), "ability"), PersistentDataType.STRING).equals(ability.getId().asString());
    }
}
