package kro.dodoworld.advancedmonsters.config.modifier;

import org.bukkit.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class VenomousModifierConfig {
    private static File file;
    private static FileConfiguration venomousModifierConfig;

    public static void init(){
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("AdvancedMonsters").getDataFolder() + "/ability_configs/", "venomous_ability_config.yml");

        if(!file.exists()){
            try{
                file.createNewFile();
            }catch (IOException e){
                Bukkit.getServer().getLogger().warning("No Plugin Folder Found. Creating New Folder...");
            }
        }

        venomousModifierConfig = YamlConfiguration.loadConfiguration(file);
        venomousModifierConfig.addDefault("apply_effect_chance", 100.0);
        venomousModifierConfig.addDefault("poison_effect_ticks", 200);
        venomousModifierConfig.addDefault("poison_effect_amplifier", 3);
        venomousModifierConfig.addDefault("weakness_effect_ticks", 240);
        venomousModifierConfig.addDefault("weakness_effect_amplifier", 2);
        venomousModifierConfig.addDefault("attack_damage_multiply_amount", 1.2);
        List<String> commandDescription = new ArrayList<>();
        commandDescription.add(ChatColor.YELLOW + "공격 시 {applyEffectChance}% 확률로 독에 걸린다.");
        venomousModifierConfig.addDefault("command_description", commandDescription);
        venomousModifierConfig.options().copyDefaults(true);
        saveConfig();
        reloadConfig();
    }

    public static FileConfiguration getVenomousModifierConfig() {
        return venomousModifierConfig;
    }

    public static void saveConfig(){
        try {
            venomousModifierConfig.save(file);
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public static void reloadConfig(){
        venomousModifierConfig = YamlConfiguration.loadConfiguration(file);
    }
}
