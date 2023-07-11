package kro.dodoworld.advancedmonsters.config.modifier;

import org.bukkit.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BomberModifierConfig {
    private static File file;
    private static FileConfiguration bomberModifierConfig;

    public static void init(){
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("AdvancedMonsters").getDataFolder() + "/ability_configs/", "bomber_ability_config.yml");

        if(!file.exists()){
            try{
                file.createNewFile();
            }catch (IOException e){
                Bukkit.getServer().getLogger().warning("No Plugin Folder Found. Creating New Folder...");
            }
        }

        bomberModifierConfig = YamlConfiguration.loadConfiguration(file);
        bomberModifierConfig.addDefault("bomber_tnt_drop_chance", 100.0);
        bomberModifierConfig.addDefault("bomber_tnt_fuse_ticks", 70);
        List<String> commandDescription = new ArrayList<>();
        commandDescription.add(ChatColor.YELLOW + "죽일 시 TNT를 {bomber_tnt_drop_chance} 확률로 드롭 한다.");
        bomberModifierConfig.addDefault("command_description", commandDescription);
        bomberModifierConfig.options().copyDefaults(true);
        saveConfig();
        reloadConfig();
    }

    public static FileConfiguration getBomberModifierConfig() {
        return bomberModifierConfig;
    }

    public static void saveConfig(){
        try {
            bomberModifierConfig.save(file);
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public static void reloadConfig(){
        bomberModifierConfig = YamlConfiguration.loadConfiguration(file);
    }
}
