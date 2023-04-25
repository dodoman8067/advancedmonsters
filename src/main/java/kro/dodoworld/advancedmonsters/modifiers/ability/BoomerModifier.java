package kro.dodoworld.advancedmonsters.modifiers.ability;

import kro.dodoworld.advancedmonsters.config.modifier.BoomerModifierConfig;
import org.bukkit.entity.Monster;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;

public class BoomerModifier implements Listener {
    @EventHandler
    public void onDeath(EntityDeathEvent event){
        if(!(event.getEntity() instanceof Monster)) return;
        if(!event.getEntity().getScoreboardTags().contains("adm_modifier_boomer")) return;
        double chance = Math.random() * 100;
        if(!(chance <= BoomerModifierConfig.getBoomerModifierConfig().getDouble("tnt_drop_chance"))) return;
        int ticks = BoomerModifierConfig.getBoomerModifierConfig().getInt("tnt_fuse_ticks");
        TNTPrimed tnt = event.getEntity().getLocation().getWorld().spawn(event.getEntity().getLocation(), TNTPrimed.class);
        tnt.setSource(event.getEntity());
        tnt.setFuseTicks(ticks);
    }

    @EventHandler
    public void onShoot(ProjectileHitEvent event){
        if(event.getEntity().getShooter() == null) return;
        if(event.getEntity().getShooter() instanceof Monster && ((Monster) event.getEntity().getShooter()).getScoreboardTags().contains("adm_modifier_boomer")) {
            if(event.getHitEntity() != null){
                event.getHitEntity().getWorld().createExplosion(event.getHitEntity().getLocation(), 4f, false, true, (Monster) event.getEntity().getShooter());
            }else{
                if(event.getHitBlock() == null) return;
                event.getHitBlock().getWorld().createExplosion(event.getHitBlock().getLocation(), 4f, false, true, (Monster) event.getEntity().getShooter());
            }
        }
    }
}
