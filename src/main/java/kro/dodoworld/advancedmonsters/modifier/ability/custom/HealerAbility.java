package kro.dodoworld.advancedmonsters.modifier.ability.custom;

import kro.dodoworld.advancedmonsters.AdvancedMonsters;
import kro.dodoworld.advancedmonsters.core.registry.RegisterResult;
import kro.dodoworld.advancedmonsters.modifier.ability.Ability;
import kro.dodoworld.advancedmonsters.modifier.ability.runnable.HealerRunnable;
import kro.dodoworld.advancedmonsters.util.AbilityUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Monster;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class HealerAbility extends Ability implements Listener {
    private static final Set<UUID> HEALER_MONSTERS = new HashSet<>();

    public HealerAbility(@NotNull NamespacedKey id, @Nullable Component symbol, @NotNull Component name, @Nullable FileConfiguration abilityConfig, @Nullable TextColor displayColor, int spawnWeight) {
        super(id, symbol, name, abilityConfig, displayColor, spawnWeight);
    }


    @Override
    public @NotNull RegisterResult init() {
        if(getConfig() == null) return RegisterResult.FAIL;
        Bukkit.getPluginManager().registerEvents(this, AdvancedMonsters.getPlugin(AdvancedMonsters.class));
        //new HealerRunnable(this, 4, 2).runTaskTimer(AdvancedMonsters.getPlugin(AdvancedMonsters.class), 0L, 1L);
        return RegisterResult.SUCCESS;
    }

    @EventHandler
    public void onRegen(EntityRegainHealthEvent event){
        if(!(event.getEntity() instanceof Monster monster)) return;
        if(!AbilityUtils.hasAbility(monster, this)) return;
        event.setAmount(event.getAmount() * 2);
    }

    public static Set<UUID> getHealerMonsters() {
        return HEALER_MONSTERS;
    }
}
