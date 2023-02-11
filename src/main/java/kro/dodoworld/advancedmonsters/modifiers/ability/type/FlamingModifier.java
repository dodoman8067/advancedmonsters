package kro.dodoworld.advancedmonsters.modifiers.ability.type;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class FlamingModifier implements Listener {
    @EventHandler
    public void onAttack(EntityDamageByEntityEvent event){
        if(!(event.getDamager() instanceof Monster monster)) return;
        if(!(event.getEntity() instanceof LivingEntity entity)) return;
        if(!monster.getScoreboardTags().contains("adm_modifier_flaming")) return;
        entity.setFireTicks(200);
    }
}
