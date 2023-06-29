package kro.dodoworld.advancedmonsters.config.data;

import kro.dodoworld.advancedmonsters.modifier.ability.MonsterAbility;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class UnlockedEntityAbilities {
    private static File file;
    private static FileConfiguration unlockedEntityAbilityConfig;

    public static void init(){
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("AdvancedMonsters").getDataFolder() + "/world_data/", "unlocked_entity_abilities.yml");

        if(!file.exists()){
            try{
                file.createNewFile();
            }catch (IOException e){
                Bukkit.getServer().getLogger().warning("No Plugin Folder Found. Creating New Folder...");
            }
        }

        unlockedEntityAbilityConfig = YamlConfiguration.loadConfiguration(file);
        for(MonsterAbility ability : MonsterAbility.values()){
            unlockedEntityAbilityConfig.addDefault(ability.toString().toLowerCase(), false);
        }
        unlockedEntityAbilityConfig.options().copyDefaults(true);
        saveConfig();
        reloadConfig();
    }

    public static FileConfiguration getUnlockedEntityAbilityConfig() {
        return unlockedEntityAbilityConfig;
    }

    public static void saveConfig(){
        try {
            unlockedEntityAbilityConfig.save(file);
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public static void reloadConfig(){
        unlockedEntityAbilityConfig = YamlConfiguration.loadConfiguration(file);
    }
}
