package kro.dodoworld.advancedmonsters.config.modifier;

import org.bukkit.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
        List<String> commandDescription = new ArrayList<>();
        commandDescription.add(ChatColor.YELLOW + "죽일 시 TNT를 {tntDropChance} 확률로 드롭 한다.");
        boomerModifierConfig.addDefault("command_description", commandDescription);
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
