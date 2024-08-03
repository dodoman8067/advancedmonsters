package kro.dodoworld.advancedmonsters.modifier.ability.custom;

import kro.dodoworld.advancedmonsters.core.registry.RegisterResult;
import kro.dodoworld.advancedmonsters.modifier.ability.Ability;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class HealerAbility extends Ability {
    /**
     * Constructor for this class.
     *
     * @param id            id for ability
     * @param symbol        symbol for ability. if null, the symbol will not appear on the monster's name
     * @param name          ability's user-friendly name
     * @param abilityConfig configuration for the ability
     * @param displayColor  color used on monster's name
     */
    public HealerAbility(@NotNull NamespacedKey id, @Nullable Component symbol, @NotNull Component name, @Nullable FileConfiguration abilityConfig, @Nullable TextColor displayColor) {
        super(id, symbol, name, abilityConfig, displayColor);
    }

    @Override
    public @NotNull RegisterResult init() {
        if(getConfig() == null) return RegisterResult.FAIL;
        return RegisterResult.SUCCESS;
    }


}
