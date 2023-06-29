package kro.dodoworld.advancedmonsters.config.data;

import kro.dodoworld.advancedmonsters.modifier.ability.MonsterAbility;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class RevealedAbilities {
    private static File file;
    private static FileConfiguration revealedAbilityConfig;

    public static void init(){
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("AdvancedMonsters").getDataFolder() + "/world_data/", "revealed_entity_abilities.yml");

        if(!file.exists()){
            try{
                file.createNewFile();
            }catch (IOException e){
                Bukkit.getServer().getLogger().warning("No Plugin Folder Found. Creating New Folder...");
            }
        }

        revealedAbilityConfig = YamlConfiguration.loadConfiguration(file);
        for(MonsterAbility ability : MonsterAbility.values()){
            revealedAbilityConfig.addDefault(ability.toString().toLowerCase(), false);
        }
        revealedAbilityConfig.options().copyDefaults(true);
        saveConfig();
        reloadConfig();
    }

    public static FileConfiguration getRevealedAbilityConfig() {
        return revealedAbilityConfig;
    }

    public static void saveConfig(){
        try {
            revealedAbilityConfig.save(file);
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public static void reloadConfig(){
        revealedAbilityConfig = YamlConfiguration.loadConfiguration(file);
    }
}
