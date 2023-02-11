package kro.dodoworld.advancedmonsters.modifiers.ability.type;

import org.bukkit.entity.Monster;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class BoomerModifier implements Listener {
    @EventHandler
    public void onDeath(EntityDeathEvent event){
        if(!(event.getEntity() instanceof Monster)) return;
        if(!event.getEntity().getScoreboardTags().contains("adm_modifier_boomer")) return;
        TNTPrimed tnt = event.getEntity().getLocation().getWorld().spawn(event.getEntity().getLocation(), TNTPrimed.class);
        tnt.setSource(event.getEntity());
        tnt.setFuseTicks(70);
    }
}
