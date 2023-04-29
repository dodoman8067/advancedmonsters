package kro.dodoworld.advancedmonsters.config.modifier;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LightingModifierConfig {
    private static File file;
    private static FileConfiguration lightingModifierConfig;

    public static void init(){
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("AdvancedMonsters").getDataFolder() + "/ability_configs/", "lighting_ability_config.yml");

        if(!file.exists()){
            try{
                file.createNewFile();
            }catch (IOException e){
                Bukkit.getServer().getLogger().warning("No Plugin Folder Found. Creating New Folder...");
            }
        }

        lightingModifierConfig = YamlConfiguration.loadConfiguration(file);
        lightingModifierConfig.addDefault("lighting_strike_chance", 100.0);
        lightingModifierConfig.addDefault("max_lighting_strike_amount", 4);
        lightingModifierConfig.addDefault("lighting_damage_amount", 5.0);
        List<String> commandDescription = new ArrayList<>();
        commandDescription.add(ChatColor.YELLOW + "공격 시 {lightingStrikeChance}% 확률로 최대 {maxLightingStrikeAmount}번 만큼 ");
        commandDescription.add(ChatColor.YELLOW + "{lightingDamageAmount}대미지를 주는 번개를 생성한다.");
        lightingModifierConfig.addDefault("command_description", commandDescription);
        lightingModifierConfig.options().copyDefaults(true);
        saveConfig();
        reloadConfig();
    }

    public static FileConfiguration getLightingModifierConfig() {
        return lightingModifierConfig;
    }

    public static void saveConfig(){
        try {
            lightingModifierConfig.save(file);
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public static void reloadConfig(){
        lightingModifierConfig = YamlConfiguration.loadConfiguration(file);
    }
}
