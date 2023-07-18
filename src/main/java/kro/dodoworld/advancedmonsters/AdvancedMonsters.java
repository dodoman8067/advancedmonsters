package kro.dodoworld.advancedmonsters;

import kro.dodoworld.advancedmonsters.core.builder.ConfigBuilder;
import kro.dodoworld.advancedmonsters.core.registry.Registry;
import kro.dodoworld.advancedmonsters.event.registry.RegistryInitializeEvent;
import kro.dodoworld.advancedmonsters.modifier.ability.Abilities;
import kro.dodoworld.advancedmonsters.modifier.ability.Ability;
import kro.dodoworld.advancedmonsters.modifier.ability.custom.HealthyAbility;
import kro.dodoworld.advancedmonsters.system.entity.ModifierApplier;
import kro.dodoworld.advancedmonsters.util.ConfigUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Random;
import java.util.logging.Logger;

public final class AdvancedMonsters extends JavaPlugin {

    /**
     * TODO: Add Necromancer boss
     */

    private final Logger logger = getLogger();
    private final Random random = new Random();
    @Override
    public void onEnable() {
        if(!checkServerEnvironment()) return;
        initFiles();
        try{
            Registry.init(this);
        }catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        getServer().getPluginManager().registerEvents(new Abilities(), this);
        getServer().getPluginManager().registerEvents(new ModifierApplier(), this);
        logger.info("Plugin successfully started.");
    }


    /**
     * Checks this server is running Paper or fork of Paper.
     * @return true if Paper, otherwise false
     */
    private boolean isPaperServer(){
        try{
            Class<?> a = Class.forName("io.papermc.paper.entity.PaperBucketable");
        }catch (ClassNotFoundException e){
            return false;
        }
        return true;
    }

    /**
     * Checks server environment.
     * @return true, if server environment is good enough to enable this plugin, otherwise false
     */
    private boolean checkServerEnvironment(){
        if(!isPaperServer()){
            logger.severe("Plugin requires Paper or fork of Paper server.");
            logger.severe("Disabling plugin...");
            //Disables plugin
            getServer().getPluginManager().disablePlugin(this);
            return false;
        }
        return true;
    }

    /**
     * Removes entities with adm_remove_when_reload tag
     */
    private void removeEntities(){
        for(World world : getServer().getWorlds()){
            for(LivingEntity entity : world.getLivingEntities()){
                if(entity.getScoreboardTags().contains("adm_remove_when_reload")){
                    entity.remove();
                }
            }
        }
    }

    @Override
    public void onDisable() {
        logger.info("Plugin successfully disabled.");
        removeEntities();
    }

    private void initFiles(){
        File abilityConfigFolder = new File(getDataFolder() + "/ability_configs/");
        abilityConfigFolder.mkdir();
    }
}
