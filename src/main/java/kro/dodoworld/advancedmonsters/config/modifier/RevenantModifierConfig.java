package kro.dodoworld.advancedmonsters.config.modifier;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RevenantModifierConfig {
    private static File file;
    private static FileConfiguration revenantModifierConfig;

    public static void init(){
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("AdvancedMonsters").getDataFolder() + "/ability_configs/", "revenant_ability_config.yml");

        if(!file.exists()){
            try{
                file.createNewFile();
            }catch (IOException e){
                Bukkit.getServer().getLogger().warning("No Plugin Folder Found. Creating New Folder...");
            }
        }

        revenantModifierConfig = YamlConfiguration.loadConfiguration(file);
        revenantModifierConfig.addDefault("monster_revive_range", 12.0);
        revenantModifierConfig.addDefault("monster_revive_chance", 45.0);
        List<String> commandDescription = new ArrayList<>();
        commandDescription.add(ChatColor.YELLOW + "이 능력을 가진 몬스터의 주위 {monsterReviveRange}블록 이내에서 죽는다면 {monsterReviveChance}% 확률로 부활한다.");
        revenantModifierConfig.addDefault("command_description", commandDescription);
        revenantModifierConfig.options().copyDefaults(true);
        saveConfig();
        reloadConfig();
    }

    public static FileConfiguration getRevenantModifierConfig() {
        return revenantModifierConfig;
    }

    public static void saveConfig(){
        try {
            revenantModifierConfig.save(file);
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public static void reloadConfig(){
        revenantModifierConfig = YamlConfiguration.loadConfiguration(file);
    }
}
