package kro.dodoworld.advancedmonsters.config.modifier;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class StrongModifierConfig {
    private File file;
    private FileConfiguration strongModifierConfig;

    public void init(){
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("AdvancedMonsters").getDataFolder() + "/ability_configs/", "strong_ability_config.yml");

        if(!file.exists()){
            try{
                file.mkdir();
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

    public FileConfiguration getStrongModifierConfig() {
        return strongModifierConfig;
    }

    public void saveConfig(){
        try {
            strongModifierConfig.save(file);
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public void reloadConfig(){
        strongModifierConfig = YamlConfiguration.loadConfiguration(file);
    }
}
