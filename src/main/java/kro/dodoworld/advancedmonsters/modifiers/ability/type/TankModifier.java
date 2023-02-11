package kro.dodoworld.advancedmonsters.modifiers.ability.type;

import org.bukkit.ChatColor;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.concurrent.ThreadLocalRandom;

public class TankModifier implements Listener {
    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event){
        if(!(event.getEntity() instanceof Monster)) return;
        if(!event.getEntity().getScoreboardTags().contains("adm_modifier_tank")) return;
        int ignoreDamageChance = ThreadLocalRandom.current().nextInt(0,4);
        if(ignoreDamageChance == 1){
            event.setCancelled(true);
            if(event.getDamager() instanceof Player){
                Player player = (Player) event.getDamager();
                player.sendMessage(ChatColor.DARK_GRAY + "❇Tank" + ChatColor.RED + " 능력으로 인해 대미지가 무력화 되었습니다!");
            }
        }
    }
}
