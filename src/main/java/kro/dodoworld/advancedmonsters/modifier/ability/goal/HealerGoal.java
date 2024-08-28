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
    private static final long COOLDOWN = 20000; // 20 seconds cooldown in milliseconds

    public HealerGoal(Mob mob) {
        this.mob = mob;
    }

    @Override
    public boolean shouldActivate() {
        return mob.getTarget() != null;  // Activate only if the mob has a target
    }

    @Override
    public boolean shouldStayActive() {
        return mob.getTarget() != null && !mob.isDead();  // Stay active while mob has a target and is alive
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
        ticks++;
        if(ticks % 200 == 0){  // Every 200 ticks (10 seconds)
            tryCircleSpawn();
            ticks = 0;  // Reset ticks after each attempt
        }
    }

    private void tryCircleSpawn() {
        // Check cooldown before trying to spawn a healing circle
        long currentTime = System.currentTimeMillis();
        if(currentTime - lastCircleSpawnTime < COOLDOWN) return;  // If cooldown has not passed, do not spawn

        int lowHealthMonsters = 0;
        for(Monster nearbyMonster : mob.getLocation().getNearbyEntitiesByType(Monster.class, 20, 5, 20)){
            AttributeInstance maxHealthAttr = nearbyMonster.getAttribute(Attribute.GENERIC_MAX_HEALTH);
            if(maxHealthAttr != null && nearbyMonster.getHealth() < maxHealthAttr.getValue() / 2){
                lowHealthMonsters++;
            }
        }

        if(lowHealthMonsters > 1){
            // Create and spawn the healing circle
            int duration = 10 + Math.min(20, lowHealthMonsters);  // Adjust duration based on the number of low-health monsters
            HealingCircle circle = new HealingCircle(mob, 3, 8.0);  // Spawn a healing circle with radius 3 and healing amount 8.0
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
        return EnumSet.of(GoalType.MOVE);  // Define the goal type as MOVE
    }
}
