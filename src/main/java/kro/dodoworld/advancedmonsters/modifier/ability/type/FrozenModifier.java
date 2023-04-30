package kro.dodoworld.advancedmonsters.modifier.ability.type;

import kro.dodoworld.advancedmonsters.config.modifier.FrozenModifierConfig;
import kro.dodoworld.advancedmonsters.config.modifier.StrongModifierConfig;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class FrozenModifier implements Listener {
    @EventHandler
    public void onAttack(EntityDamageByEntityEvent event){
        if(!(event.getDamager() instanceof Monster)) return;
        if(!event.getDamager().getScoreboardTags().contains("adm_modifier_frozen")) return;
        FileConfiguration config = FrozenModifierConfig.getFrozenModifierConfig();
        if((Math.random() * 100) <= config.getDouble("freeze_effect_chance")){
            event.getEntity().setFreezeTicks(config.getInt("freeze_effect_ticks"));
        }
    }

    @EventHandler
    public void onArrowDamage(EntityDamageByEntityEvent event){
        if(event.getDamager() instanceof Projectile && ((Projectile) event.getDamager()).getShooter() != null && ((Projectile) event.getDamager()).getShooter() instanceof Monster){
            Projectile projectile = (Projectile) event.getDamager();
            Monster monster = (Monster) projectile.getShooter();
            if(!monster.getScoreboardTags().contains("adm_modifier_frozen")) return;
            FileConfiguration config = FrozenModifierConfig.getFrozenModifierConfig();
            if((Math.random() * 100) <= config.getDouble("freeze_effect_chance")){
                event.getEntity().setFreezeTicks(config.getInt("freeze_effect_ticks"));
            }
        }
    }
}
