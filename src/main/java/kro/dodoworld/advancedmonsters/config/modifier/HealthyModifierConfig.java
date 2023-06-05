package kro.dodoworld.advancedmonsters.config.modifier;

import org.bukkit.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HealthyModifierConfig {
    private static File file;
    private static FileConfiguration healthyModifierConfig;

    public static void init(){
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("AdvancedMonsters").getDataFolder() + "/ability_configs/", "healthy_ability_config.yml");

        if(!file.exists()){
            try{
                file.createNewFile();
            }catch (IOException e){
                Bukkit.getServer().getLogger().warning("No Plugin Folder Found. Creating New Folder...");
            }
        }

        healthyModifierConfig = YamlConfiguration.loadConfiguration(file);
        healthyModifierConfig.addDefault("healthy_health_multiply_amount", 2.0);
        List<String> commandDescription = new ArrayList<>();
        commandDescription.add(ChatColor.YELLOW + "체력이 {healthy_health_multiply_amount}배 늘어난 채로 스폰된다.");
        healthyModifierConfig.addDefault("command_description", commandDescription);
        healthyModifierConfig.options().copyDefaults(true);
        saveConfig();
        reloadConfig();
    }

    public static FileConfiguration getHealthyModifierConfig() {
        return healthyModifierConfig;
    }

    public static void saveConfig(){
        try {
            healthyModifierConfig.save(file);
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public static void reloadConfig(){
        healthyModifierConfig = YamlConfiguration.loadConfiguration(file);
    }
}
