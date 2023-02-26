package kro.dodoworld.advancedmonsters.config.modifier;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class TeleportModifierConfig {
    private static File file;
    private static FileConfiguration teleporterModifierConfig;

    public static void init(){
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("AdvancedMonsters").getDataFolder(), "teleporter_ability_config.yml");

        if(!file.exists()){
            try{
                file.createNewFile();
            }catch (IOException e){
                Bukkit.getServer().getLogger().warning("No Plugin Folder Found. Creating New Folder...");
            }
        }

        teleporterModifierConfig = YamlConfiguration.loadConfiguration(file);
        teleporterModifierConfig.addDefault("teleport_range", 4.0);
        teleporterModifierConfig.options().copyDefaults(true);
        saveConfig();
        reloadConfig();
    }

    public static FileConfiguration getTeleporterModifierConfig() {
        return teleporterModifierConfig;
    }

    public static void saveConfig(){
        try {
            teleporterModifierConfig.save(file);
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public static void reloadConfig(){
        teleporterModifierConfig = YamlConfiguration.loadConfiguration(file);
    }
}
