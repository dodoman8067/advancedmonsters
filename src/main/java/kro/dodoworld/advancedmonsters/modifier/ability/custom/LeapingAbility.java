package kro.dodoworld.advancedmonsters.modifier.ability.custom;

import com.destroystokyo.paper.entity.ai.GoalKey;
import kro.dodoworld.advancedmonsters.AdvancedMonsters;
import kro.dodoworld.advancedmonsters.core.registry.RegisterResult;
import kro.dodoworld.advancedmonsters.modifier.ability.Ability;
import kro.dodoworld.advancedmonsters.modifier.ability.goal.LeapingGoal;
import kro.dodoworld.advancedmonsters.util.AbilityUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Spider;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class LeapingAbility extends Ability implements Listener {
    public LeapingAbility(@NotNull NamespacedKey id, @Nullable Component symbol, @NotNull Component name, @Nullable FileConfiguration abilityConfig, @Nullable TextColor displayColor, int spawnWeight) {
        super(id, symbol, name, abilityConfig, displayColor, spawnWeight);
    }

    @Override
    public @NotNull RegisterResult init() {
        if(getConfig() == null) return RegisterResult.FAIL;
        Bukkit.getPluginManager().registerEvents(this, AdvancedMonsters.getPlugin(AdvancedMonsters.class));
        return RegisterResult.SUCCESS;
    }

    @EventHandler
    public void onChunkLoad(ChunkLoadEvent event){
        if(getConfig() == null) return;
        for(Entity e : event.getChunk().getEntities()){
            if(!(e instanceof Monster monster)) continue;
            if(!AbilityUtils.hasAbility(monster, this)) continue;
            if(Bukkit.getMobGoals().hasGoal(monster, GoalKey.of(Mob.class, new NamespacedKey(AdvancedMonsters.getPlugin(AdvancedMonsters.class), "leaping")))) continue;
            Bukkit.getMobGoals().addGoal(monster, 3, new LeapingGoal(monster, getConfig().getInt("leaping_cooldown_ticks"), getConfig().getInt("leaping_leap_range")));
        }
    }

    @Override
    public void onSpawn(Monster monster){
        super.onSpawn(monster);
        if(Bukkit.getMobGoals().hasGoal(monster, GoalKey.of(Mob.class, new NamespacedKey(AdvancedMonsters.getPlugin(AdvancedMonsters.class), "leaping")))) return;
        Bukkit.getMobGoals().addGoal(monster, 3, new LeapingGoal(monster, getConfig().getInt("leaping_cooldown_ticks"), getConfig().getInt("leaping_leap_range")));
    }

    @Override
    public boolean canSpawn(Monster monster) {
        return monster instanceof Spider;
    }
}
