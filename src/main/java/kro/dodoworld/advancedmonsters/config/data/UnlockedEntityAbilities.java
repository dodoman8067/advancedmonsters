package kro.dodoworld.advancedmonsters.config.data;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class UnlockedEntityAbilities {
    private static File file;
    private static FileConfiguration unlockedEntityAbilityConfig;

    public static void init(){
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("AdvancedMonsters").getDataFolder() + "/world_data/", "unlocked_entity_abilities.yml");

        if(!file.exists()){
            try{
                file.createNewFile();
            }catch (IOException e){
                Bukkit.getServer().getLogger().warning("No Plugin Folder Found. Creating New Folder...");
            }
        }

        unlockedEntityAbilityConfig = YamlConfiguration.loadConfiguration(file);
        unlockedEntityAbilityConfig.addDefault("healthy", false);
        unlockedEntityAbilityConfig.addDefault("strong", false);
        unlockedEntityAbilityConfig.addDefault("tank", false);
        unlockedEntityAbilityConfig.addDefault("speedy", false);
        unlockedEntityAbilityConfig.addDefault("invisible", false);
        unlockedEntityAbilityConfig.addDefault("laser", false);
        unlockedEntityAbilityConfig.addDefault("boomer", false);
        unlockedEntityAbilityConfig.addDefault("flaming", false);
        unlockedEntityAbilityConfig.addDefault("punchy", false);
        unlockedEntityAbilityConfig.addDefault("teleporter", false);
        unlockedEntityAbilityConfig.addDefault("venomous", false);
        unlockedEntityAbilityConfig.addDefault("stormy", false);
        unlockedEntityAbilityConfig.options().copyDefaults(true);
        saveConfig();
        reloadConfig();
    }

    public static FileConfiguration getUnlockedEntityAbilityConfig() {
        return unlockedEntityAbilityConfig;
    }

    public static void saveConfig(){
        try {
            unlockedEntityAbilityConfig.save(file);
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public static void reloadConfig(){
        unlockedEntityAbilityConfig = YamlConfiguration.loadConfiguration(file);
    }
}
