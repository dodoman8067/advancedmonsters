package kro.dodoworld.advancedmonsters.modifiers.ability.type;

import kro.dodoworld.advancedmonsters.AdvancedMonsters;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.util.Vector;

import java.util.concurrent.ThreadLocalRandom;

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
                if(rnd <= 40){
                    event.setCancelled(true);
                    LivingEntity player = (LivingEntity) event.getEntity();
                    Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                        player.setVelocity(new Vector(0,2,0));
                    }, 1L);
                    if(player instanceof Player){
                        player.sendMessage(ChatColor.GREEN + "Punchy " + ChatColor.RED + "능력으로 의해 하늘로 날려졌습니다!");
                    }
                }
            }
        }
    }
}
