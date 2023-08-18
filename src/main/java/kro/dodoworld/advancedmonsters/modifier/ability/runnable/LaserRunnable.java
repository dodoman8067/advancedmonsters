package kro.dodoworld.advancedmonsters.modifier.ability.runnable;

import kro.dodoworld.advancedmonsters.modifier.ability.Ability;
import kro.dodoworld.advancedmonsters.modifier.ability.AbilityRunnable;
import kro.dodoworld.advancedmonsters.modifier.ability.custom.LaserAbility;
import kro.dodoworld.advancedmonsters.util.AbilityUtils;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.util.Vector;

public class LaserRunnable extends AbilityRunnable {
    private final double damage;
    private final double range;

    public LaserRunnable(Ability ability, double damage, double range) {
        super(ability);
        this.damage = damage;
        this.range = range;
    }

    @Override
    public void run(){
        if(getAbility() == null) return;
        for(World world : Bukkit.getWorlds()){
            for(LivingEntity entity : world.getLivingEntities()){
                if(!(entity instanceof Monster monster)) continue;
                if(LaserAbility.getLaserMonsters().contains(monster.getUniqueId()) && AbilityUtils.hasAbility(monster, getAbility())){
                    if(monster.isDead()){
                        LaserAbility.getLaserMonsters().remove(monster.getUniqueId());
                    }else if(monster.getTarget() != null && !(monster.getLocation().distance(monster.getTarget().getLocation()) > range) && monster.hasLineOfSight(monster.getTarget())){
                        spawnLaser(monster.getEyeLocation(), monster.getTarget().getEyeLocation(), Color.fromRGB(255, 26, 18));
                        monster.getTarget().damage(damage, monster);
                    }
                }else if(!AbilityUtils.hasAbility(monster, getAbility())){
                    LaserAbility.getLaserMonsters().remove(monster.getUniqueId());
                }
                if(AbilityUtils.hasAbility(monster, getAbility())){
                    LaserAbility.getLaserMonsters().add(monster.getUniqueId());
                }

            }
        }
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
            world.spawnParticle(Particle.REDSTONE, pos1.getX(), pos1.getY(), pos1.getZ(), 1, dustOptions);
            cover += 0.1;
            pos1.add(vector);
        }
    }
}
