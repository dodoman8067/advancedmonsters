package kro.dodoworld.advancedmonsters.config.modifier;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InvisibleModifierConfig {
    private static File file;
    private static FileConfiguration invisibleModifierConfig;

    public static void init(){
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("AdvancedMonsters").getDataFolder() + "/ability_configs/", "invisible_ability_config.yml");

        if(!file.exists()){
            try{
                file.createNewFile();
            }catch (IOException e){
                Bukkit.getServer().getLogger().warning("No Plugin Folder Found. Creating New Folder...");
            }
        }

        invisibleModifierConfig = YamlConfiguration.loadConfiguration(file);
        List<String> commandDescription = new ArrayList<>();
        commandDescription.add(ChatColor.YELLOW + "투명하다.");
        invisibleModifierConfig.addDefault("command_description", commandDescription);
        invisibleModifierConfig.options().copyDefaults(true);
        saveConfig();
        reloadConfig();
    }

    public static FileConfiguration getInvisibleModifierConfig() {
        return invisibleModifierConfig;
    }

    public static void saveConfig(){
        try {
            invisibleModifierConfig.save(file);
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public static void reloadConfig(){
        invisibleModifierConfig = YamlConfiguration.loadConfiguration(file);
    }
}
