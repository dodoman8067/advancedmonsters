package kro.dodoworld.advancedmonsters.config.modifier;

import org.bukkit.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SpeedyModifierConfig {
    private static File file;
    private static FileConfiguration speedyModifierConfig;

    public static void init(){
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("AdvancedMonsters").getDataFolder() + "/ability_configs/", "speedy_ability_config.yml");

        if(!file.exists()){
            try{
                file.createNewFile();
            }catch (IOException e){
                Bukkit.getServer().getLogger().warning("No Plugin Folder Found. Creating New Folder...");
            }
        }

        speedyModifierConfig = YamlConfiguration.loadConfiguration(file);
        speedyModifierConfig.addDefault("speedy_speed_multiply_amount", 2.0);
        speedyModifierConfig.addDefault("speedy_health_multiply_amount", 0.5);
        List<String> commandDescription = new ArrayList<>();
        commandDescription.add(ChatColor.YELLOW + "속도가 {speedy_speed_multiply_amount}배가 되지만,");
        commandDescription.add(ChatColor.YELLOW + "체력은 {speedy_health_multiply_amount}배가 된다.");
        speedyModifierConfig.addDefault("command_description", commandDescription);
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
