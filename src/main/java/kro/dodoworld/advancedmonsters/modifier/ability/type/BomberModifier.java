package kro.dodoworld.advancedmonsters.modifier.ability.type;

import kro.dodoworld.advancedmonsters.config.modifier.BomberModifierConfig;
import org.bukkit.Location;
import org.bukkit.entity.Monster;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;

public class BomberModifier implements Listener {
    @EventHandler
    public void onDeath(EntityDeathEvent event){
        if(!(event.getEntity() instanceof Monster)) return;
        if(!event.getEntity().getScoreboardTags().contains("adm_modifier_bomber")) return;
        double chance = Math.random() * 100;
        if(!(chance <= BomberModifierConfig.getBomberModifierConfig().getDouble("bomber_tnt_drop_chance"))) return;
        int ticks = BomberModifierConfig.getBomberModifierConfig().getInt("bomber_tnt_fuse_ticks");
        TNTPrimed tnt = event.getEntity().getLocation().getWorld().spawn(event.getEntity().getLocation(), TNTPrimed.class);
        tnt.setSource(event.getEntity());
        tnt.setFuseTicks(ticks);
    }

    @EventHandler
    public void onShoot(ProjectileHitEvent event){
        if(event.getEntity().getShooter() == null) return;
        if(event.getEntity().getShooter() instanceof Monster && ((Monster) event.getEntity().getShooter()).getScoreboardTags().contains("adm_modifier_bomber")) {
            if(event.getHitEntity() != null){
                createExplosion(event.getHitEntity().getLocation(), event.getEntity().getFireTicks() >= 1, ((Monster) event.getEntity().getShooter()));
            }else{
                if(event.getHitBlock() == null) return;
                createExplosion(event.getHitBlock().getLocation(), event.getEntity().getFireTicks() >= 1, ((Monster) event.getEntity().getShooter()));
            }
        }
    }

    private void createExplosion(Location loc, boolean fire, Monster shooter){
        loc.getWorld().createExplosion(loc, 4f, fire, false, shooter);
    }
}
