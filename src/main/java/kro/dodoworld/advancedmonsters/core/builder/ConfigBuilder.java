package kro.dodoworld.advancedmonsters.core.builder;

import kro.dodoworld.advancedmonsters.util.ConfigUtils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigBuilder {
    private FileConfiguration config;
    private final File file;

    public ConfigBuilder(File file){
        this.file = file;
        try{
            this.file.createNewFile();
        }catch (IOException e){
            e.printStackTrace();
        }
        config = YamlConfiguration.loadConfiguration(file);
    }

    public ConfigBuilder addOption(String path, Object value){
        this.config.addDefault(path, value);
        return this;
    }

    public FileConfiguration build(){
        this.config.options().copyDefaults(true);
        ConfigUtils.saveAndReloadConfig(this.config, this.file);
        return this.config;
    }
}
