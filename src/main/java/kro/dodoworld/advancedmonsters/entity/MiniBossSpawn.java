package kro.dodoworld.advancedmonsters.entity;

import kro.dodoworld.advancedmonsters.AdvancedMonsters;
import kro.dodoworld.advancedmonsters.entity.miniboss.DiamondZombie;
import kro.dodoworld.advancedmonsters.entity.miniboss.LeapingSpider;
import kro.dodoworld.advancedmonsters.entity.miniboss.VoidGloom;
import org.bukkit.Difficulty;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Monster;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

import java.util.concurrent.ThreadLocalRandom;

public class MiniBossSpawn implements Listener {
    private AdvancedMonsters plugin;
    public MiniBossSpawn(AdvancedMonsters plugin){
        this.plugin = plugin;
    }
    @EventHandler
    public void onSpawn(EntitySpawnEvent event){
        if(event.getEntity().getWorld().getDifficulty().equals(Difficulty.PEACEFUL)) return;
        if(!(event.getEntity() instanceof Monster)) return;
        final ThreadLocalRandom rnd = ThreadLocalRandom.current();
        Monster entity = (Monster) event.getEntity();
        if((rnd.nextInt(0, 51) == 1)){

            if(entity.getType().equals(EntityType.ZOMBIE)){
                event.setCancelled(true);
                DiamondZombie.createZombie(entity.getLocation());
            }
            if(entity.getType().equals(EntityType.SPIDER)){
                event.setCancelled(true);
                LeapingSpider.createLeapingSpider(entity.getLocation());
            }
            if(entity.getType().equals(EntityType.ENDERMAN)){
                event.setCancelled(true);
                VoidGloom voidGloom = new VoidGloom(plugin);
                voidGloom.createVoidGloom(entity.getLocation());
            }
        }
    }
}
