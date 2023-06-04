package kro.dodoworld.advancedmonsters.modifier.ability.type;

import kro.dodoworld.advancedmonsters.config.modifier.FlamingModifierConfig;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;

public class FlamingModifier implements Listener {
    @EventHandler
    public void onAttack(EntityDamageByEntityEvent event){
        if(!(event.getDamager() instanceof Monster monster)) return;
        if(!(event.getEntity() instanceof LivingEntity entity)) return;
        if(!monster.getScoreboardTags().contains("adm_modifier_flaming")) return;
        double chance = Math.random() * 100;
        if(!(chance <= FlamingModifierConfig.getFlamingModifierConfig().getDouble("flaming_fire_effect_chance"))) return;
        int ticks = FlamingModifierConfig.getFlamingModifierConfig().getInt("flaming_fire_effect_ticks");
        entity.setFireTicks(ticks);
    }

    @EventHandler
    public void onShoot(ProjectileLaunchEvent event){
        if(event.getEntity().getShooter() == null) return;
        if(!(event.getEntity().getShooter() instanceof Skeleton)) return;
        Monster monster = (Monster) event.getEntity().getShooter();
        if(!monster.getScoreboardTags().contains("adm_modifier_flaming")) return;
        Arrow arrow = (Arrow) event.getEntity();
        arrow.setFireTicks(Integer.MAX_VALUE - 10000000);
    }

    @EventHandler
    public void onExplode(ExplosionPrimeEvent event){
        if(!(event.getEntity() instanceof Monster monster)) return;
        if(!monster.getScoreboardTags().contains("adm_modifier_flaming")) return;
        if(!FlamingModifierConfig.getFlamingModifierConfig().getBoolean("flaming_set_fire_on_explode")) return;
        event.setFire(true);
    }


    @EventHandler
    public void onDamage(EntityDamageEvent event){
        if(!(event.getEntity() instanceof Monster)) return;
        if(!(event.getCause().equals(EntityDamageEvent.DamageCause.FIRE) || event.getCause().equals(EntityDamageEvent.DamageCause.FIRE_TICK)
        || event.getCause().equals(EntityDamageEvent.DamageCause.LAVA))) return;
        if(!event.getEntity().getScoreboardTags().contains("adm_modifier_flaming")) return;
        event.setCancelled(true);
    }
}
