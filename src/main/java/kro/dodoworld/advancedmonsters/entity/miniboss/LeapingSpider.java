package kro.dodoworld.advancedmonsters.entity.miniboss;

import kro.dodoworld.advancedmonsters.AdvancedMonsters;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.Particle;
import org.bukkit.Location;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Spider;
import org.bukkit.entity.Entity;
import org.bukkit.entity.CaveSpider;
import org.bukkit.entity.Silverfish;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.awt.Color;

public class LeapingSpider implements Listener {

    private static AdvancedMonsters plugin;

    public LeapingSpider(AdvancedMonsters plugin){
        LeapingSpider.plugin = plugin;
    }
    public static void registerLeapingSpider(AdvancedMonsters plugin){
        for(World world : Bukkit.getWorlds()){
            for(LivingEntity entity : world.getLivingEntities()){
                if(entity instanceof Spider){
                    if(entity.getScoreboardTags().contains("adm_miniboss_leaping_spider")){
                        Spider spider = (Spider) entity;
                        new BukkitRunnable(){
                            @Override
                            public void run() {
                                if(spider.isDead()) cancel();
                                if(spider.getTarget() == null){
                                    for(Entity entity : spider.getNearbyEntities(25,25,20)){
                                        if(entity instanceof Player){
                                            Player player = (Player) entity;
                                            spider.setTarget(player);
                                        }
                                    }
                                }
                                else{
                                    LivingEntity target = spider.getTarget();
                                    if(target.getLocation().distanceSquared(spider.getLocation()) > 6){
                                        spider.getWorld().spawnParticle(Particle.CAMPFIRE_COSY_SMOKE, spider.getLocation(), 10);
                                        spider.setVelocity(target.getLocation().add(0,2,0).subtract(spider.getLocation()).toVector().multiply(0.275));
                                    }
                                }
                            }
                        }.runTaskTimer(plugin, 0L, 1L);
                    }
                }
            }
        }
    }

    public static void createLeapingSpider(Location loc){
        CaveSpider spider = loc.getWorld().spawn(loc, CaveSpider.class);
        spider.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(80);
        spider.setHealth(80);
        spider.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.22);
        spider.addScoreboardTag("adm_miniboss_leaping_spider");
        spider.setCustomName(net.md_5.bungee.api.ChatColor.of(new Color(219, 42, 216)) + "" + ChatColor.BOLD + "⚛MINIBOSS " + net.md_5.bungee.api.ChatColor.of(new Color(212, 197, 38)) + "Leaping Spider");
        new BukkitRunnable(){
            @Override
            public void run() {
                if(spider.isDead()) cancel();
                if(spider.getTarget() == null){
                    for(Entity entity : spider.getNearbyEntities(25,25,20)){
                        if(entity instanceof Player){
                            Player player = (Player) entity;
                            spider.setTarget(player);
                        }
                    }
                }
                else{
                    LivingEntity target = spider.getTarget();
                    if(target.getLocation().distanceSquared(spider.getLocation()) > 6){
                        spider.getWorld().spawnParticle(Particle.CAMPFIRE_COSY_SMOKE, spider.getLocation(), 10);
                        spider.setVelocity(target.getLocation().add(0,2,0).subtract(spider.getLocation()).toVector().multiply(0.275));
                    }
                }
            }
        }.runTaskTimer(plugin, 0L, 1L);
    }

    @EventHandler
    public void onFall(EntityDamageEvent event){
        if(event.getEntity() instanceof CaveSpider){
            if(!event.getEntity().getScoreboardTags().contains("adm_miniboss_leaping_spider")) return;
            if(!event.getCause().equals(EntityDamageEvent.DamageCause.FALL)) return;
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event){
        if(event.getDamager() instanceof CaveSpider){
            if(event.getDamager().getScoreboardTags().contains("adm_miniboss_leaping_spider")){
                if(event.getEntity() instanceof Player){
                    Player player = (Player) event.getEntity();
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 200, 2));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 200, 3));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 200, 0));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 240, 3));
                }
            }
        }
    }

    @EventHandler
    public void onKill(EntityDeathEvent event){
        if(!(event.getEntity() instanceof CaveSpider)) return;
        if(!event.getEntity().getScoreboardTags().contains("adm_miniboss_leaping_spider")) return;
        for(int i = 0; i < 3; i++){
            Silverfish silverfish = event.getEntity().getWorld().spawn(event.getEntity().getLocation(), Silverfish.class);
            silverfish.setTarget(((CaveSpider) event.getEntity()).getTarget());
        }
    }
}
