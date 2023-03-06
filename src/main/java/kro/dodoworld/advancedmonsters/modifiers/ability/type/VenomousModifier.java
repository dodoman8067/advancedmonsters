package kro.dodoworld.advancedmonsters.modifiers.ability.type;

import kro.dodoworld.advancedmonsters.config.modifier.VenomousModifierConfig;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class VenomousModifier implements Listener {
    @EventHandler
    public void onHit(EntityDamageByEntityEvent event){
        if(event.getDamager() instanceof Monster monster && event.getEntity() instanceof LivingEntity entity){
            if(!monster.getScoreboardTags().contains("adm_modifier_venomous")) return;
            FileConfiguration config = VenomousModifierConfig.getVenomousModifierConfig();
            if(!(Math.random() * 100 <= config.getDouble("apply_effect_chance"))) return;
            event.setDamage(event.getFinalDamage() * config.getDouble("attack_damage_multiply_amount"));
            int poisonTicks = config.getInt("poison_effect_ticks");
            int poisonAmplifier = config.getInt("poison_effect_amplifier");
            int weaknessTicks = config.getInt("weakness_effect_ticks");
            int weaknessAmplifier = config.getInt("weakness_effect_amplifier");
            entity.addPotionEffect(new PotionEffect(PotionEffectType.POISON,  poisonTicks, poisonAmplifier, true, true, true));
            entity.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, weaknessTicks, weaknessAmplifier, true, true, true));
        }
        if(event.getDamager() instanceof Projectile && ((Projectile) event.getDamager()).getShooter() instanceof Monster monster && event.getEntity() instanceof LivingEntity entity && ((Projectile) event.getDamager()).getShooter() != null){
            if(!monster.getScoreboardTags().contains("adm_modifier_venomous")) return;
            FileConfiguration config = VenomousModifierConfig.getVenomousModifierConfig();
            if(!(Math.random() * 100 <= config.getDouble("apply_effect_chance"))) return;
            event.setDamage(event.getFinalDamage() * config.getDouble("attack_damage_multiply_amount"));
            int poisonTicks = config.getInt("poison_effect_ticks");
            int poisonAmplifier = config.getInt("poison_effect_amplifier");
            int weaknessTicks = config.getInt("weakness_effect_ticks");
            int weaknessAmplifier = config.getInt("weakness_effect_amplifier");
            entity.addPotionEffect(new PotionEffect(PotionEffectType.POISON,  poisonTicks, poisonAmplifier, true, true, true));
            entity.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, weaknessTicks, weaknessAmplifier, true, true, true));
        }
    }
}
