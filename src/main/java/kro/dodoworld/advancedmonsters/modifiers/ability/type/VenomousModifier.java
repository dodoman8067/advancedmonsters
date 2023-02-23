package kro.dodoworld.advancedmonsters.modifiers.ability.type;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class VenomousModifier implements Listener {
    @EventHandler
    public void onHit(EntityDamageByEntityEvent event){
        if(!(event.getDamager() instanceof Monster monster)) return;
        if(!(event.getEntity() instanceof LivingEntity entity)) return;
        if(!monster.getScoreboardTags().contains("adm_modifier_venomous")) return;
        event.setDamage(event.getFinalDamage() * 1.2);
        entity.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 200, 3, true, true, true));
        entity.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 240, 2, true, true, true));
    }
}
