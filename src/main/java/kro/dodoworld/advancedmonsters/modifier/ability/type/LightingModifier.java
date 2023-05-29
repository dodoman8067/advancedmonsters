package kro.dodoworld.advancedmonsters.modifier.ability.type;

import kro.dodoworld.advancedmonsters.config.modifier.LightingModifierConfig;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.Random;

public class LightingModifier implements Listener {
    private final Random rnd;

    public LightingModifier(){
        this.rnd = new Random();
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event){
        if(!(event.getDamager() instanceof Monster)) return;
        if(!(event.getEntity() instanceof LivingEntity)) return;
        if(!event.getDamager().getScoreboardTags().contains("adm_modifier_lighting")) return;
        FileConfiguration config = LightingModifierConfig.getLightingModifierConfig();
        double chance = config.getDouble("lighting_strike_chance");
        int maxAmount = config.getInt("max_lighting_strike_amount");
        double damageAmount = config.getDouble("lighting_damage_amount");

        if((Math.random() * 100) <= chance){
            int amount = rnd.nextInt(0, maxAmount + 1);
            for(int i = 0; i<=amount; i++){
                event.getEntity().getWorld().strikeLightningEffect(event.getEntity().getLocation());
                ((LivingEntity) event.getEntity()).damage(damageAmount);
            }
        }
    }

    @EventHandler
    public void onArrowDamage(EntityDamageByEntityEvent event){
        if(!(event.getDamager() instanceof Projectile)) return;
        if(((Projectile) event.getDamager()).getShooter() == null) return;
        if(!(((Projectile) event.getDamager()).getShooter() instanceof Monster monster)) return;
        if(!(event.getEntity() instanceof LivingEntity)) return;
        if(!monster.getScoreboardTags().contains("adm_modifier_lighting")) return;
        FileConfiguration config = LightingModifierConfig.getLightingModifierConfig();
        double chance = config.getDouble("lighting_strike_chance");
        int maxAmount = config.getInt("max_lighting_strike_amount");
        double damageAmount = config.getDouble("lighting_damage_amount");

        if((Math.random() * 100) <= chance){
            int amount = rnd.nextInt(0, maxAmount + 1);
            for(int i = 0; i<=amount; i++){
                event.getEntity().getWorld().strikeLightningEffect(event.getEntity().getLocation());
                ((LivingEntity) event.getEntity()).damage(damageAmount);
            }
        }
    }
}
