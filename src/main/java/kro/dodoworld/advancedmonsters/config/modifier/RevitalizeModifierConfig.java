package kro.dodoworld.advancedmonsters.config.modifier;

import kro.dodoworld.advancedmonsters.AdvancedMonsters;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RevitalizeModifierConfig {
    private static File file;
    private static FileConfiguration revitalizeConfig;

    public static void init(){
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("AdvancedMonsters").getDataFolder() + "/ability_configs/", "revitalize_ability_config.yml");

        if(!file.exists()){
            try{
                file.createNewFile();
            }catch (IOException e){
                Bukkit.getServer().getLogger().warning("No Plugin Folder Found. Creating New Folder...");
            }
        }

        revitalizeConfig = YamlConfiguration.loadConfiguration(file);
        List<String> effectList = new ArrayList<>();
        effectList.add("invisibility");
        effectList.add("fire_resistance");
        effectList.add("resistance");
        effectList.add("strength");
        effectList.add("speed");
        effectList.add("absorption");
        revitalizeConfig.addDefault("revitalize_effects", effectList);
        revitalizeConfig.addDefault("revitalize_effect_apply_range", 8.0);
        revitalizeConfig.addDefault("revitalize_effect_apply_chance", 80.0);
        revitalizeConfig.addDefault("revitalize_effect_max_ticks", 2000);
        revitalizeConfig.addDefault("revitalize_apply_effects_per_tick_amount", 200);
        revitalizeConfig.addDefault("revitalize_max_effect_amount_per_entity", 3);
        for(String s : revitalizeConfig.getStringList("revitalize_effects")){
            if(PotionEffectType.getByKey(NamespacedKey.minecraft(s)) != null){
                revitalizeConfig.addDefault("revitalize_max_effect_amplifier_" + s, 1);
            }else{
                AdvancedMonsters.getPlugin(AdvancedMonsters.class).getLogger().warning("Plugin detected unknown potion effect while initializing revitalize_ability_config.yml!");
                AdvancedMonsters.getPlugin(AdvancedMonsters.class).getLogger().warning("Effect name : " + s);
            }
        }
        List<String> commandDescription = new ArrayList<>();
        commandDescription.add(ChatColor.YELLOW + "매 {revitalize_apply_effects_per_tick_amount}틱 마다 주변 {revitalize_effect_apply_range}블럭 사이에 있는 적들에게 {revitalize_effect_apply_chance}% 확률로");
        commandDescription.add(ChatColor.YELLOW + "최대 {revitalize_effect_max_ticks}틱 동안 효과를 부여한다.");
        revitalizeConfig.addDefault("command_description", commandDescription);
        revitalizeConfig.options().copyDefaults(true);
        saveConfig();
        reloadConfig();
    }

    public static FileConfiguration getRevitalizeModifierConfig() {
        return revitalizeConfig;
    }

    public static void saveConfig(){
        try {
            revitalizeConfig.save(file);
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public static void reloadConfig(){
        revitalizeConfig = YamlConfiguration.loadConfiguration(file);
    }
}
