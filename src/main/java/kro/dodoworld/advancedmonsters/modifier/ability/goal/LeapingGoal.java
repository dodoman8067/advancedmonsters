package kro.dodoworld.advancedmonsters.modifier.ability.goal;

import com.destroystokyo.paper.entity.ai.Goal;
import com.destroystokyo.paper.entity.ai.GoalKey;
import com.destroystokyo.paper.entity.ai.GoalType;
import kro.dodoworld.advancedmonsters.AdvancedMonsters;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.util.Vector;

import java.util.EnumSet;

public class LeapingGoal implements Goal<Mob> {
    private final GoalKey<Mob> key = GoalKey.of(Mob.class, new NamespacedKey(AdvancedMonsters.getPlugin(AdvancedMonsters.class), "leaping"));
    private final Mob mob;
    private final int cooldown;
    private final int distance;
    private int cooldownTick;

    public LeapingGoal(Mob mob, int cooldown, int distance){
        this.mob = mob;
        this.cooldown = cooldown;
        this.distance = distance;
    }

    @Override
    public boolean shouldActivate() {
        return mob.getTarget() != null;
    }

    @Override
    public boolean shouldStayActive() {
        return mob.getTarget() != null;
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
        LivingEntity target = mob.getTarget();
        if(target.getLocation().distance(mob.getLocation()) > distance && cooldownTick % cooldown == 0){
            mob.getWorld().spawnParticle(Particle.CAMPFIRE_COSY_SMOKE, mob.getX(), mob.getY(), mob.getZ(), 3);
            Vector dir = target.getLocation().add(
                    (Math.random() - 0.5) * 1.5,
                    2,
                    (Math.random() - 0.5) * 1.5
            ).subtract(mob.getLocation()).toVector();
            mob.setVelocity(dir.multiply(0.25));
        }
        cooldownTick++;
    }

    @Override
    public GoalKey<Mob> getKey() {
        return key;
    }

    @Override
    public EnumSet<GoalType> getTypes() {
        return EnumSet.noneOf(GoalType.class);
    }
}
