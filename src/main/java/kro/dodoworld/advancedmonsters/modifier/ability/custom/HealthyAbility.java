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

public class HealthyAbility extends Ability {

    public HealthyAbility(@NotNull NamespacedKey id, @Nullable Component symbol, @NotNull Component name, @NotNull FileConfiguration abilityConfig, @Nullable BukkitRunnable runnable, @Nullable TextColor displayColor) {
        super(id, symbol, name, abilityConfig, runnable, displayColor);
    }

    @Override
    public @NotNull RegisterResult init() {
        if(getConfig() == null) return RegisterResult.FAIL;
        return RegisterResult.SUCCESS;
    }

    @Override
    public void onSpawn(Monster monster){
        super.onSpawn(monster);
        if(getConfig() == null) return;
        int amount = getConfig().getInt("healthy_health_multiply_amount");
        monster.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(monster.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() * amount);
        monster.setHealth(monster.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());
    }
}
