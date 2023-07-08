package kro.dodoworld.advancedmonsters.modifier.level.increase;

import io.papermc.paper.event.player.PlayerDeepSleepEvent;
import kro.dodoworld.advancedmonsters.AdvancedMonsters;
import org.bukkit.Material;
import org.bukkit.Raid;
import org.bukkit.entity.Boss;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.SpawnCategory;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityBreedEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.raid.RaidFinishEvent;

public class MonsterLevelIncrease implements Listener {
    @EventHandler
    public void onKill(EntityDeathEvent event){
        if(event.getEntity().getKiller() == null) return;
        if(!event.getEntity().getSpawnCategory().equals(SpawnCategory.MONSTER)) return;
        if(event.getEntity() instanceof Boss) return;
        if(!(event.getEntity().getKiller() instanceof Player)) return;
        if((Math.random() * 100) <= 6) {
            AdvancedMonsters.getMonsterLevel().increase(event.getEntity().getWorld(), (Math.random() * 1.8));
        }
    }

    @EventHandler
    public void onSleep(PlayerDeepSleepEvent event){
        if((Math.random() * 100) <= 4){
            AdvancedMonsters.getMonsterLevel().increase(event.getPlayer().getWorld(), (Math.random() * 5));
        }
    }

    @EventHandler
    public void onFish(PlayerFishEvent event){
        if(!event.getState().equals(PlayerFishEvent.State.CAUGHT_FISH)) return;
        if((Math.random() * 100) <= 2){
            AdvancedMonsters.getMonsterLevel().increase(event.getPlayer().getWorld(), (Math.random() * 1.4));
        }
    }

    @EventHandler
    public void onEat(PlayerItemConsumeEvent event){
        if(!event.getItem().getType().isEdible()) return;
        if(!isMeat(event.getItem().getType())) return;
        if((Math.random() * 100) <= 0.2){
            AdvancedMonsters.getMonsterLevel().increase(event.getPlayer().getWorld(), (Math.random() * 0.5));
        }
    }

    @EventHandler
    public void onBreed(EntityBreedEvent event){
        if(event.getBreeder() == null) return;
        if(!event.getBreeder().getType().equals(EntityType.PLAYER)) return;
        if((Math.random() * 100) <= 0.6){
            AdvancedMonsters.getMonsterLevel().increase(event.getBreeder().getWorld(), (Math.random() * 2));
        }
    }

    @EventHandler
    public void onRaid(RaidFinishEvent event){
        if(!event.getRaid().getStatus().equals(Raid.RaidStatus.LOSS)) return;
        if((Math.random() * 100) <= 80){
            AdvancedMonsters.getMonsterLevel().increase(event.getWorld(), (Math.random() * 6));
        }
    }

    private boolean isMeat(Material material){
        if(!material.isEdible()) return false;
        switch (material){
            case BEEF, COOKED_BEEF, PORKCHOP, COOKED_PORKCHOP, CHICKEN, COOKED_CHICKEN, MUTTON, COOKED_MUTTON, RABBIT, COOKED_RABBIT, COD, COOKED_COD, SALMON, COOKED_SALMON -> {
                return true;
            }
        }
        return false;
    }
}
