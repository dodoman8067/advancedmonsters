package kro.dodoworld.advancedmonsters.entity.boss.ai;

import com.destroystokyo.paper.entity.ai.Goal;
import com.destroystokyo.paper.entity.ai.GoalKey;
import com.destroystokyo.paper.entity.ai.GoalType;
import org.bukkit.entity.Creature;
import org.jetbrains.annotations.NotNull;

import java.util.EnumSet;

/**
 * To be added
 */
public class NecromancerAttackGoal implements Goal<Creature> {

    private final Creature entity;

    public NecromancerAttackGoal(Creature entity){
        this.entity = entity;
    }

    @Override
    public boolean shouldActivate() {
        return false;
    }

    @Override
    public boolean shouldStayActive() {
        return Goal.super.shouldStayActive();
    }

    @Override
    public void start() {
        Goal.super.start();
    }

    @Override
    public void stop() {
        Goal.super.stop();
    }

    @Override
    public void tick() {
        Goal.super.tick();
    }

    @Override
    public @NotNull GoalKey<Creature> getKey() {
        return null;
    }

    @Override
    public @NotNull EnumSet<GoalType> getTypes() {
        return null;
    }
}
