package kro.dodoworld.advancedmonsters;

import kro.dodoworld.advancedmonsters.core.builder.ConfigBuilder;
import kro.dodoworld.advancedmonsters.core.registry.Registry;
import kro.dodoworld.advancedmonsters.event.registry.RegistryInitializeEvent;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

public final class AdvancedMonsters extends JavaPlugin implements Listener {

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
        getServer().getPluginManager().registerEvents(this, this);
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

    @EventHandler
    public void onRegister(RegistryInitializeEvent event){
        File healthyFile = new File(getDataFolder() + "/ability_configs/healthy_modifier_config.yml");
        List<String> healthyDescription = new ArrayList<>();
        healthyDescription.add("체력이 {healthy_health_multiply_amount}배가 된다.");
        FileConfiguration healthyConfig = new ConfigBuilder(healthyFile).addOption("healthy_health_multiply_amount", 2).addOption("command_description", healthyDescription).build();
        ConfigUtils.saveAndReloadConfig(healthyConfig, healthyFile);
        event.getRegistry().registerAbility(new HealthyAbility(
                new NamespacedKey(this, "healthy"),
                Component.text("❤", NamedTextColor.RED),
                Component.text("Healthy", NamedTextColor.RED),
                healthyConfig,
                null,
                null
        ));
        for(Ability a : Registry.getRegisteredAbilities()){
            logger.info(((TextComponent) a.getName()).content() + " : " + a.getId().asString());
        }
    }

    private void initFiles(){
        File abilityConfigFolder = new File(getDataFolder() + "/ability_configs/");
        abilityConfigFolder.mkdir();
    }
}
