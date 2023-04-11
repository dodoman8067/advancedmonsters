package kro.dodoworld.advancedmonsters.modifiers.level;

import kro.dodoworld.advancedmonsters.config.data.MonsterEquipmentLevel;
import kro.dodoworld.advancedmonsters.event.WorldMonsterLevelIncreaseEvent;
import org.bukkit.Bukkit;
import org.bukkit.World;

public class MonsterLevel {

    public MonsterLevel(){

    }

    public double getMonsterEquipmentLevel(World world){
        return MonsterEquipmentLevel.getMonsterEquipmentLevel().getDouble(world.getName());
    }

    public void increase(World world, double amount){
        WorldMonsterLevelIncreaseEvent event = new WorldMonsterLevelIncreaseEvent(world, amount);
        Bukkit.getServer().getPluginManager().callEvent(event);
        if(event.isCancelled()) return;
        MonsterEquipmentLevel.getMonsterEquipmentLevel().set(event.getWorld().getName(), getMonsterEquipmentLevel(world) + event.getAmount());
        MonsterEquipmentLevel.saveConfig();
        MonsterEquipmentLevel.reloadConfig();
    }

    public void decrease(World world, double amount){
        MonsterEquipmentLevel.getMonsterEquipmentLevel().set(world.getName(), MonsterEquipmentLevel.getMonsterEquipmentLevel().getDouble(world.getName()) - amount);
        MonsterEquipmentLevel.saveConfig();
        MonsterEquipmentLevel.reloadConfig();
    }
}
