package kro.dodoworld.advancedmonsters.modifier.ability.custom;

import kro.dodoworld.advancedmonsters.core.registry.RegisterResult;
import kro.dodoworld.advancedmonsters.modifier.ability.Ability;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Monster;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SpeedyAbility extends Ability {

    public SpeedyAbility(@NotNull NamespacedKey id, @Nullable Component symbol, @NotNull Component name, @Nullable FileConfiguration abilityConfig, @Nullable TextColor displayColor) {
        super(id, symbol, name, abilityConfig, displayColor);
    }

    @Override
    public void onSpawn(Monster monster){
        if(getConfig() == null) return;
        super.onSpawn(monster);
        double speedMultiplyAmount = getConfig().getDouble("speedy_speed_multiply_amount");
        double healthMultiplyAmount = getConfig().getDouble("speedy_health_multiply_amount");
        monster.getAttribute(Attribute.MOVEMENT_SPEED).setBaseValue(monster.getAttribute(Attribute.MOVEMENT_SPEED).getBaseValue() * speedMultiplyAmount);
        monster.getAttribute(Attribute.MAX_HEALTH).setBaseValue(monster.getAttribute(Attribute.MAX_HEALTH).getBaseValue() * healthMultiplyAmount);
        monster.setHealth(monster.getAttribute(Attribute.MAX_HEALTH).getValue());
        monster.getAttribute(Attribute.MOVEMENT_EFFICIENCY).setBaseValue(10);
        monster.getAttribute(Attribute.WATER_MOVEMENT_EFFICIENCY).setBaseValue(10);
    }

    @Override
    public @NotNull RegisterResult init() {
        if(getConfig() == null) return RegisterResult.FAIL;
        return RegisterResult.SUCCESS;
    }
}
