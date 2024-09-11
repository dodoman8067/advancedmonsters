package kro.dodoworld.advancedabilities.ability;

import kro.dodoworld.advancedmonsters.core.registry.Registrable;
import kro.dodoworld.advancedmonsters.core.registry.Registry;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.Set;

public class AbilityManager {
    private static final AbilityManager MANAGER = new AbilityManager();

    private AbilityManager(){}

    public void applyAbility(ItemStack item, ItemAbility ability){

    }

    public boolean hasAbility(ItemStack item, ItemAbility ability){
        return false;
    }

    public void removeAbility(ItemStack item, ItemAbility ability){

    }

    public Set<ItemAbility> getAppliedItemAbilities(ItemStack item){
        return null;
    }

    public void removeAllAbility(ItemStack item){

    }

    public static AbilityManager getManager() {
        return MANAGER;
    }

    public static Set<ItemAbility> getRegisteredItemAbilities(){
        Set<ItemAbility> abilitySet = new HashSet<>();
        for(Registrable r : Registry.getRegisteredObjects()){
            if(!(r instanceof ItemAbility a)) continue;
            abilitySet.add(a);
        }
        return abilitySet;
    }
}
