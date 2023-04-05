package kro.dodoworld.advancedmonsters.modifiers.equipment;

import kro.dodoworld.advancedmonsters.AdvancedMonsters;
import kro.dodoworld.advancedmonsters.modifiers.equipment.armor.EntityArmor;
import org.bukkit.Difficulty;
import org.bukkit.World;
import org.bukkit.entity.Monster;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.inventory.EquipmentSlot;

public class EntityEquipment implements Listener {
    @EventHandler
    public void onSpawn(CreatureSpawnEvent event){
        if(event.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.CUSTOM)) return;
        if(event.getEntity().getWorld().getDifficulty().equals(Difficulty.PEACEFUL)) return;
        if(!(event.getEntity() instanceof Monster entity)) return;
        if(event.getEntity().getScoreboardTags().contains("adm_miniboss")) return;
        World world = entity.getWorld();

        if((Math.random() * 100) <= getLevel(world) * 2){
            entity.getEquipment().setHelmet(EntityArmor.getRandomArmor(world, EquipmentSlot.HEAD));
            entity.getEquipment().setChestplate(EntityArmor.getRandomArmor(world, EquipmentSlot.CHEST));
            entity.getEquipment().setLeggings(EntityArmor.getRandomArmor(world, EquipmentSlot.LEGS));
            entity.getEquipment().setBoots(EntityArmor.getRandomArmor(world, EquipmentSlot.FEET));
        }
    }
    
    private double getLevel(World world){
        return AdvancedMonsters.getMonsterLevel().getMonsterEquipmentLevel(world);
    }

}
