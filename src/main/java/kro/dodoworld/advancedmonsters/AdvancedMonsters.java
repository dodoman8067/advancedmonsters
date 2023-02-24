package kro.dodoworld.advancedmonsters;

import kro.dodoworld.advancedmonsters.config.PluginConfigs;
import kro.dodoworld.advancedmonsters.entity.MiniBossSpawn;
import kro.dodoworld.advancedmonsters.entity.miniboss.LeapingSpider;
import kro.dodoworld.advancedmonsters.entity.miniboss.VoidGloom;
import kro.dodoworld.advancedmonsters.modifiers.EntityModifier;
import kro.dodoworld.advancedmonsters.modifiers.ability.type.BoomerModifier;
import kro.dodoworld.advancedmonsters.modifiers.ability.type.FlamingModifier;
import kro.dodoworld.advancedmonsters.modifiers.ability.type.LaserModifier;
import kro.dodoworld.advancedmonsters.modifiers.ability.type.PunchyModifier;
import kro.dodoworld.advancedmonsters.modifiers.ability.type.StormyModifier;
import kro.dodoworld.advancedmonsters.modifiers.ability.type.StrongModifier;
import kro.dodoworld.advancedmonsters.modifiers.ability.type.TankModifier;
import kro.dodoworld.advancedmonsters.modifiers.ability.type.TeleporterModifier;
import kro.dodoworld.advancedmonsters.modifiers.ability.type.VenomousModifier;
import kro.dodoworld.advancedmonsters.config.unlock.EntityAbilityConfig;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class AdvancedMonsters extends JavaPlugin {

    private final Logger logger = getLogger();

    @Override
    public void onEnable() {
        logger.info("Loading Configs...");
        long configMs = System.currentTimeMillis();
        EntityAbilityConfig.init();
        EntityAbilityConfig.saveConfig();
        EntityAbilityConfig.reloadConfig();
        PluginConfigs.init();
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
        getServer().getPluginManager().registerEvents(voidGloom, this);
        logger.info("Loading Listeners Took " + (System.currentTimeMillis() - eventMs) + "ms.");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
