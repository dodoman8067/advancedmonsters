package kro.dodoworld.advancedmonsters.config.modifier;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FrozenModifierConfig {
    private static File file;
    private static FileConfiguration frozenModifierConfig;

    public static void init(){
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("AdvancedMonsters").getDataFolder() + "/ability_configs/", "frozen_ability_config.yml");

        if(!file.exists()){
            try{
                file.createNewFile();
            }catch (IOException e){
                Bukkit.getServer().getLogger().warning("No Plugin Folder Found. Creating New Folder...");
            }
        }

        frozenModifierConfig = YamlConfiguration.loadConfiguration(file);
        frozenModifierConfig.addDefault("frozen_freeze_effect_chance", 100.0);
        frozenModifierConfig.addDefault("frozen_freeze_effect_ticks", 200);
        List<String> commandDescription = new ArrayList<>();
        commandDescription.add(ChatColor.YELLOW + "공격 시 {frozen_freeze_effect_chance}% 확률로 {frozen_freeze_effect_ticks}틱 동안 몸이 얼어버린다.");
        frozenModifierConfig.addDefault("command_description", commandDescription);
        frozenModifierConfig.options().copyDefaults(true);
        saveConfig();
        reloadConfig();
    }

    public static FileConfiguration getFrozenModifierConfig() {
        return frozenModifierConfig;
    }

    public static void saveConfig(){
        try {
            frozenModifierConfig.save(file);
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public static void reloadConfig(){
        frozenModifierConfig = YamlConfiguration.loadConfiguration(file);
    }
}
