package kro.dodoworld.advancedabilities.ability;

import kro.dodoworld.advancedmonsters.core.registry.Registrable;
import net.kyori.adventure.text.Component;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.EquipmentSlot;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

// Note: Use AbilityManager#getAppliedAbilityTier to get the item's applied tier

public abstract class ItemAbility implements Registrable {
    private final NamespacedKey id;
    private final Component name;
    private final int maxTier;

    public ItemAbility(NamespacedKey id, Component name, int maxTier){
        this.id = id;
        this.name = name;
        this.maxTier= maxTier;
    }

    @Override
    public boolean isRegistered() {
        for(ItemAbility a : AbilityManager.getRegisteredItemAbilities()){
            if(a.getId().asString().equals(this.id.asString())) {
                return true;
            }
            if(a == this){
                return true;
            }
        }
        return false;
    }

    public abstract boolean canBeAppliedAt(EquipmentSlot slot, int tier);

    public abstract Set<EquipmentSlot> getApplicableSlots(int tier);

    public abstract boolean canBeAppliedWith(EquipmentSlot slot, ItemAbility ability);

    public @NotNull Component getName() {
        return name;
    }

    public int getMaxTier() {
        return maxTier;
    }

    @Override
    public @NotNull NamespacedKey getId() {
        return id;
    }
}
