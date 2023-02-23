package kro.dodoworld.advancedmonsters.modifiers.unlock;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class EntityAbilityConfig {
    private static File file;
    private static FileConfiguration entityAbilityConfig;

    public static void init(){
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("AdvancedMonsters").getDataFolder(), "unlocked_entity_abilities.yml");

        if(!file.exists()){
            try{
                file.createNewFile();
            }catch (IOException e){
                Bukkit.getLogger().warning("No Plugin Folder Found. Creating New Folder...");
            }
        }

        entityAbilityConfig = YamlConfiguration.loadConfiguration(file);
        entityAbilityConfig.addDefault("healthy", false);
        entityAbilityConfig.addDefault("strong", false);
        entityAbilityConfig.addDefault("tank", false);
        entityAbilityConfig.addDefault("speedy", false);
        entityAbilityConfig.addDefault("invisible", false);
        entityAbilityConfig.addDefault("laser", false);
        entityAbilityConfig.addDefault("boomer", false);
        entityAbilityConfig.addDefault("flaming", false);
        entityAbilityConfig.addDefault("punchy", false);
        entityAbilityConfig.addDefault("teleporter", false);
        entityAbilityConfig.addDefault("venomous", false);
        entityAbilityConfig.addDefault("stormy", false);
        entityAbilityConfig.options().copyDefaults(true);
        saveConfig();
        reloadConfig();
    }

    public static FileConfiguration getEntityAbilityConfig() {
        return entityAbilityConfig;
    }

    public static void saveConfig(){
        try {
            entityAbilityConfig.save(file);
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public static void reloadConfig(){
        entityAbilityConfig = YamlConfiguration.loadConfiguration(file);
    }
}
