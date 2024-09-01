package kro.dodoworld.advancedmonsters.modifier.ability.goal;

import com.destroystokyo.paper.entity.ai.Goal;
import com.destroystokyo.paper.entity.ai.GoalKey;
import com.destroystokyo.paper.entity.ai.GoalType;
import kro.dodoworld.advancedmonsters.AdvancedMonsters;
import kro.dodoworld.advancedmonsters.modifier.ability.custom.VampireAbility;
import org.apache.commons.lang.Validate;
import org.bukkit.Color;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Bat;
import org.bukkit.entity.Creature;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.entity.SpawnCategory;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.EnumSet;

public class VampireGoal implements Goal<Mob> {
    private final GoalKey<Mob> key = GoalKey.of(Mob.class, new NamespacedKey(AdvancedMonsters.getPlugin(AdvancedMonsters.class), "vampire_drain_blood"));
    private final Mob mob;
    private int ticks;
    private final double range;
    private final int tryPerTicks;
    private final double amount;

    public VampireGoal(Mob mob, int tryPerTicks, double amount, double range) {
        this.mob = mob;
        this.tryPerTicks = tryPerTicks;
        this.amount = amount;
        this.range = range;
    }

    @Override
    public boolean shouldActivate() {
        if(mob.isDead() || !mob.isValid()) return false;
        AttributeInstance maxHealth = mob.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        if(maxHealth == null) return false;
        int nonMonsterEntityCount = 0;
        for(LivingEntity e : mob.getWorld().getNearbyLivingEntities(mob.getLocation(), range)){
            if(e instanceof Creature && !e.getSpawnCategory().equals(SpawnCategory.MONSTER) && !(e instanceof Bat) && mob.hasLineOfSight(e)) nonMonsterEntityCount++;
        }

        return mob.getHealth() != maxHealth.getValue() && nonMonsterEntityCount != 0 && !(mob.getWorld().isDayTime() || mob.getLocation().getBlock().getLightLevel() >= 6);
    }

    @Override
    public boolean shouldStayActive() {
        if(mob.isDead() || !mob.isValid()) return false;
        AttributeInstance maxHealth = mob.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        if(maxHealth == null) return false;
        int nonMonsterEntityCount = 0;
        for(LivingEntity e : mob.getWorld().getNearbyLivingEntities(mob.getLocation(), range)){
            if(e instanceof Creature && !e.getSpawnCategory().equals(SpawnCategory.MONSTER) && !(e instanceof Bat) && mob.hasLineOfSight(e)) nonMonsterEntityCount++;
        }

        return mob.getHealth() != maxHealth.getValue() && nonMonsterEntityCount != 0 && !(mob.getWorld().isDayTime() || mob.getLocation().getBlock().getLightLevel() >= 6);
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
            drainBlood();
            ticks = 0;  // Reset ticks after each attempt
        }
    }

    void drainBlood(){
        for(Mob mob1 : mob.getWorld().getNearbyEntitiesByType(Mob.class, mob.getLocation(), range)){
            if(mob1.getSpawnCategory().equals(SpawnCategory.MONSTER)) continue;
            if(mob1 instanceof Bat) continue;
            if(!mob.hasLineOfSight(mob1)) continue;
            if(mob1 instanceof Player player && ((player.getGameMode().equals(GameMode.SPECTATOR) || player.getGameMode().equals(GameMode.CREATIVE)))) continue;
            mob1.damage(amount, mob);
            spawnLaser(mob.getLocation().add(0, mob.getHeight() / 2, 0), mob1.getLocation(), Color.fromRGB(108, 0, 0));
            mob.heal(amount, EntityRegainHealthEvent.RegainReason.MAGIC);
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

    private void spawnLaser(Location basis, Location target, Color color) {
        World world = basis.getWorld();
        Validate.isTrue(target.getWorld().equals(world));
        double dis = basis.distance(target);
        Vector pos1 = basis.toVector();
        Vector pos2 = target.toVector();
        Vector vector = pos2.clone().subtract(pos1).normalize().multiply(0.1);
        double cover = 0.0;

        while(cover < dis) {
            Particle.DustOptions dustOptions = new Particle.DustOptions(color, 1.0F);
            world.spawnParticle(Particle.DUST, pos1.getX(), pos1.getY(), pos1.getZ(), 1, dustOptions);
            cover += 0.1;
            pos1.add(vector);
        }
    }
}
