package kro.dodoworld.advancedmonsters.modifiers.equipment.level;

import kro.dodoworld.advancedmonsters.config.data.MonsterEquipmentLevel;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;

public class EquipmentLevel {
    private final FileConfiguration config;

    public EquipmentLevel(){
        config = MonsterEquipmentLevel.getMonsterEquipmentLevel();
    }

    public double getMonsterEquipmentLevel(World world){
        return config.getDouble(world.getName());
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public void increase(World world, double amount){
        config.set(world.getName(), config.getDouble(world.getName()) + amount);
    }

    public void decrease(World world, double amount){
        config.set(world.getName(), config.getDouble(world.getName()) - amount);
    }
}
