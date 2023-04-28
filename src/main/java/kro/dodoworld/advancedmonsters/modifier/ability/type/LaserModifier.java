package kro.dodoworld.advancedmonsters.modifier.ability.type;

import kro.dodoworld.advancedmonsters.AdvancedMonsters;
import kro.dodoworld.advancedmonsters.config.modifier.LaserModifierConfig;
import org.apache.commons.lang.Validate;
import org.bukkit.World;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Particle;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.util.Vector;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.scheduler.BukkitRunnable;

public class LaserModifier {
    public static void run(AdvancedMonsters plugin) {
        FileConfiguration configuration = LaserModifierConfig.getLaserModifierConfig();
        double range = configuration.getDouble("laser_shoot_range");
        double damage = configuration.getDouble("laser_damage");
        new BukkitRunnable(){

            @Override
            public void run() {

                for(World world : Bukkit.getWorlds()){
                    for(LivingEntity entity : world.getLivingEntities()){
                        if(entity instanceof Monster) {
                            Monster monster = (Monster) entity;
                            if(monster.getScoreboardTags().contains("adm_modifier_laser")){
                                if(monster.isDead()) cancel();
                                if(monster.getTarget() != null){
                                    if(monster.getNearbyEntities(range, range, range).contains(monster.getTarget()) && monster.hasLineOfSight(monster.getTarget())){
                                        spawnLaser(monster.getEyeLocation(), monster.getTarget().getEyeLocation(), Color.fromRGB(255, 26, 18));
                                        monster.getTarget().damage(damage, monster);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }.runTaskTimer(plugin, 0L, 1L);
    }


    public static void spawnLaser(Location basis, Location target, Color color){
        World world = basis.getWorld();
        Validate.isTrue(target.getWorld().equals(world));
        double dis = basis.distance(target);
        Vector pos1 = basis.toVector();
        Vector pos2 = target.toVector();

        Vector vector = pos2.clone().subtract(pos1).normalize().multiply(0.1);
        double cover = 0;
        for(; cover < dis; pos1.add(vector)){
            Particle.DustOptions dustOptions = new Particle.DustOptions(color, 1);
            world.spawnParticle(Particle.REDSTONE, pos1.getX(), pos1.getY(), pos1.getZ(), 1, dustOptions);
            cover += 0.1;
        }
    }
}
