package kro.dodoworld.advancedmonsters.modifier.level;

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
        double amount1 = 0;
        if(amount <= 0){
            amount1 = 0;
        }else{
            amount1 = amount;
        }
        MonsterEquipmentLevel.getMonsterEquipmentLevel().set(world.getName(), amount1);
        MonsterEquipmentLevel.saveConfig();
        MonsterEquipmentLevel.reloadConfig();
    }
}
