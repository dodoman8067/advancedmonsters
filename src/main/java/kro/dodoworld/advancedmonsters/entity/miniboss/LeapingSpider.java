package kro.dodoworld.advancedmonsters.entity.miniboss;

import kro.dodoworld.advancedmonsters.AdvancedMonsters;
import kro.dodoworld.advancedmonsters.event.MonsterAbilityUnlockEvent;
import kro.dodoworld.advancedmonsters.util.AdvancedUtils;
import kro.dodoworld.advancedmonsters.util.MonsterAbility;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.CaveSpider;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Silverfish;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class LeapingSpider implements Listener {

    public static void createLeapingSpider(Location loc){
        CaveSpider spider = loc.getWorld().spawn(loc, CaveSpider.class, CreatureSpawnEvent.SpawnReason.CUSTOM);
        spider.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(80);
        spider.setHealth(80);
        spider.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.22);
        spider.addScoreboardTag("adm_miniboss_leaping_spider");
        spider.customName(Component.text("⚛MINIBOSS ").color(TextColor.color(219, 42, 216)).decorate(TextDecoration.BOLD).append(Component.text("Leaping Spider").color(TextColor.color(212, 197, 38))));
        spider.setCustomNameVisible(true);
        spider.addScoreboardTag("adm_remove_when_reload");
            new BukkitRunnable(){
            @Override
            public void run() {
                if(spider.isDead()){
                    if(spider.getKiller() == null){
                        cancel();
                        return;
                    }
                    if(!AdvancedUtils.isUnlocked(MonsterAbility.VENOMOUS)){
                        MonsterAbilityUnlockEvent event = new MonsterAbilityUnlockEvent(MonsterAbility.VENOMOUS);
                        Bukkit.getServer().getPluginManager().callEvent(event);
                        if(!event.isCancelled()) {
                            AdvancedUtils.setUnlocked(event.getAbility(), true);
                        }
                    }
                    cancel();
                    return;
                }
                if(spider.getTarget() == null){
                    for(Entity entity : spider.getNearbyEntities(25,25,20)){
                        if(entity instanceof Player){
                            Player player = (Player) entity;
                            spider.setTarget(player);
                        }
                    }
                }else{
                    LivingEntity target = spider.getTarget();
                    if(target.getLocation().distanceSquared(spider.getLocation()) > 6){
                        spider.getWorld().spawnParticle(Particle.CAMPFIRE_COSY_SMOKE, spider.getLocation(), 10);
                        spider.setVelocity(target.getLocation().add(0,2,0).subtract(spider.getLocation()).toVector().multiply(0.275));
                    }
                }
            }
        }.runTaskTimer(AdvancedMonsters.getPlugin(AdvancedMonsters.class), 1L, 1L);
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
