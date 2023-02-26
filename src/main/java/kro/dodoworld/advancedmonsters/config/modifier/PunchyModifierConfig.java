package kro.dodoworld.advancedmonsters.config.modifier;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class PunchyModifierConfig {
    private static File file;
    private static FileConfiguration punchyModifierConfig;

    public static void init(){
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("AdvancedMonsters").getDataFolder(), "punchy_ability_config.yml");

        if(!file.exists()){
            try{
                file.createNewFile();
            }catch (IOException e){
                Bukkit.getServer().getLogger().warning("No Plugin Folder Found. Creating New Folder...");
            }
        }

        punchyModifierConfig = YamlConfiguration.loadConfiguration(file);
        punchyModifierConfig.addDefault("punch_air_chance", 40.0);
        punchyModifierConfig.addDefault("show_punch_air_message", true);
        punchyModifierConfig.options().copyDefaults(true);
        saveConfig();
        reloadConfig();
    }

    public static FileConfiguration getPunchyModifierConfig() {
        return punchyModifierConfig;
    }

    public static void saveConfig(){
        try {
            punchyModifierConfig.save(file);
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public static void reloadConfig(){
        punchyModifierConfig = YamlConfiguration.loadConfiguration(file);
    }
}
