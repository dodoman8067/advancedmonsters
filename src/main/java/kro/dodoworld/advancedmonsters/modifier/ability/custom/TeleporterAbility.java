package kro.dodoworld.advancedmonsters.modifier.ability.custom;

import kro.dodoworld.advancedmonsters.AdvancedMonsters;
import kro.dodoworld.advancedmonsters.core.registry.RegisterResult;
import kro.dodoworld.advancedmonsters.modifier.ability.Ability;
import kro.dodoworld.advancedmonsters.modifier.ability.runnable.TeleporterRunnable;
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

    @NotNull
    public static Set<UUID> getTeleportingMonsters() {
        return TELEPORTING_MONSTERS;
    }
}
