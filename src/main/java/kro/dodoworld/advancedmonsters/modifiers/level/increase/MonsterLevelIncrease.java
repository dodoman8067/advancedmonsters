package kro.dodoworld.advancedmonsters.modifiers.level.increase;

import io.papermc.paper.event.player.PlayerDeepSleepEvent;
import kro.dodoworld.advancedmonsters.AdvancedMonsters;
import org.bukkit.Raid;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.SpawnCategory;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityBreedEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.raid.RaidTriggerEvent;

public class MonsterLevelIncrease implements Listener {
    @EventHandler
    public void onKill(EntityDeathEvent event){
        if(event.getEntity().getKiller() == null) return;
        if(!event.getEntity().getSpawnCategory().equals(SpawnCategory.MONSTER)) return;
        if(!event.getEntity().getKiller().getType().equals(EntityType.PLAYER)) return;
        if((Math.random() * 100) <= 1){
            AdvancedMonsters.getMonsterLevel().increase(event.getEntity().getWorld(), (Math.random() * 1.8));
        }
    }

    @EventHandler
    public void onSleep(PlayerDeepSleepEvent event){
        if((Math.random() * 100) <= 3){
            AdvancedMonsters.getMonsterLevel().increase(event.getPlayer().getWorld(), (Math.random() * 3));
        }
    }

    @EventHandler
    public void onFish(PlayerFishEvent event){
        if(!event.getState().equals(PlayerFishEvent.State.CAUGHT_FISH)) return;
        if((Math.random() * 100) <= 1){
            AdvancedMonsters.getMonsterLevel().increase(event.getPlayer().getWorld(), (Math.random() * 1.4));
        }
    }

    @EventHandler
    public void onBreed(EntityBreedEvent event){
        if(event.getBreeder() == null) return;
        if(!event.getBreeder().getType().equals(EntityType.PLAYER)) return;
        if((Math.random() * 100) <= 0.8){
            AdvancedMonsters.getMonsterLevel().increase(event.getBreeder().getWorld(), (Math.random() * 2));
        }
    }

    @EventHandler
    public void onRaid(RaidTriggerEvent event){
        if(!event.getRaid().getStatus().equals(Raid.RaidStatus.LOSS)) return;
        if((Math.random() * 100) <= 20){
            AdvancedMonsters.getMonsterLevel().increase(event.getPlayer().getWorld(), (Math.random() * 6));
        }
    }
}
