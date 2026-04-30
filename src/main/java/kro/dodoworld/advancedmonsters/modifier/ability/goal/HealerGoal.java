package kro.dodoworld.advancedmonsters.modifier.ability.goal;

import com.destroystokyo.paper.entity.ai.Goal;
import com.destroystokyo.paper.entity.ai.GoalKey;
import com.destroystokyo.paper.entity.ai.GoalType;
import kro.dodoworld.advancedmonsters.AdvancedMonsters;
import kro.dodoworld.advancedmonsters.system.entity.ability.HealingCircle;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Monster;
import org.jetbrains.annotations.NotNull;

import java.util.EnumSet;

// chatgpt used for commenting code
public class HealerGoal implements Goal<Mob> {
    private final GoalKey<Mob> key = GoalKey.of(Mob.class, new NamespacedKey(AdvancedMonsters.getPlugin(AdvancedMonsters.class), "healer_spawn_circle"));
    private final Mob mob;
    private int ticks;
    private long lastCircleSpawnTime = 0;
    private final long cooldown;
    private final int tryPerTicks;
    private final double amount;

    public HealerGoal(Mob mob, int tryPerTicks, double amount, long cooldown) {
        this.mob = mob;
        this.tryPerTicks = tryPerTicks;
        this.cooldown = cooldown;
        this.amount = amount;
    }

    @Override
    public boolean shouldActivate() {
        return !mob.isDead() && mob.isValid();
    }

    @Override
    public boolean shouldStayActive() {
        if(mob.isDead() || !mob.isValid()) return false;
        int activeMonsters = 0;
        for(Monster m : mob.getWorld().getNearbyEntitiesByType(Monster.class, mob.getLocation(), 20, 5, 20)){
            if(m.getTarget() != null && mob.hasLineOfSight(m)) activeMonsters++;
        }
        return activeMonsters > 2;
    }

    @Override
    public void start() {
        ticks = 0;
    }

    @Override
    public void stop() {
        ticks = 0;  // Reset ticks on stop
    }

    @Override
    public void tick() {
        if(mob.isDead() || !mob.isValid()) stop();
        ticks++;
        if(ticks % tryPerTicks == 0){
            tryCircleSpawn();
            ticks = 0;  // Reset ticks after each attempt
        }
    }

    private void tryCircleSpawn() {
        // Check cooldown before trying to spawn a healing circle
        long currentTime = System.currentTimeMillis();
        if(currentTime - lastCircleSpawnTime < cooldown) return;  // If cooldown has not passed, do not spawn

        int lowHealthMonsters = 0;
        for(Monster nearbyMonster : mob.getLocation().getNearbyEntitiesByType(Monster.class, 20, 5, 20)){
            AttributeInstance maxHealthAttr = nearbyMonster.getAttribute(Attribute.MAX_HEALTH);
            if(maxHealthAttr != null && nearbyMonster.getHealth() < maxHealthAttr.getValue() / 2){
                lowHealthMonsters++;
            }
        }

        if(lowHealthMonsters > 1){
            // Create and spawn the healing circle
            int duration = 10 + Math.min(20, lowHealthMonsters);  // Adjust duration based on the number of low-health monsters
            HealingCircle circle = new HealingCircle(mob, 3, amount);  // Spawn a healing circle with radius 3 and healing amount 8.0
            circle.spawn(duration);
            lastCircleSpawnTime = currentTime;  // Update the last spawn time to the current time
        }
    }

    @Override
    public @NotNull GoalKey<Mob> getKey() {
        return key;
    }

    @Override
    public @NotNull EnumSet<GoalType> getTypes() {
        return EnumSet.noneOf(GoalType.class);  // Define the goal type as MOVE
    }
}
