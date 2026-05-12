package kro.dodoworld.advancedmonsters.modifier.ability.goal;

import com.destroystokyo.paper.entity.ai.Goal;
import com.destroystokyo.paper.entity.ai.GoalKey;
import com.destroystokyo.paper.entity.ai.GoalType;
import kro.dodoworld.advancedmonsters.AdvancedMonsters;
import org.apache.commons.lang3.Validate;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import java.util.EnumSet;

public class LaserGoal implements Goal<Mob> {
    private final GoalKey<Mob> key = GoalKey.of(Mob.class, new NamespacedKey(AdvancedMonsters.getPlugin(AdvancedMonsters.class), "laser_beam"));
    private final Mob mob;
    private final int cooldown;
    private final double damage;
    private final double range;
    private int cooldownTick;

    public LaserGoal(Mob mob, int cooldown, double damage, double range) {
        this.mob = mob;
        this.cooldown = cooldown;
        this.damage = damage;
        this.range = range;
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
        if(!(mob.getTarget() != null && mob.isValid() && mob.getTarget().isValid())) return;
        Location start = mob.getEyeLocation();

        Vector direction = mob.getTarget().getEyeLocation().toVector()
                .subtract(start.toVector())
                .normalize();

        RayTraceResult result;

        if(mob.getType().equals(EntityType.DROWNED) || mob.getType().equals(EntityType.ZOMBIE_NAUTILUS)){
            result = mob.getWorld().rayTraceBlocks(
                    start,
                    direction,
                    range,
                    FluidCollisionMode.NEVER,
                    true
            );
        }else{
            result = mob.getWorld().rayTraceBlocks(
                    start,
                    direction,
                    range,
                    FluidCollisionMode.SOURCE_ONLY,
                    true
            );
        }

        Location end;

        if(result != null && result.getHitBlock() != null){
            end = result.getHitPosition().toLocation(mob.getWorld());
        }else{
            end = start.clone().add(direction.clone().multiply(range));
        }

        spawnLaser(start, end, Color.fromRGB(255, 26, 18), damage);
    }

    @Override
    public GoalKey<Mob> getKey() {
        return this.key;
    }

    @Override
    public EnumSet<GoalType> getTypes() {
        return EnumSet.noneOf(GoalType.class);
    }

    private void spawnLaser(Location basis, Location target, Color color, double damage) {
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
            for(Entity e : pos1.toLocation(world).getNearbyEntities(1.5, 1.5, 1.5)){
                if(e instanceof Damageable mob1 && !(e.getUniqueId().equals(mob.getUniqueId())) && !mob1.getSpawnCategory().equals(SpawnCategory.MONSTER)){
                    mob1.damage(damage, mob);
                }
            }
            cover += 0.1;
            pos1.add(vector);
        }
    }
}
