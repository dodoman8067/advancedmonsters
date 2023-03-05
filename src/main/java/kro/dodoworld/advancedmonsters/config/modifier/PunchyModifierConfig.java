package kro.dodoworld.advancedmonsters.config.modifier;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PunchyModifierConfig {
    private static File file;
    private static FileConfiguration punchyModifierConfig;

    public static void init(){
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("AdvancedMonsters").getDataFolder() + "/ability_configs/", "punchy_ability_config.yml");

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
        List<String> commandDescription = new ArrayList<>();
        commandDescription.add(ChatColor.YELLOW + "적을 {punchAirChance}% 확률로 하늘로 날린다.");
        punchyModifierConfig.addDefault("command_description", commandDescription);
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
