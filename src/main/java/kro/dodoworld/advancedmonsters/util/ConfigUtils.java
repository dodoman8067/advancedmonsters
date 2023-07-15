package kro.dodoworld.advancedmonsters.util;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigUtils {
    public static void saveAndReloadConfig(FileConfiguration config, File file){
        try{
            config.save(file);
            config.load(file);
        }catch (IOException | InvalidConfigurationException e){
            e.printStackTrace();
        }
    }
}