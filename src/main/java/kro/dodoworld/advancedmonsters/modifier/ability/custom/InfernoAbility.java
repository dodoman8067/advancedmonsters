package kro.dodoworld.advancedmonsters.modifier.ability.custom;

import kro.dodoworld.advancedmonsters.AdvancedMonsters;
import kro.dodoworld.advancedmonsters.core.registry.RegisterResult;
import kro.dodoworld.advancedmonsters.modifier.ability.Ability;
import kro.dodoworld.advancedmonsters.util.AbilityUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.damage.DamageType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class InfernoAbility extends Ability implements Listener {
    public InfernoAbility(@NotNull NamespacedKey id, @Nullable Component symbol, @NotNull Component name, @Nullable FileConfiguration abilityConfig, @Nullable TextColor displayColor, int spawnWeight) {
        super(id, symbol, name, abilityConfig, displayColor, spawnWeight);
    }

    @Override
    public @NotNull RegisterResult init() {
        if(getConfig() == null) return RegisterResult.FAIL;
        Bukkit.getPluginManager().registerEvents(this, AdvancedMonsters.getPlugin(AdvancedMonsters.class));
        return RegisterResult.SUCCESS;
    }

    @EventHandler
    public void onShoot(ProjectileLaunchEvent event){
        if(event.getEntity().getShooter() == null) return;
        if(!(event.getEntity().getShooter() instanceof Monster monster)) return;
        if(!AbilityUtils.hasAbility(monster, this)) return;
        event.getEntity().addScoreboardTag("adm_inferno_projectile");
    }

    @EventHandler
    public void onDamage(ProjectileHitEvent event){
        if(event.getEntity().getShooter() == null) return;
        if(event.getHitEntity() == null) return;
        if(!(event.getEntity().getShooter() instanceof LivingEntity shooter)) return;
        if(!event.getEntity().getScoreboardTags().contains("adm_inferno_projectile")) return;
        if(event.getHitEntity() instanceof LivingEntity living){
            createInfernoEffect(living, shooter, getConfig().getDouble("inferno_ring_damage"));
        }
    }

    @EventHandler
    public void onDamaged(EntityDamageEvent event){
        if(event.getEntity() instanceof Monster monster){
            if(!AbilityUtils.hasAbility(monster, this)) return;
            if(monster.getHealth() < monster.getAttribute(Attribute.MAX_HEALTH).getValue() / 2){
                if(event.getDamageSource().getDamageType().equals(DamageType.OUT_OF_WORLD) || event.getDamageSource().getDamageType().equals(DamageType.IN_WALL) || event.getDamageSource().getDamageType().equals(DamageType.GENERIC_KILL) || event.getDamageSource().getDamageType().equals(DamageType.GENERIC) || event.getDamageSource().getDamageType().equals(DamageType.MOB_ATTACK) || event.getDamageSource().getDamageType().equals(DamageType.PLAYER_ATTACK)) return;
                event.setCancelled(true);
                createCircle(monster.getLocation(), 0.47F, 255, 238, 0, 0.78F);
                createCircle(monster.getLocation().add(0, 0.6,0), 0.47F, 255, 238, 0, 0.78F);
                createCircle(monster.getLocation().add(0, 1.2, 0), 0.47F, 255, 238, 0, 0.78F);
            }
        }
    }

    private void createCircle(Location loc, float radius, int r, int g, int b, float size){
        for(double t = 0; t<50; t+=0.5){
            float x = radius*(float) Math.sin(t);
            float z = radius*(float) Math.cos(t);
            //Spawns particle
            loc.getWorld().spawnParticle(Particle.DUST, x + loc.getX(), loc.getY(), z + loc.getZ(), 1, 0 ,0, 0 ,0, new Particle.DustOptions(org.bukkit.Color.fromRGB(r, g, b), size));
        }
    }

    private void createInfernoEffect(LivingEntity entity, LivingEntity attacker, double damage){
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
                    loc.getWorld().spawnParticle(Particle.DRIPPING_LAVA, loc, 1, 0 ,0, 0 ,0, null);
                    loc.subtract(x, y, z);
                    for(Entity entity1 : entity.getNearbyEntities(2.5, 2.5 ,2.5)){
                        if(entity1 instanceof LivingEntity living){
                            if(living != attacker){
                                living.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 40, 7, true, false, false));
                                living.addPotionEffect(new PotionEffect(PotionEffectType.MINING_FATIGUE, 40, 4, true, false, false));
                                living.damage(damage);
                                living.setVelocity(new Vector(0, 0, 0));
                                living.setFireTicks(200);
                            }
                        }
                    }
                    entity.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
                    entity.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 40, 9, true, false, false));
                    entity.addPotionEffect(new PotionEffect(PotionEffectType.MINING_FATIGUE, 40, 4, true, false, false));
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

    @Override
    public boolean canSpawn(Monster monster) {
        return !(monster.getLocation().getBlock().getTemperature() <= 0.05) && monster.getType().equals(EntityType.BLAZE);
    }
}
