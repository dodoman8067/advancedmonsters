package kro.dodoworld.advancedmonsters.modifier.ability.custom;

import kro.dodoworld.advancedmonsters.AdvancedMonsters;
import kro.dodoworld.advancedmonsters.core.registry.RegisterResult;
import kro.dodoworld.advancedmonsters.modifier.ability.Ability;
import kro.dodoworld.advancedmonsters.modifier.ability.runnable.TeleporterRunnable;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Biome;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.AbstractSkeleton;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Monster;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class TeleporterAbility extends Ability {

    private static final Set<UUID> TELEPORTING_MONSTERS = new HashSet<>();

    public TeleporterAbility(@NotNull NamespacedKey id, @Nullable Component symbol, @NotNull Component name, @Nullable FileConfiguration abilityConfig, @Nullable TextColor displayColor) {
        super(id, symbol, name, abilityConfig, displayColor);
    }

    @Override
    public @NotNull RegisterResult init() {
        if(getConfig() == null) return RegisterResult.FAIL;
        new TeleporterRunnable(this).runTaskTimer(AdvancedMonsters.getPlugin(AdvancedMonsters.class), 0L, 1L);
        return RegisterResult.SUCCESS;
    }

    @Override
    public void onSpawn(Monster monster){
        super.onSpawn(monster);
        TELEPORTING_MONSTERS.add(monster.getUniqueId());
    }

    @Override
    public boolean canSpawn(Monster monster){
        if(monster.getType().equals(EntityType.ENDERMAN) || monster.getType().equals(EntityType.BREEZE) || monster.getType().equals(EntityType.BLAZE) || monster.getType().equals(EntityType.PHANTOM) || monster.getType().equals(EntityType.GHAST) || monster.getType().equals(EntityType.GUARDIAN) || monster.getType().equals(EntityType.ELDER_GUARDIAN) || monster.getType().equals(EntityType.WITHER)){
            return false;
        }
        return !(monster.getEquipment().getItemInMainHand().equals(ItemStack.of(Material.BOW)) || monster.getEquipment().getItemInMainHand().equals(ItemStack.of(Material.CROSSBOW)) || monster.getEquipment().getItemInMainHand().equals(ItemStack.of(Material.TRIDENT)));
    }

    @NotNull
    public static Set<UUID> getTeleportingMonsters() {
        return TELEPORTING_MONSTERS;
    }
}
