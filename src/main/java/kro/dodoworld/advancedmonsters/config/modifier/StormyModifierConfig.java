package kro.dodoworld.advancedmonsters.config.modifier;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StormyModifierConfig {
    private static File file;
    private static FileConfiguration stormyModifierConfig;

    public static void init(){
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("AdvancedMonsters").getDataFolder() + "/ability_configs/", "stormy_ability_config.yml");

        if(!file.exists()){
            try{
                file.createNewFile();
            }catch (IOException e){
                Bukkit.getServer().getLogger().warning("No Plugin Folder Found. Creating New Folder...");
            }
        }

        stormyModifierConfig = YamlConfiguration.loadConfiguration(file);
        stormyModifierConfig.addDefault("stormy_lighting_range", 40.0);
        stormyModifierConfig.addDefault("stormy_lighting_damage", 7.0);
        stormyModifierConfig.addDefault("show_lighting_damage_message", true);
        stormyModifierConfig.addDefault("stormy_slow_effect_ticks", 30);
        stormyModifierConfig.addDefault("stormy_slow_effect_amplifier", 3);
        stormyModifierConfig.addDefault("stormy_lighting_cooldown", 60);
        stormyModifierConfig.addDefault("only_spawn_when_storming", false);
        List<String> commandDescription = new ArrayList<>();
        commandDescription.add(ChatColor.YELLOW + "적이 {stormyLightingRange}블록 이내에 있다면,");
        commandDescription.add(ChatColor.YELLOW + "{stormyLightingCooldown}틱 마다 번개 소환 + {stormyLightingDamage}만큼의 대미지를 준다.");
        stormyModifierConfig.addDefault("command_description", commandDescription);
        stormyModifierConfig.options().copyDefaults(true);
        saveConfig();
        reloadConfig();
    }

    public static FileConfiguration getStormyModifierConfig() {
        return stormyModifierConfig;
    }

    public static void saveConfig(){
        try {
            stormyModifierConfig.save(file);
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public static void reloadConfig(){
        stormyModifierConfig = YamlConfiguration.loadConfiguration(file);
    }
}
