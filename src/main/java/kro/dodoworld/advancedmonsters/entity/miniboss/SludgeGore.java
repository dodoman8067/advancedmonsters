package kro.dodoworld.advancedmonsters.entity.miniboss;

import kro.dodoworld.advancedmonsters.AdvancedMonsters;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Slime;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.SlimeSplitEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class SludgeGore implements Listener {
    public static void createSludgeGore(Location loc){
        Slime slime = loc.getWorld().spawn(loc, Slime.class);
        slime.setSize(5);
        slime.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(250);
        slime.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).setBaseValue(10);
        slime.getAttribute(Attribute.GENERIC_ATTACK_KNOCKBACK).setBaseValue(5);
        slime.setHealth(250);
        slime.customName(Component.text("⚛MINIBOSS ").color(TextColor.color(219, 42, 216)).decorate(TextDecoration.BOLD).append(Component.text("SludgeGore").color(TextColor.color(67, 191, 79))));
        slime.addScoreboardTag("adm_remove_when_reload");
        slime.addScoreboardTag("adm_miniboss_sludgegore");
        slime.setCustomNameVisible(true);
        new BukkitRunnable(){

            @Override
            public void run() {
                if(slime.isDead()){
                    cancel();
                }
            }
        }.runTaskTimer(AdvancedMonsters.getPlugin(AdvancedMonsters.class), 0L, 1L);
    }

    @EventHandler
    public void onDivide(SlimeSplitEvent event){
        if(!event.getEntity().getScoreboardTags().contains("adm_miniboss_sludgegore")) return;
        event.setCount(0);
    }

    @EventHandler
    public void onAttack(EntityDamageByEntityEvent event){
        if(!event.getDamager().getScoreboardTags().contains("adm_miniboss_sludgegore")) return;
        if(!(event.getEntity() instanceof LivingEntity entity)) return;
        entity.setVelocity(entity.getLocation().getDirection().multiply(-5));
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event){
        if(!event.getEntity().getScoreboardTags().contains("adm_miniboss_sludgegore")) return;
        if(event.getCause().equals(EntityDamageEvent.DamageCause.PROJECTILE) || event.getCause().equals(EntityDamageEvent.DamageCause.LAVA) || event.getCause().equals(EntityDamageEvent.DamageCause.FIRE) || event.getCause().equals(EntityDamageEvent.DamageCause.FIRE_TICK) || event.getCause().equals(EntityDamageEvent.DamageCause.MAGIC)){
            event.setCancelled(true);
        }
    }
}
