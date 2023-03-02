package kro.dodoworld.advancedmonsters.config.modifier;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class BoomerModifierConfig {
    private static File file;
    private static FileConfiguration boomerModifierConfig;

    public static void init(){
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("AdvancedMonsters").getDataFolder() + "/ability_configs/", "boomer_ability_config.yml");

        if(!file.exists()){
            try{
                file.createNewFile();
            }catch (IOException e){
                Bukkit.getServer().getLogger().warning("No Plugin Folder Found. Creating New Folder...");
            }
        }

        boomerModifierConfig = YamlConfiguration.loadConfiguration(file);
        boomerModifierConfig.addDefault("tnt_drop_chance", 100.0);
        boomerModifierConfig.addDefault("tnt_fuse_ticks", 70);
        boomerModifierConfig.options().copyDefaults(true);
        saveConfig();
        reloadConfig();
    }

    public static FileConfiguration getBoomerModifierConfig() {
        return boomerModifierConfig;
    }

    public static void saveConfig(){
        try {
            boomerModifierConfig.save(file);
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public static void reloadConfig(){
        boomerModifierConfig = YamlConfiguration.loadConfiguration(file);
    }
}
