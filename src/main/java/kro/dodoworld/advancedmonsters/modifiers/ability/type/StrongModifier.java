package kro.dodoworld.advancedmonsters.modifiers.ability.type;

import kro.dodoworld.advancedmonsters.config.modifier.StrongModifierConfig;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class StrongModifier implements Listener {
    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event){
        if(event.getDamager() instanceof Monster){
            if(!event.getDamager().getScoreboardTags().contains("adm_modifier_strong")) return;
            FileConfiguration config = StrongModifierConfig.getStrongModifierConfig();
            if(!(Math.random() * 100 <= config.getDouble("damage_multiply_chance"))) return;
            event.setDamage(event.getFinalDamage() * config.getDouble("damage_multiply_amount"));
        }
        if(event.getDamager() instanceof Projectile && ((Projectile) event.getDamager()).getShooter() != null && ((Projectile) event.getDamager()).getShooter() instanceof Monster){
            Projectile projectile = (Projectile) event.getDamager();
            Monster monster = (Monster) projectile.getShooter();
            if(!monster.getScoreboardTags().contains("adm_modifier_strong")) return;
            FileConfiguration config = StrongModifierConfig.getStrongModifierConfig();
            if(!(Math.random() * 100 <= config.getDouble("damage_multiply_chance"))) return;
            event.setDamage(event.getFinalDamage() * config.getDouble("damage_multiply_amount"));
        }
    }
}
