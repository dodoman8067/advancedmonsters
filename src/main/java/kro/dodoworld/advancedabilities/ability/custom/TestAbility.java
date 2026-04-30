package kro.dodoworld.advancedabilities.ability.custom;

import kro.dodoworld.advancedabilities.ability.ItemAbility;
import kro.dodoworld.advancedmonsters.core.registry.RegisterResult;
import net.kyori.adventure.text.Component;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.EquipmentSlot;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class TestAbility extends ItemAbility {
    public TestAbility(NamespacedKey id, Component name, int maxTier) {
        super(id, name, maxTier);
    }

    @Override
    public boolean canBeAppliedAt(EquipmentSlot slot, int tier) {
        return slot.equals(EquipmentSlot.CHEST);
    }

    @Override
    public Set<EquipmentSlot> getApplicableSlots(int tier) {
        return Set.of(EquipmentSlot.CHEST);
    }

    @Override
    public boolean canBeAppliedWith(EquipmentSlot slot, ItemAbility ability) {
        return slot.equals(EquipmentSlot.CHEST);
    }

    @Override
    public @NotNull RegisterResult init() {
        return RegisterResult.SUCCESS;
    }
}
