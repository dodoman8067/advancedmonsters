package kro.dodoworld.advancedmonsters;

import kro.dodoworld.advancedmonsters.command.UnlockedMobs;
import kro.dodoworld.advancedmonsters.config.data.RevealedAbilities;
import kro.dodoworld.advancedmonsters.config.modifier.*;
import kro.dodoworld.advancedmonsters.entity.MiniBossSpawn;
import kro.dodoworld.advancedmonsters.entity.miniboss.Inferno;
import kro.dodoworld.advancedmonsters.entity.miniboss.LeapingSpider;
import kro.dodoworld.advancedmonsters.entity.miniboss.VoidGloom;
import kro.dodoworld.advancedmonsters.modifiers.EntityModifier;
import kro.dodoworld.advancedmonsters.modifiers.ability.type.*;
import kro.dodoworld.advancedmonsters.config.data.UnlockedEntityAbilities;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.logging.Logger;

public final class AdvancedMonsters extends JavaPlugin {

    private final Logger logger = getLogger();

    @Override
    public void onEnable() {
        logger.info("Loading Configs...");
        long configMs = System.currentTimeMillis();
        initConfigs();
        logger.info("Loading Configs Took " + (System.currentTimeMillis() - configMs) + "ms.");
        logger.info("Loading Modifier Threads...");
        long modifierMs = System.currentTimeMillis();
        VoidGloom voidGloom = new VoidGloom(this);
        TeleporterModifier.run(this);
        LaserModifier.run(this);
        StormyModifier.run(this);
        logger.info("Loading Modifier Threads Took " + (System.currentTimeMillis() - modifierMs) + "ms.");
        logger.info("Loading Listeners...");
        long eventMs = System.currentTimeMillis();
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
        getServer().getPluginManager().registerEvents(voidGloom, this);
        getCommand("ability").setExecutor(new UnlockedMobs());
        logger.info("Loading Listeners Took " + (System.currentTimeMillis() - eventMs) + "ms.");
        logger.info("Plugin Successfully Enabled.");
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

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        logger.info("Plugin Successfully Disabled.");
    }
}
