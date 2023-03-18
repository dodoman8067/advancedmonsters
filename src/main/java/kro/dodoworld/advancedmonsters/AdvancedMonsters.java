package kro.dodoworld.advancedmonsters;

import kro.dodoworld.advancedmonsters.command.UnlockedMobs;
import kro.dodoworld.advancedmonsters.config.data.RevealedAbilities;
import kro.dodoworld.advancedmonsters.config.modifier.*;
import kro.dodoworld.advancedmonsters.entity.MiniBossSpawn;
import kro.dodoworld.advancedmonsters.entity.miniboss.*;
import kro.dodoworld.advancedmonsters.modifiers.EntityModifier;
import kro.dodoworld.advancedmonsters.modifiers.ability.type.*;
import kro.dodoworld.advancedmonsters.config.data.UnlockedEntityAbilities;

import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.logging.Logger;

public final class AdvancedMonsters extends JavaPlugin {

    private final Logger logger = getLogger();

    @Override
    public void onEnable() {
        logger.info("Loading configs...");
        long configMs = System.currentTimeMillis();
        initConfigs();
        logger.info("Loading configs took " + (System.currentTimeMillis() - configMs) + "ms.");
        logger.info("Loading modifier threads...");
        long modifierMs = System.currentTimeMillis();
        VoidGloom voidGloom = new VoidGloom(this);
        TeleporterModifier.run(this);
        LaserModifier.run(this);
        StormyModifier.run(this);
        logger.info("Loading modifier threads took " + (System.currentTimeMillis() - modifierMs) + "ms.");
        logger.info("Loading listeners...");
        long eventMs = System.currentTimeMillis();
        getServer().getPluginManager().registerEvents(new Storm(this), this);
        getServer().getPluginManager().registerEvents(new MiniBossSpawn(this), this);
        getServer().getPluginManager().registerEvents(new PunchyModifier(this), this);
        getServer().getPluginManager().registerEvents(new TankModifier(), this);
        getServer().getPluginManager().registerEvents(new StrongModifier(), this);
        getServer().getPluginManager().registerEvents(new BoomerModifier(), this);
        getServer().getPluginManager().registerEvents(new FlamingModifier(), this);
        getServer().getPluginManager().registerEvents(new EntityModifier(), this);
        getServer().getPluginManager().registerEvents(new LeapingSpider(this), this);
        getServer().getPluginManager().registerEvents(new VenomousModifier(), this);
        getServer().getPluginManager().registerEvents(new Inferno(this), this);
        getServer().getPluginManager().registerEvents(new Bombie(this), this);
        getServer().getPluginManager().registerEvents(new EarthQuaker(), this);
        getServer().getPluginManager().registerEvents(voidGloom, this);
        getCommand("ability").setExecutor(new UnlockedMobs());
        logger.info("Loading listeners took " + (System.currentTimeMillis() - eventMs) + "ms.");
        logger.info("Plugin successfully enabled.");
    }

    private void initConfigs(){
        File file = new File(getDataFolder() + "/ability_configs/");
        if(!file.exists()) file.mkdir();
        File dataDir = new File(getDataFolder() + "/world_data/");
        if(!dataDir.exists()) dataDir.mkdir();
        UnlockedEntityAbilities.init();
        UnlockedEntityAbilities.saveConfig();
        UnlockedEntityAbilities.reloadConfig();
        RevealedAbilities.init();
        RevealedAbilities.saveConfig();
        RevealedAbilities.reloadConfig();
        HealthyModifierConfig.init();
        HealthyModifierConfig.saveConfig();
        HealthyModifierConfig.reloadConfig();
        StrongModifierConfig.init();
        StrongModifierConfig.saveConfig();
        StrongModifierConfig.reloadConfig();
        InvisibleModifierConfig.init();
        InvisibleModifierConfig.saveConfig();
        InvisibleModifierConfig.reloadConfig();
        BoomerModifierConfig.init();
        BoomerModifierConfig.saveConfig();
        BoomerModifierConfig.reloadConfig();
        FlamingModifierConfig.init();
        FlamingModifierConfig.saveConfig();
        FlamingModifierConfig.reloadConfig();
        LaserModifierConfig.init();
        LaserModifierConfig.saveConfig();
        LaserModifierConfig.reloadConfig();
        PunchyModifierConfig.init();
        PunchyModifierConfig.saveConfig();
        PunchyModifierConfig.reloadConfig();
        SpeedyModifierConfig.init();
        SpeedyModifierConfig.saveConfig();
        SpeedyModifierConfig.reloadConfig();
        StormyModifierConfig.init();
        StormyModifierConfig.saveConfig();
        StormyModifierConfig.reloadConfig();
        TankModifierConfig.init();
        TankModifierConfig.saveConfig();
        TankModifierConfig.reloadConfig();
        TeleportModifierConfig.init();
        TeleportModifierConfig.saveConfig();
        TeleportModifierConfig.reloadConfig();
        VenomousModifierConfig.init();
        VenomousModifierConfig.saveConfig();
        VenomousModifierConfig.reloadConfig();
    }

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
        // Plugin shutdown logic
        logger.info("Plugin successfully disabled.");
        removeEntities();
    }
}
