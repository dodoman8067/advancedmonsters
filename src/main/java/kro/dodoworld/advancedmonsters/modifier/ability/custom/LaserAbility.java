package kro.dodoworld.advancedmonsters.modifier.ability.custom;

import kro.dodoworld.advancedmonsters.AdvancedMonsters;
import kro.dodoworld.advancedmonsters.core.registry.RegisterResult;
import kro.dodoworld.advancedmonsters.modifier.ability.Ability;
import kro.dodoworld.advancedmonsters.modifier.ability.runnable.LaserRunnable;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Monster;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class LaserAbility extends Ability {

    private static final Set<UUID> LASER_MONSTERS = new HashSet<>();

    public LaserAbility(@NotNull NamespacedKey id, @Nullable Component symbol, @NotNull Component name, @Nullable FileConfiguration abilityConfig, @Nullable TextColor displayColor) {
        super(id, symbol, name, abilityConfig, displayColor);
    }

    @Override
    public void onSpawn(Monster monster){
        super.onSpawn(monster);
        LASER_MONSTERS.add(monster.getUniqueId());
    }

    @Override
    public @NotNull RegisterResult init() {
        if(getConfig() == null) return RegisterResult.FAIL;
        new LaserRunnable(this, getConfig().getDouble("laser_damage"), getConfig().getDouble("laser_shoot_range")).runTaskTimer(AdvancedMonsters.getPlugin(AdvancedMonsters.class), 0L, 1L);
        return RegisterResult.SUCCESS;
    }

    @NotNull
    public static Set<UUID> getLaserMonsters() { return LASER_MONSTERS; }
}
