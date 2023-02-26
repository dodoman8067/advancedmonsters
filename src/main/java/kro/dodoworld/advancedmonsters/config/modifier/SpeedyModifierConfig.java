package kro.dodoworld.advancedmonsters.config.modifier;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class SpeedyModifierConfig {
    private static File file;
    private static FileConfiguration speedyModifierConfig;

    public static void init(){
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("AdvancedMonsters").getDataFolder(), "speedy_ability_config.yml");

        if(!file.exists()){
            try{
                file.createNewFile();
            }catch (IOException e){
                Bukkit.getServer().getLogger().warning("No Plugin Folder Found. Creating New Folder...");
            }
        }

        speedyModifierConfig = YamlConfiguration.loadConfiguration(file);
        speedyModifierConfig.addDefault("speed_multiply_amount", 2.0);
        speedyModifierConfig.addDefault("health_multiply_amount", 0.5);
        speedyModifierConfig.options().copyDefaults(true);
        saveConfig();
        reloadConfig();
    }

    public static FileConfiguration getSpeedyModifierConfig() {
        return speedyModifierConfig;
    }

    public static void saveConfig(){
        try {
            speedyModifierConfig.save(file);
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public static void reloadConfig(){
        speedyModifierConfig = YamlConfiguration.loadConfiguration(file);
    }
}
