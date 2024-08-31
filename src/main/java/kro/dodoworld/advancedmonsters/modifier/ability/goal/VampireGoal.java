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
import org.bukkit.entity.Bat;
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
        if(mob.getWorld().isDayTime()) return false;
        return mob.getTarget() != null;  // Activate only if the mob has a target
    }

    @Override
    public boolean shouldStayActive() {
        if(mob.getWorld().isDayTime()) return false;
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
        if(ticks % tryPerTicks == 0){
            drainBlood();
            ticks = 0;  // Reset ticks after each attempt
        }
    }

    void drainBlood(){
        if(mob.isDead() || !mob.isValid()) stop();
        if(mob.getWorld().isDayTime()){
            mob.damage(mob.getLocation().getBlock().getLightLevel() * amount);
            mob.setNoDamageTicks(1);
        }else{
            for(Mob mob : mob.getWorld().getNearbyEntitiesByType(Mob.class, mob.getLocation(), range)){
                if(mob.getSpawnCategory().equals(SpawnCategory.MONSTER)) continue;
                if(mob instanceof Bat) continue;
                if(!mob.hasLineOfSight(mob)) continue;
                if(mob instanceof Player player && ((player.getGameMode().equals(GameMode.SPECTATOR) || player.getGameMode().equals(GameMode.CREATIVE)))) continue;
                mob.damage(amount, mob);
                spawnLaser(mob.getLocation().add(0, mob.getHeight() / 2, 0), mob.getLocation(), Color.fromRGB(108, 0, 0));
                mob.heal(amount, EntityRegainHealthEvent.RegainReason.MAGIC);
            }
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
