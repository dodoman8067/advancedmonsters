package kro.dodoworld.advancedmonsters.modifier.ability.custom;

import kro.dodoworld.advancedmonsters.core.registry.RegisterResult;
import kro.dodoworld.advancedmonsters.modifier.ability.Ability;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Monster;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SpeedyAbility extends Ability {

    public SpeedyAbility(@NotNull NamespacedKey id, @Nullable Component symbol, @NotNull Component name, @Nullable FileConfiguration abilityConfig, @Nullable BukkitRunnable runnable, @Nullable TextColor displayColor) {
        super(id, symbol, name, abilityConfig, runnable, displayColor);
    }

    @Override
    public void onSpawn(Monster monster){
        if(getConfig() == null) return;
        super.onSpawn(monster);
        double speedMultiplyAmount = getConfig().getDouble("speedy_speed_multiply_amount", 2.0);
        double healthMultiplyAmount = getConfig().getDouble("speedy_health_multiply_amount", 0.5);
        monster.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(monster.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getBaseValue() * speedMultiplyAmount);
        monster.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(monster.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() * healthMultiplyAmount);
        monster.setHealth(monster.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
    }

    @Override
    public RegisterResult init() {
        if(getConfig() == null) return RegisterResult.FAIL;
        return RegisterResult.SUCCESS;
    }
}
