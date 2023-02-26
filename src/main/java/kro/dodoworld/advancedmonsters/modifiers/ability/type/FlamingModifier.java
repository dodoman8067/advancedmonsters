package kro.dodoworld.advancedmonsters.modifiers.ability.type;

import kro.dodoworld.advancedmonsters.config.modifier.FlamingModifierConfig;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;

public class FlamingModifier implements Listener {
    @EventHandler
    public void onAttack(EntityDamageByEntityEvent event){
        if(!(event.getDamager() instanceof Monster monster)) return;
        if(!(event.getEntity() instanceof LivingEntity entity)) return;
        if(!monster.getScoreboardTags().contains("adm_modifier_flaming")) return;
        double chance = Math.random() * 100;
        if(!(chance <= FlamingModifierConfig.getFlamingModifierConfig().getDouble("fire_effect_chance"))) return;
        int ticks = FlamingModifierConfig.getFlamingModifierConfig().getInt("fire_effect_ticks");
        entity.setFireTicks(ticks);
    }

    @EventHandler
    public void onShoot(ProjectileLaunchEvent event){
        if(event.getEntity().getShooter() == null) return;
        if(!(event.getEntity().getShooter() instanceof Monster) && event.getEntity() instanceof Arrow) return;
        Monster monster = (Monster) event.getEntity().getShooter();
        if(!monster.getScoreboardTags().contains("adm_modifier_flaming")) return;
        Arrow arrow = (Arrow) event.getEntity();
        arrow.setFireTicks(Integer.MAX_VALUE - 10000000);
    }
}
