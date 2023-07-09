package kro.dodoworld.advancedmonsters.entity.miniboss;

import kro.dodoworld.advancedmonsters.AdvancedMonsters;
import kro.dodoworld.advancedmonsters.event.MonsterAbilityUnlockEvent;
import kro.dodoworld.advancedmonsters.util.AdvancedUtils;
import kro.dodoworld.advancedmonsters.util.BlockUtilMethods;
import kro.dodoworld.advancedmonsters.modifier.ability.MonsterAbility;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Blaze;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.SmallFireball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class Inferno implements Listener {
    public static void createInferno(Location loc){
        Blaze blaze = loc.getWorld().spawn(loc, Blaze.class, CreatureSpawnEvent.SpawnReason.CUSTOM);
        blaze.addScoreboardTag("adm_remove_when_reload");
        blaze.addScoreboardTag("adm_miniboss_inferno");
        blaze.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(300);
        blaze.setHealth(300);
        blaze.setPersistent(true);
        blaze.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(20);
        blaze.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).setBaseValue(9);
        blaze.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(7);
        blaze.setCustomNameVisible(true);
        blaze.customName(Component.text("⚛MINIBOSS ").color(TextColor.color(219, 42, 216)).decorate(TextDecoration.BOLD).append(Component.text("Inferno").color(TextColor.color(247, 163, 17)).decorate(TextDecoration.BOLD)));
        new BukkitRunnable(){

            @Override
            public void run() {
                if(blaze.isDead()){
                    if(blaze.getKiller() == null){
                        cancel();
                        return;
                    }
                    if(!AdvancedUtils.isUnlocked(MonsterAbility.FLAMING)){
                        if(blaze.isValid()){ cancel(); return; }
                        MonsterAbilityUnlockEvent event = new MonsterAbilityUnlockEvent(MonsterAbility.FLAMING);
                        if(blaze.getKiller().getHealth() <= 0){ cancel(); return; }
                        Bukkit.getServer().getPluginManager().callEvent(event);
                        if(!event.isCancelled()) {
                            AdvancedUtils.setUnlocked(event.getAbility(), true);
                        }
                    }
                    cancel();
                    return;
                }
                if(blaze.getHealth() <= blaze.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() / 2){
                    BlockUtilMethods.createCircle(blaze.getLocation(), 0.47F, 255, 238, 0, 0.78F);
                    BlockUtilMethods.createCircle(blaze.getLocation().add(0, 0.6,0), 0.47F, 255, 238, 0, 0.78F);
                    BlockUtilMethods.createCircle(blaze.getLocation().add(0, 1.2, 0), 0.47F, 255, 238, 0, 0.78F);
                }
            }
        }.runTaskTimer(AdvancedMonsters.getPlugin(AdvancedMonsters.class), 0L, 1L);
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event){
        if(!(event.getEntity() instanceof Blaze)) return;
        if(!event.getEntity().getScoreboardTags().contains("adm_miniboss_inferno")) return;
        if(((Blaze) event.getEntity()).getHealth() <= ((Blaze) event.getEntity()).getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() / 2){
            if(!event.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_ATTACK)){
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onShoot(ProjectileLaunchEvent event){
        if(!(event.getEntity().getShooter() instanceof Blaze blaze)) return;
        if(!(event.getEntity() instanceof SmallFireball fireball)) return;
        if(!blaze.getScoreboardTags().contains("adm_miniboss_inferno")) return;
        fireball.addScoreboardTag("adm_entity_inferno_fireball");
    }

    @EventHandler
    public void onHit(ProjectileHitEvent event){
        if(event.getHitEntity() == null) return;
        if(!(event.getHitEntity() instanceof LivingEntity)) return;
        if(!(event.getEntity().getShooter() instanceof Blaze blaze)) return;
        if(!(event.getEntity() instanceof SmallFireball fireball)) return;
        if(!blaze.getScoreboardTags().contains("adm_miniboss_inferno")) return;
        if(!fireball.getScoreboardTags().contains("adm_entity_inferno_fireball")) return;
        createInfernoEffect(((LivingEntity) event.getHitEntity()), blaze, 15);
    }

    private void createInfernoEffect(LivingEntity entity, Blaze attacker, double damage){
        new BukkitRunnable(){
            double phi = 0;
            final Location loc = entity.getLocation();
            @Override
            public void run() {
                phi += Math.PI/10;
                for(double theta = 0; theta <= 2*Math.PI; theta+= Math.PI/40){
                    if(entity.isDead()) cancel();
                    double r = 1.5;
                    double x = r*Math.cos(theta)*Math.sin(phi);
                    double y = r*Math.cos(phi) + 1.5;
                    double z = r*Math.sin(theta) * Math.sin(phi);
                    loc.add(x, y, z);
                    loc.getWorld().spawnParticle(Particle.DRIP_LAVA, loc, 1, 0 ,0, 0 ,0, null);
                    loc.subtract(x, y, z);
                    for(Entity entity1 : entity.getNearbyEntities(2.5, 2.5 ,2.5)){
                        if(entity1 instanceof LivingEntity living){
                            if(living != attacker){
                                living.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 40, 7, true, false, false));
                                living.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 40, 4, true, false, false));
                                living.damage(damage);
                                living.setVelocity(new Vector(0, 0, 0));
                                living.setFireTicks(200);
                            }
                        }
                    }
                    entity.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
                    entity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 40, 9, true, false, false));
                    entity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 40, 4, true, false, false));
                    entity.damage(damage, attacker);
                    entity.setVelocity(new Vector(0, 0, 0));
                    entity.setFireTicks(200);
                }

                if(phi > 2*Math.PI){
                    cancel();
                }
            }
        }.runTaskTimer(AdvancedMonsters.getPlugin(AdvancedMonsters.class), 0L, 2L);
    }
}
