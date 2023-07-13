package kro.dodoworld.advancedmonsters.core.builder;

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
        try{
            this.config.save(file);
        }catch (IOException e){
            e.printStackTrace();
        }
        this.config = YamlConfiguration.loadConfiguration(file);
        return this.config;
    }
}
