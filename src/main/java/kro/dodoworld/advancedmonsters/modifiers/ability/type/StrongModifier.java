package kro.dodoworld.advancedmonsters.modifiers.ability.type;

import org.bukkit.entity.Monster;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class StrongModifier implements Listener {
    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event){
        if(event.getDamager() instanceof Monster){
            if(!event.getDamager().getScoreboardTags().contains("adm_modifier_strong")) return;
            event.setDamage(event.getFinalDamage() * 2);
        }
    }
}
