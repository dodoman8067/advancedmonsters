package kro.dodoworld.advancedmonsters;

import kro.dodoworld.advancedmonsters.core.builder.ConfigBuilder;
import kro.dodoworld.advancedmonsters.core.registry.Registry;
import kro.dodoworld.advancedmonsters.event.registry.RegistryInitializeEvent;
import kro.dodoworld.advancedmonsters.util.ConfigUtils;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Random;
import java.util.logging.Logger;

public final class AdvancedMonsters extends JavaPlugin implements Listener {

    /**
     * TODO: Add Necromancer boss
     */

    private final Logger logger = getLogger();
    private final Random random = new Random();
    private final File file = new File(getDataFolder(), "a.yml");
    @Override
    public void onEnable() {
        if(!checkServerEnvironment()) return;
        try{
            Registry.init(this);
        }catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        FileConfiguration configuration = new ConfigBuilder(file)
                .addOption("hello", 1)
                .addOption("world", false)
                .build();
        configuration.set("hello", random.nextInt(0, 100));
        configuration.set("world", random.nextBoolean());
        ConfigUtils.saveAndReloadConfig(configuration, file);
        logger.info(configuration.getInt("hello") + " ");
        logger.info(configuration.getBoolean("world") + " ");
        getServer().getPluginManager().registerEvents(this, this);
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

    @EventHandler
    public void onRegister(RegistryInitializeEvent event){
        logger.info("Registry event called.");
    }
}
