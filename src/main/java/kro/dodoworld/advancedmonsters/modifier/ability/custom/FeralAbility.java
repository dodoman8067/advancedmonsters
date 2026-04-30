package kro.dodoworld.advancedmonsters.modifier.ability.custom;

import kro.dodoworld.advancedmonsters.AdvancedMonsters;
import kro.dodoworld.advancedmonsters.core.registry.RegisterResult;
import kro.dodoworld.advancedmonsters.modifier.ability.Ability;
import kro.dodoworld.advancedmonsters.util.AbilityUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class FeralAbility extends Ability implements Listener {


    public FeralAbility(@NotNull NamespacedKey id, @Nullable Component symbol, @NotNull Component name, @Nullable FileConfiguration abilityConfig, @Nullable TextColor displayColor, int spawnWeight) {
        super(id, symbol, name, abilityConfig, displayColor, spawnWeight);
    }

    @Override
    public @NotNull RegisterResult init() {
        if(getConfig() == null) return RegisterResult.FAIL;
        Bukkit.getPluginManager().registerEvents(this, AdvancedMonsters.getPlugin(AdvancedMonsters.class));
        return RegisterResult.SUCCESS;
    }

    @Override
    public void onSpawn(Monster monster){
        if(getConfig() == null) return;
        super.onSpawn(monster);
        double speedMultiplyAmount = getConfig().getDouble("feral_speed_multiply_amount");
        monster.getAttribute(Attribute.MOVEMENT_SPEED).setBaseValue(monster.getAttribute(Attribute.MOVEMENT_SPEED).getBaseValue() * speedMultiplyAmount);
        monster.getAttribute(Attribute.MOVEMENT_EFFICIENCY).setBaseValue(10);
        monster.getAttribute(Attribute.WATER_MOVEMENT_EFFICIENCY).setBaseValue(10);
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event){
        if(getConfig() == null) return;
        if(event.getDamager() instanceof Monster monster && event.getEntity() instanceof LivingEntity livingEntity) {
            if(!AbilityUtils.hasAbility(monster, this)) return;
            if(isMarkedDamage(event)) return;
            int count = ThreadLocalRandom.current().nextInt(getConfig().getInt("feral_max_attack_count")) + 1;
            for(int i = 0; i<count; i++){
                Bukkit.getScheduler().runTaskLater(AdvancedMonsters.getPlugin(AdvancedMonsters.class), () -> createAdditionalAttack(monster, event.getDamage(), livingEntity), i + 1);
            }
        }
        if(event.getDamager() instanceof Projectile projectile){
            if(!(projectile.getShooter() != null && projectile.getShooter() instanceof Monster monster)) return;
            if(!(event.getEntity() instanceof LivingEntity livingEntity)) return;
            if(!AbilityUtils.hasAbility(monster, this)) return;
            if(isMarkedDamage(event)) return;

            int count = ThreadLocalRandom.current().nextInt(getConfig().getInt("feral_max_attack_count")) + 1;
            for(int i = 0; i<count; i++){
                Bukkit.getScheduler().runTaskLater(AdvancedMonsters.getPlugin(AdvancedMonsters.class), () -> createAdditionalAttack(monster, event.getDamage(), livingEntity), i + 1);
            }
        }
    }

    private boolean isMarkedDamage(EntityDamageByEntityEvent event){
        List<MetadataValue> metadata = event.getEntity().getMetadata("adm_feral_damage");
        Plugin plugin = AdvancedMonsters.getPlugin(AdvancedMonsters.class);
        for(MetadataValue value : metadata){
            if(value.getOwningPlugin() == null) continue;
            if(value.getOwningPlugin().equals(plugin) && value.asBoolean()){
                return true;
            }
        }
        return false;
    }

    private void createAdditionalAttack(Monster attacker, double damage, LivingEntity damaged){
        if(damaged.isDead() || attacker.isDead()) return;
        damaged.setMetadata("adm_feral_damage", new FixedMetadataValue(AdvancedMonsters.getPlugin(AdvancedMonsters.class), true));
        damaged.setNoDamageTicks(0);
        damaged.damage(damage, attacker);
        attacker.heal(damage / 2, EntityRegainHealthEvent.RegainReason.MAGIC);

        spawnXShapeParticles(damaged);
        damaged.removeMetadata("adm_feral_damage", AdvancedMonsters.getPlugin(AdvancedMonsters.class));
    }

    public void spawnXShapeParticles(LivingEntity entity) {
        Location loc = entity.getLocation().add(0, 1, 0);
        double radius = 2; // Radius of the X shape
        int points = 20; // Number of particles per line

        Vector hurtDirection = getHurtDirection(entity); // Replace with actual method call

        drawDiagonalLine(loc, radius, points, hurtDirection, Math.toRadians(45));
        drawDiagonalLine(loc, radius, points, hurtDirection, Math.toRadians(-45));
    }

    private void drawDiagonalLine(Location center, double radius, int points, Vector hurtDir, double angle) {
        Vector dir1 = rotateVector(hurtDir, angle); // Rotate the hurt direction to form the diagonal
        Vector dir2 = dir1.clone().multiply(-1); // Opposite direction to form the other half of the line

        for (int i = 0; i < points; i++) {
            double factor = (i / (double) (points - 1)) * radius * 2;
            Location loc1 = center.clone().add(dir1.clone().multiply(factor - radius));
            Location loc2 = center.clone().add(dir2.clone().multiply(factor - radius));

            Particle.DustOptions dustOptions = new Particle.DustOptions(Color.fromRGB(163, 11, 11), 0.8F);

            center.getWorld().spawnParticle(Particle.DUST, loc1, 1, 0, 0, 0, 0, dustOptions);
            center.getWorld().spawnParticle(Particle.DUST, loc2, 1, 0, 0, 0, 0, dustOptions);
        }
    }

    private Vector rotateVector(Vector vector, double angle) {
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);
        double x = vector.getX() * cos - vector.getZ() * sin;
        double z = vector.getX() * sin + vector.getZ() * cos;
        return new Vector(x, vector.getY(), z);
    }

    private Vector getHurtDirection(LivingEntity entity) {
        return entity.getLocation().getDirection().normalize();
    }
}
