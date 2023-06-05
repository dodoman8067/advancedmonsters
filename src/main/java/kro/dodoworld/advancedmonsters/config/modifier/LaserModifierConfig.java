package kro.dodoworld.advancedmonsters.config.modifier;

import org.bukkit.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LaserModifierConfig {
    private static File file;
    private static FileConfiguration laserModifierConfig;

    public static void init(){
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("AdvancedMonsters").getDataFolder() + "/ability_configs/", "laser_ability_config.yml");

        if(!file.exists()){
            try{
                file.createNewFile();
            }catch (IOException e){
                Bukkit.getServer().getLogger().warning("No Plugin Folder Found. Creating New Folder...");
            }
        }

        laserModifierConfig = YamlConfiguration.loadConfiguration(file);
        laserModifierConfig.addDefault("laser_shoot_range", 50.0);
        laserModifierConfig.addDefault("laser_damage", 4.0);
        List<String> commandDescription = new ArrayList<>();
        commandDescription.add(ChatColor.YELLOW + "적이 {laser_shoot_range} 블록 이내에 있다면,");
        commandDescription.add(ChatColor.YELLOW + "{laser_damage} 대미지를 주는 레이저를 쏜다.");
        laserModifierConfig.addDefault("command_description", commandDescription);
        laserModifierConfig.options().copyDefaults(true);
        saveConfig();
        reloadConfig();
    }

    public static FileConfiguration getLaserModifierConfig() {
        return laserModifierConfig;
    }

    public static void saveConfig(){
        try {
            laserModifierConfig.save(file);
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public static void reloadConfig(){
        laserModifierConfig = YamlConfiguration.loadConfiguration(file);
    }
}
