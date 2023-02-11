package kro.dodoworld.advancedmonsters.modifiers.ability.type;

import kro.dodoworld.advancedmonsters.AdvancedMonsters;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class TeleporterModifier {


    public static void run(AdvancedMonsters plugin) {
        new BukkitRunnable(){

            @Override
            public void run() {
                for(World world : Bukkit.getWorlds()){
                    for(LivingEntity entity : world.getLivingEntities()){
                        if(entity instanceof Monster) {
                            Monster monster = (Monster) entity;
                            if(monster.getScoreboardTags().contains("adm_modifier_teleporter")){
                                if(monster.isDead()) cancel();
                                if(monster.getTarget() != null){
                                    if(!monster.getNearbyEntities(4, 4, 4).contains(monster.getTarget())){
                                        monster.teleport(monster.getTarget().getLocation(), PlayerTeleportEvent.TeleportCause.PLUGIN);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }.runTaskTimer(plugin, 0L, 2L);
    }


}
