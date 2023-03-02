package kro.dodoworld.advancedmonsters.config.modifier;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class TankModifierConfig {
    private static File file;
    private static FileConfiguration tankModifierConfig;

    public static void init(){
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("AdvancedMonsters").getDataFolder() + "/ability_configs/", "tank_ability_config.yml");

        if(!file.exists()){
            try{
                file.createNewFile();
            }catch (IOException e){
                Bukkit.getServer().getLogger().warning("No Plugin Folder Found. Creating New Folder...");
            }
        }

        tankModifierConfig = YamlConfiguration.loadConfiguration(file);
        tankModifierConfig.addDefault("ignore_damage_chance", 35.0);
        tankModifierConfig.addDefault("send_damage_nullify_message", true);
        tankModifierConfig.addDefault("bouns_defence_amount", 15.0);
        tankModifierConfig.addDefault("speed_multiply_amount", 0.4);
        tankModifierConfig.options().copyDefaults(true);
        saveConfig();
        reloadConfig();
    }

    public static FileConfiguration getTankModifierConfig() {
        return tankModifierConfig;
    }

    public static void saveConfig(){
        try {
            tankModifierConfig.save(file);
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public static void reloadConfig(){
        tankModifierConfig = YamlConfiguration.loadConfiguration(file);
    }
}
