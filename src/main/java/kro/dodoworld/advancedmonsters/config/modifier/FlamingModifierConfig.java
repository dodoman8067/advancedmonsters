package kro.dodoworld.advancedmonsters.config.modifier;

import org.bukkit.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FlamingModifierConfig {
    private static File file;
    private static FileConfiguration flamingModifierConfig;

    public static void init(){
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("AdvancedMonsters").getDataFolder() + "/ability_configs/", "flaming_ability_config.yml");

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
        List<String> commandDescription = new ArrayList<>();
        commandDescription.add(ChatColor.YELLOW + "공격 시 {fireEffectChance}% 확률로 {fireEffectTicks}틱 동안 불에 붙는다.");
        flamingModifierConfig.addDefault("command_description", commandDescription);
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
