package kro.dodoworld.advancedmonsters.modifiers.ability.type;

import kro.dodoworld.advancedmonsters.config.modifier.TankModifierConfig;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class TankModifier implements Listener {
    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event){
        if(!(event.getEntity() instanceof Monster)) return;
        if(!event.getEntity().getScoreboardTags().contains("adm_modifier_tank")) return;
        FileConfiguration config = TankModifierConfig.getTankModifierConfig();
        double ignoreDamageChance = Math.random() * 100;
        if(ignoreDamageChance <= config.getDouble("ignore_damage_chance")){
            event.setCancelled(true);
            if(event.getDamager() instanceof Player){
                Player player = (Player) event.getDamager();
                if(!config.getBoolean("send_damage_nullify_message")) return;
                player.sendMessage(ChatColor.DARK_GRAY + "❇Tank" + ChatColor.RED + " 능력으로 인해 대미지가 무력화 되었습니다!");
            }
        }
    }
}
