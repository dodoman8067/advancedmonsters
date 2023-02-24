package kro.dodoworld.advancedmonsters.config.modifier;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class HealthyModifierConfig {
    private File file;
    private FileConfiguration healthyModifierConfig;

    public void init(){
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("AdvancedMonsters").getDataFolder() + "/ability_configs/", "healthy_ability_config.yml");

        if(!file.exists()){
            try{
                file.mkdir();
                file.createNewFile();
            }catch (IOException e){
                Bukkit.getServer().getLogger().warning("No Plugin Folder Found. Creating New Folder...");
            }
        }

        healthyModifierConfig = YamlConfiguration.loadConfiguration(file);
        healthyModifierConfig.addDefault("health_multiply_amount", 2.0);
        healthyModifierConfig.options().copyDefaults(true);
        saveConfig();
        reloadConfig();
    }

    public FileConfiguration getHealthyModifierConfig() {
        return healthyModifierConfig;
    }

    public void saveConfig(){
        try {
            healthyModifierConfig.save(file);
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public void reloadConfig(){
        healthyModifierConfig = YamlConfiguration.loadConfiguration(file);
    }
}
