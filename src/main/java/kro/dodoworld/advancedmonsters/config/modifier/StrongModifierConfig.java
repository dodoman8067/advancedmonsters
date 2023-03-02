package kro.dodoworld.advancedmonsters.config.modifier;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class StrongModifierConfig {
    private static File file;
    private static FileConfiguration strongModifierConfig;

    public static void init(){
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("AdvancedMonsters").getDataFolder() + "/ability_configs/", "strong_ability_config.yml");

        if(!file.exists()){
            try{
                file.createNewFile();
            }catch (IOException e){
                Bukkit.getServer().getLogger().warning("No Plugin Folder Found. Creating New Folder...");
            }
        }
        strongModifierConfig = YamlConfiguration.loadConfiguration(file);
        strongModifierConfig.addDefault("damage_multiply_chance", 100.0);
        strongModifierConfig.addDefault("damage_multiply_amount", 2.75);
        strongModifierConfig.options().copyDefaults(true);
        saveConfig();
        reloadConfig();
    }

    public static FileConfiguration getStrongModifierConfig() {
        return strongModifierConfig;
    }

    public static void saveConfig(){
        try {
            strongModifierConfig.save(file);
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public static void reloadConfig(){
        strongModifierConfig = YamlConfiguration.loadConfiguration(file);
    }
}
