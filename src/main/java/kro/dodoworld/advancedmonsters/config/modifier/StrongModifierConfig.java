package kro.dodoworld.advancedmonsters.config.modifier;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
        List<String> commandDescription = new ArrayList<>();
        commandDescription.add(ChatColor.YELLOW + "{damageMultiplyChance}% 확률로 대미지가 {damageMultiplyAmount}배가 된다.");
        strongModifierConfig.addDefault("command_description", commandDescription);
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
