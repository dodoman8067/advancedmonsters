package kro.dodoworld.advancedmonsters.system.entity.ability;

import kro.dodoworld.advancedmonsters.AdvancedMonsters;
import kro.dodoworld.advancedmonsters.util.LocationUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class HealingCircle {
    private final LivingEntity summonedBy;
    private final HealingCircleRunnable runnable;
    private final HealRunnable healRunnable;
    private final int radius;


    public HealingCircle(LivingEntity summonedBy, int radius, double amount){
        this.summonedBy = summonedBy;
        this.runnable = new HealingCircleRunnable(summonedBy.getLocation());
        this.healRunnable = new HealRunnable(summonedBy.getLocation(), amount);
        this.radius = radius;
    }

    public LivingEntity getSummonedBy() {
        return summonedBy;
    }

    public void spawn(){
        spawn(10);
    }

    public void spawn(int seconds){
        this.runnable.runTaskTimer(AdvancedMonsters.getPlugin(AdvancedMonsters.class), 0L, 4L);
        this.healRunnable.runTaskTimer(AdvancedMonsters.getPlugin(AdvancedMonsters.class), 0L, 20L);

        Bukkit.getScheduler().runTaskLater(AdvancedMonsters.getPlugin(AdvancedMonsters.class), () -> {this.runnable.cancel(); this.healRunnable.cancel();}, seconds * 20L);
    }

    public void remove(){
        this.runnable.cancel();
        this.healRunnable.cancel();
    }

    private class HealingCircleRunnable extends BukkitRunnable {
        private final Location location;

        HealingCircleRunnable(Location location){
            this.location = location;
        }

        private void moveMonsters(){
            if(summonedBy.isDead()){ cancel(); return; }

            for(Monster m : location.getNearbyEntitiesByType(Monster.class, 30, 3, 30)){
                if(m.getAttribute(Attribute.GENERIC_MAX_HEALTH) == null) continue;
                double maxHealth = m.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();

                if(!(m.getHealth() < maxHealth / 2)) continue;

                m.setTarget(null);
                m.getPathfinder().stopPathfinding();
                m.getPathfinder().moveTo(location);
            }
        }

        private void spawnParticles(){
            if(summonedBy.isDead()){ cancel(); return; }
            LocationUtils.createCircle(location, radius, 30, 156, 38, 0.9f);
        }

        @Override
        public void run() {
            moveMonsters();
            spawnParticles();
        }
    }

    private class HealRunnable extends BukkitRunnable {
        private final Location location;
        private final double amount;

        HealRunnable(Location location, double amount){
            this.location = location;
            this.amount = amount;
        }

        private void healMonsters(){
            if(summonedBy.isDead()){ cancel(); return; }

            for(Monster m : location.getNearbyEntitiesByType(Monster.class, radius, 2, radius)){
                m.heal((float) amount, EntityRegainHealthEvent.RegainReason.MAGIC);

                location.getWorld().spawnParticle(Particle.HEART, m.getEyeLocation(), 1);
            }
        }

        @Override
        public void run(){
            healMonsters();
        }
    }
}
