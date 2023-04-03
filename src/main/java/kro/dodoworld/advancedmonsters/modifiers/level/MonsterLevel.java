package kro.dodoworld.advancedmonsters.modifiers.level;

import kro.dodoworld.advancedmonsters.config.data.MonsterEquipmentLevel;
import kro.dodoworld.advancedmonsters.event.WorldMonsterLevelIncreaseEvent;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;

public class MonsterLevel {
    private final FileConfiguration config;

    public MonsterLevel(){
        config = MonsterEquipmentLevel.getMonsterEquipmentLevel();
    }

    public double getMonsterEquipmentLevel(World world){
        return config.getDouble(world.getName());
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public void increase(World world, double amount){
        WorldMonsterLevelIncreaseEvent event = new WorldMonsterLevelIncreaseEvent(world, amount);
        Bukkit.getServer().getPluginManager().callEvent(event);
        if(event.isCancelled()) return;
        config.set(event.getWorld().getName(), config.getDouble(event.getWorld().getName()) + event.getAmount());
    }

    public void decrease(World world, double amount){
        config.set(world.getName(), config.getDouble(world.getName()) - amount);
    }
}
