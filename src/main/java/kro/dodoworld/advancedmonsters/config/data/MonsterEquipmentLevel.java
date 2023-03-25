package kro.dodoworld.advancedmonsters.config.data;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class MonsterEquipmentLevel {
    private static File file;
    private static FileConfiguration monsterEquipmentLevel;

    public static void init(){
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("AdvancedMonsters").getDataFolder() + "/world_data/", "monster_equipment_level.yml");

        if(!file.exists()){
            try{
                file.createNewFile();
            }catch (IOException e){
                Bukkit.getServer().getLogger().warning("No Plugin Folder Found. Creating New Folder...");
            }
        }

        monsterEquipmentLevel = YamlConfiguration.loadConfiguration(file);
        for(World world : Bukkit.getWorlds()){
            monsterEquipmentLevel.addDefault(world.getName(), 0.0);
        }
        monsterEquipmentLevel.options().copyDefaults(true);
        saveConfig();
        reloadConfig();
    }

    public static FileConfiguration getMonsterEquipmentLevel() {
        return monsterEquipmentLevel;
    }

    public static void saveConfig(){
        try {
            monsterEquipmentLevel.save(file);
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public static void reloadConfig(){
        monsterEquipmentLevel = YamlConfiguration.loadConfiguration(file);
    }
}
