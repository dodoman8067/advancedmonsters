package kro.dodoworld.advancedmonsters.modifier.ability.goal;

import com.destroystokyo.paper.entity.ai.Goal;
import com.destroystokyo.paper.entity.ai.GoalKey;
import com.destroystokyo.paper.entity.ai.GoalType;
import kro.dodoworld.advancedmonsters.AdvancedMonsters;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class EarthyGoal implements Goal<Mob> {
    private final GoalKey<Mob> key = GoalKey.of(Mob.class, new NamespacedKey(AdvancedMonsters.getPlugin(AdvancedMonsters.class), "earthy_spawn_waves"));
    private final Mob mob;
    private final int radius;
    private final int cooldownTicks;
    private final double damage;
    private int ticks = 1;

    public EarthyGoal(Mob mob, int radius, int cooldownTicks, double damage) {
        this.mob = mob;
        this.radius = radius;
        this.cooldownTicks = cooldownTicks;
        this.damage = damage;
    }

    @Override
    public boolean shouldActivate() {
        return mob.getTarget() != null && mob.isValid() && mob.getTarget().isValid();
    }

    @Override
    public boolean shouldStayActive() {
        return mob.getTarget() != null && mob.isValid() && mob.getTarget().isValid();
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
        if(ticks % cooldownTicks == 0){
            spawnWave(mob.getLocation().subtract(0, 1, 0), radius);
        }
        ticks++;
    }

    @Override
    public GoalKey<Mob> getKey() {
        return this.key;
    }

    @Override
    public EnumSet<GoalType> getTypes() {
        return EnumSet.noneOf(GoalType.class);
    }

    private void spawnWave(Location location, int radius1){
        List<FallingBlock> blocks = new ArrayList<>();
        new BukkitRunnable() {
            int rad = 1;
            @Override
            public void run() {
                for (Location loc : getCircle(location, rad, (rad * ((int) (Math.PI * 2))))) {
                    if(loc.getBlock().getType().getBlastResistance() >= 3000000) continue;
                    FallingBlock fb = loc.getWorld().spawnFallingBlock(loc, loc.getBlock().getBlockData());
                    fb.setHurtEntities(false);
                    fb.setDropItem(false);
                    fb.setVelocity(new Vector(0, .62, 0));
                    for(Entity entity : fb.getNearbyEntities(0.5, 0.5, 0.5)){
                        if(entity instanceof LivingEntity living &&
                                !entity.getUniqueId().equals(mob.getUniqueId())) {
                            living.damage(damage * (Math.max(radius1 - rad, 1)));
                        }
                    }
                    blocks.add(fb);
                }
                rad++;
                if(rad >= radius1){
                    cancel();
                }
            }
        }.runTaskTimer(AdvancedMonsters.getPlugin(AdvancedMonsters.class), 2, 2);
        new BukkitRunnable(){
            int i = 1;
            @Override
            public void run() {
                if(i % 100 == 0) cancel();
                for(FallingBlock block : blocks){
                    if(block.isValid()){
                        moveBlocks(block, mob.getTarget().getLocation());
                    }
                }
                i++;
            }
        }.runTaskTimer(AdvancedMonsters.getPlugin(AdvancedMonsters.class), 0L, 2L);
    }

    private void moveBlocks(FallingBlock block, Location target){
        //block.setVelocity(block.getVelocity().clone().add(target.clone().toVector().subtract(block.getLocation().clone().toVector()).multiply(0.005)));
        Vector dir = target.toVector().subtract(block.getLocation().toVector()).normalize();
        block.setVelocity(block.getVelocity().add(dir.multiply(0.2)));
    }

    private List<Location> getCircle(Location center, double radius, int amount){
        World world = center.getWorld();
        double increment = ((2 * Math.PI) / amount);
        ArrayList<Location> locations = new ArrayList<>();
        for(int i = 0;i < amount; i++){
            double angle = i * increment;
            double x = center.getX() + (radius * Math.cos(angle));
            double z = center.getZ() + (radius * Math.sin(angle));
            locations.add(new Location(world, x, center.getY(), z));
        }
        return locations;
    }
}
