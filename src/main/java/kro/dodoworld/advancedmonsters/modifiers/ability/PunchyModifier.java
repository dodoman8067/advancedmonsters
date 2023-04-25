package kro.dodoworld.advancedmonsters.modifiers.ability;

import kro.dodoworld.advancedmonsters.AdvancedMonsters;
import kro.dodoworld.advancedmonsters.config.modifier.PunchyModifierConfig;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.util.Vector;

public class PunchyModifier implements Listener {

    private static AdvancedMonsters plugin;

    public PunchyModifier(AdvancedMonsters plugin){
        PunchyModifier.plugin = plugin;
    }

    @EventHandler
    public void onHit(EntityDamageByEntityEvent event){
        if(event.getDamager() instanceof Monster && event.getEntity() instanceof LivingEntity){
            if(event.getDamager().getScoreboardTags().contains("adm_modifier_punchy")){
                double rnd = Math.random() * 100;
                if(rnd <= PunchyModifierConfig.getPunchyModifierConfig().getDouble("punch_air_chance")){
                    event.setCancelled(true);
                    LivingEntity player = (LivingEntity) event.getEntity();
                    Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                        player.setVelocity(new Vector(0,2,0));
                    }, 1L);
                    if(player instanceof Player && PunchyModifierConfig.getPunchyModifierConfig().getBoolean("show_punch_air_message")){
                        player.sendMessage(ChatColor.GREEN + "Punchy " + ChatColor.RED + "능력으로 의해 하늘로 날려졌습니다!");
                    }
                }
            }
        }
        if(event.getDamager() instanceof Projectile && ((Projectile) event.getDamager()).getShooter() instanceof Monster){
            if(!(((Monster) ((Projectile) event.getDamager()).getShooter()).getScoreboardTags().contains("adm_modifier_punchy"))) return;
            double rnd = Math.random() * 100;
            if(rnd <= PunchyModifierConfig.getPunchyModifierConfig().getDouble("punch_air_chance")) {
                event.setCancelled(true);
                LivingEntity player = (LivingEntity) event.getEntity();
                Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                    player.setVelocity(new Vector(0, 2, 0));
                }, 1L);
                if (player instanceof Player && PunchyModifierConfig.getPunchyModifierConfig().getBoolean("show_punch_air_message")) {
                    player.sendMessage(ChatColor.GREEN + "Punchy " + ChatColor.RED + "능력으로 의해 하늘로 날려졌습니다!");
                }
            }
        }
    }
}
