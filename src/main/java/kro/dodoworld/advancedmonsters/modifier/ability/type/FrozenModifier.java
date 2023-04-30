package kro.dodoworld.advancedmonsters.modifier.ability.type;

import kro.dodoworld.advancedmonsters.config.modifier.FrozenModifierConfig;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Monster;
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
}
