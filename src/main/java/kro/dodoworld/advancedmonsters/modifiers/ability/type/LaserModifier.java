package kro.dodoworld.advancedmonsters.modifiers.ability.type;

import kro.dodoworld.advancedmonsters.AdvancedMonsters;
import org.apache.commons.lang.Validate;
import org.bukkit.World;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Particle;
import org.bukkit.Location;
import org.bukkit.util.Vector;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.scheduler.BukkitRunnable;

public class LaserModifier {
    public static void run(AdvancedMonsters plugin) {
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
                                    if(monster.getNearbyEntities(50, 50, 50).contains(monster.getTarget())){
                                        spawnLaser(monster.getEyeLocation(), monster.getTarget().getEyeLocation(), Color.fromRGB(255, 26, 18));
                                        monster.getTarget().damage(4, monster);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }.runTaskTimer(plugin, 0L, 2L);
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
