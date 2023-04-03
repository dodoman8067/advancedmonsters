package kro.dodoworld.advancedmonsters.modifiers.equipment;

import kro.dodoworld.advancedmonsters.util.AdvancedMonstersUtilMethods;
import org.bukkit.Difficulty;
import org.bukkit.World;
import org.bukkit.entity.Monster;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class EntityEquipment implements Listener {
    @EventHandler
    public void onSpawn(CreatureSpawnEvent event){
        if(event.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.CUSTOM)) return;
        if(event.getEntity().getWorld().getDifficulty().equals(Difficulty.PEACEFUL)) return;
        if(!(event.getEntity() instanceof Monster entity)) return;
        if(event.getEntity().getScoreboardTags().contains("adm_miniboss")) return;
        World world = entity.getWorld();
        if(getLevel(world) >= 3.0){
        }
    }
    
    private double getLevel(World world){
        return AdvancedMonstersUtilMethods.getEquipmentLevel().getMonsterEquipmentLevel(world);
    }

}
