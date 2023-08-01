package kro.dodoworld.advancedmonsters.modifier.ability.runnable;

import kro.dodoworld.advancedmonsters.modifier.ability.Ability;
import kro.dodoworld.advancedmonsters.modifier.ability.AbilityRunnable;
import kro.dodoworld.advancedmonsters.modifier.ability.custom.TeleporterAbility;
import kro.dodoworld.advancedmonsters.util.AbilityUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.event.player.PlayerTeleportEvent;

public class TeleporterRunnable extends AbilityRunnable {
    public TeleporterRunnable(Ability ability) {
        super(ability);
    }

    @Override
    public void run() {
        if(getAbility().getConfig() == null) return;
        for(World world : Bukkit.getWorlds()){
            for(LivingEntity entity : world.getLivingEntities()){
                if(!(entity instanceof Monster monster)) continue;
                if(TeleporterAbility.getTeleportingMonsters().contains(monster.getUniqueId()) && AbilityUtils.hasAbility(monster, getAbility())){
                    if(monster.isDead()){
                        TeleporterAbility.getTeleportingMonsters().remove(monster.getUniqueId());
                    }else if(monster.getTarget() != null && monster.getLocation().distance(monster.getTarget().getLocation()) > getAbility().getConfig().getDouble("teleporter_teleport_range")){
                        monster.teleport(monster.getTarget().getLocation(), PlayerTeleportEvent.TeleportCause.PLUGIN);
                    }
                }else if(!AbilityUtils.hasAbility(monster, getAbility())){
                    TeleporterAbility.getTeleportingMonsters().remove(monster.getUniqueId());
                }
                if(AbilityUtils.hasAbility(monster, getAbility())){
                    TeleporterAbility.getTeleportingMonsters().add(monster.getUniqueId());
                }
            }
        }
    }
}
