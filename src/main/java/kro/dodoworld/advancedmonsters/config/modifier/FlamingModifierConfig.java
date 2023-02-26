package kro.dodoworld.advancedmonsters.config.modifier;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class FlamingModifierConfig {
    private static File file;
    private static FileConfiguration flamingModifierConfig;

    public static void init(){
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("AdvancedMonsters").getDataFolder(), "flaming_ability_config.yml");

        if(!file.exists()){
            try{
                file.createNewFile();
            }catch (IOException e){
                Bukkit.getServer().getLogger().warning("No Plugin Folder Found. Creating New Folder...");
            }
        }

        flamingModifierConfig = YamlConfiguration.loadConfiguration(file);
        flamingModifierConfig.addDefault("fire_effect_chance", 100.0);
        flamingModifierConfig.addDefault("fire_effect_ticks", 200);
        flamingModifierConfig.options().copyDefaults(true);
        saveConfig();
        reloadConfig();
    }

    public static FileConfiguration getFlamingModifierConfig() {
        return flamingModifierConfig;
    }

    public static void saveConfig(){
        try {
            flamingModifierConfig.save(file);
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public static void reloadConfig(){
        flamingModifierConfig = YamlConfiguration.loadConfiguration(file);
    }
}
