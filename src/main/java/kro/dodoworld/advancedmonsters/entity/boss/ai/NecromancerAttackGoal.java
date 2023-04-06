package kro.dodoworld.advancedmonsters.entity.boss.ai;

import com.destroystokyo.paper.entity.ai.Goal;
import com.destroystokyo.paper.entity.ai.GoalKey;
import com.destroystokyo.paper.entity.ai.GoalType;
import kro.dodoworld.advancedmonsters.AdvancedMonsters;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Creature;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.EnumSet;

/**
 * To be added
 */
public class NecromancerAttackGoal implements Goal<Creature>, Listener {
    private final Plugin plugin = AdvancedMonsters.getPlugin(AdvancedMonsters.class);
    private final GoalKey<Creature> key = GoalKey.of(Creature.class, new NamespacedKey(AdvancedMonsters.getPlugin(AdvancedMonsters.class), "necromancer_attack"));
    private final Creature entity;

    public NecromancerAttackGoal(Creature entity){
        this.entity = entity;
    }

    @Override
    public boolean shouldActivate() {
        return true;
    }

    @Override
    public boolean shouldStayActive() {
        return Goal.super.shouldStayActive();
    }

    @Override
    public void start() {
        Goal.super.start();
        Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public Creature getEntity() {
        return entity;
    }

    @Override
    public void stop() {
        Goal.super.stop();
        HandlerList.unregisterAll(this);
    }

    @Override
    public void tick() {
        Goal.super.tick();
    }

    @Override
    public @NotNull GoalKey<Creature> getKey() {
        return key;
    }

    @Override
    public @NotNull EnumSet<GoalType> getTypes() {
        return EnumSet.of(GoalType.TARGET);
    }
}
