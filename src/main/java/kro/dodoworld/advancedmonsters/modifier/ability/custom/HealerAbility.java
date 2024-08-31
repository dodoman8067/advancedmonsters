package kro.dodoworld.advancedmonsters.modifier.ability.custom;

import com.destroystokyo.paper.entity.ai.GoalKey;
import kro.dodoworld.advancedmonsters.AdvancedMonsters;
import kro.dodoworld.advancedmonsters.core.registry.RegisterResult;
import kro.dodoworld.advancedmonsters.modifier.ability.Ability;
import kro.dodoworld.advancedmonsters.modifier.ability.goal.HealerGoal;
import kro.dodoworld.advancedmonsters.util.AbilityUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Monster;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.world.ChunkLoadEvent;
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
    public void onSpawn(Monster monster){
        super.onSpawn(monster);
        if(getConfig() == null) return;
        if(Bukkit.getMobGoals().hasGoal(monster, GoalKey.of(Mob.class, new NamespacedKey(AdvancedMonsters.getPlugin(AdvancedMonsters.class), "healer_spawn_circle")))) return;
        Bukkit.getMobGoals().addGoal(monster, 1, new HealerGoal(monster, getConfig().getInt("healer_circle_try_per_ticks"), getConfig().getDouble("healer_circle_healing_amount"), getConfig().getLong("healer_circle_cooldown_ticks")));
    }


    @Override
    public @NotNull RegisterResult init() {
        if(getConfig() == null) return RegisterResult.FAIL;
        Bukkit.getPluginManager().registerEvents(this, AdvancedMonsters.getPlugin(AdvancedMonsters.class));
        return RegisterResult.SUCCESS;
    }

    @EventHandler
    public void onRegen(EntityRegainHealthEvent event){
        if(!(event.getEntity() instanceof Monster monster)) return;
        if(!AbilityUtils.hasAbility(monster, this)) return;
        event.setAmount(event.getAmount() * 2);
    }

    @EventHandler
    public void onChunkLoad(ChunkLoadEvent event){
        if(getConfig() == null) return;
        for(Entity e : event.getChunk().getEntities()){
            if(!(e instanceof Monster monster)) continue;
            if(!AbilityUtils.hasAbility(monster, this)) continue;
            if(Bukkit.getMobGoals().hasGoal(monster, GoalKey.of(Mob.class, new NamespacedKey(AdvancedMonsters.getPlugin(AdvancedMonsters.class), "healer_spawn_circle")))) continue;
            Bukkit.getMobGoals().addGoal(monster, 1, new HealerGoal(monster, getConfig().getInt("healer_circle_try_per_ticks"), getConfig().getDouble("healer_circle_healing_amount"), getConfig().getLong("healer_circle_cooldown_ticks")));
        }
    }

    public static Set<UUID> getHealerMonsters() {
        return HEALER_MONSTERS;
    }
}
