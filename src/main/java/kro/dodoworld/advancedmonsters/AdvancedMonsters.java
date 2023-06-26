package kro.dodoworld.advancedmonsters;

import kro.dodoworld.advancedmonsters.command.*;
import kro.dodoworld.advancedmonsters.command.tab.*;
import kro.dodoworld.advancedmonsters.config.data.MonsterEquipmentLevel;
import kro.dodoworld.advancedmonsters.config.data.RevealedAbilities;
import kro.dodoworld.advancedmonsters.config.modifier.*;
import kro.dodoworld.advancedmonsters.entity.MiniBossSpawn;
import kro.dodoworld.advancedmonsters.entity.miniboss.*;
import kro.dodoworld.advancedmonsters.modifier.EntityModifier;
import kro.dodoworld.advancedmonsters.config.data.UnlockedEntityAbilities;
import kro.dodoworld.advancedmonsters.modifier.ability.AbilityUnlock;
import kro.dodoworld.advancedmonsters.modifier.ability.type.*;
import kro.dodoworld.advancedmonsters.modifier.equipment.EntityEquipment;
import kro.dodoworld.advancedmonsters.modifier.level.MonsterLevel;
import kro.dodoworld.advancedmonsters.modifier.level.increase.MonsterLevelIncrease;
import kro.dodoworld.advancedmonsters.util.AdvancedUtils;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.logging.Logger;

public final class AdvancedMonsters extends JavaPlugin {

    /**
     * TODO: Add Necromancer boss
     */

    private final Logger logger = getLogger();
    private static MonsterLevel monsterLevel;
    private final boolean beta = true;

    @Override
    public void onEnable() {
        if(!checkServerEnvironment()) return;
        logger.info("Loading configs...");
        long configMs = System.currentTimeMillis();
        MonsterEquipmentLevel.init();
        MonsterEquipmentLevel.saveConfig();
        MonsterEquipmentLevel.reloadConfig();
        monsterLevel = new MonsterLevel();
        initConfigs();
        logger.info("Loading configs took " + (System.currentTimeMillis() - configMs) + "ms.");
        logger.info("Loading modifier runnable classes...");
        long modifierMs = System.currentTimeMillis();
        TeleporterModifier.run(this);
        LaserModifier.run(this);
        StormyModifier.run(this);
        RevitalizeModifier.run(this);
        logger.info("Loading modifier runnable classes took " + (System.currentTimeMillis() - modifierMs) + "ms.");
        logger.info("Loading listeners...");
        long eventMs = System.currentTimeMillis();
        getServer().getPluginManager().registerEvents(new Storm(), this);
        getServer().getPluginManager().registerEvents(new MiniBossSpawn(), this);
        getServer().getPluginManager().registerEvents(new PunchyModifier(this), this);
        getServer().getPluginManager().registerEvents(new TankModifier(), this);
        getServer().getPluginManager().registerEvents(new StrongModifier(), this);
        getServer().getPluginManager().registerEvents(new BoomerModifier(), this);
        getServer().getPluginManager().registerEvents(new FlamingModifier(), this);
        getServer().getPluginManager().registerEvents(new FrozenModifier(), this);
        getServer().getPluginManager().registerEvents(new LightingModifier(), this);
        getServer().getPluginManager().registerEvents(new EntityModifier(), this);
        getServer().getPluginManager().registerEvents(new LeapingSpider(), this);
        getServer().getPluginManager().registerEvents(new VenomousModifier(), this);
        getServer().getPluginManager().registerEvents(new DiamondZombie(), this);
        getServer().getPluginManager().registerEvents(new Inferno(), this);
        getServer().getPluginManager().registerEvents(new Bombie(), this);
        getServer().getPluginManager().registerEvents(new EarthQuaker(), this);
        getServer().getPluginManager().registerEvents(new VoidGloom(), this);
        getServer().getPluginManager().registerEvents(new EntityEquipment(), this);
        getServer().getPluginManager().registerEvents(new MonsterLevelIncrease(), this);
        getServer().getPluginManager().registerEvents(new AbilityUnlock(), this);
        getServer().getPluginManager().registerEvents(new SludgeGore(), this);
        getServer().getPluginManager().registerEvents(new DeadlyAnimal(), this);
        logger.info("Loading listeners took " + (System.currentTimeMillis() - eventMs) + "ms.");
        logger.info("Loading commands...");
        long commandMs = System.currentTimeMillis();
        getCommand("ability").setExecutor(new AbilityCommand());
        getCommand("ability").setTabCompleter(new AbilityTabCompleter());
        getCommand("admminiboss").setExecutor(new MiniBossSpawnCommand());
        getCommand("admminiboss").setTabCompleter(new MiniBossSpawnTabCompleter());
        logger.info("Loading commands took " + (System.currentTimeMillis() - commandMs) + "ms.");
        logger.info("Plugin successfully enabled.");
    }

    public static MonsterLevel getMonsterLevel() {
        return monsterLevel;
    }

    private boolean isPaperServer(){
        try{
            Class<?> a = Class.forName("io.papermc.paper.entity.PaperBucketable");
        }catch (ClassNotFoundException e){
            return false;
        }
        return true;
    }

    private boolean checkServerEnvironment(){
        if(!isPaperServer()){
            logger.severe("Plugin requires Paper or fork of Paper server.");
            logger.severe("Disabling plugin...");
            getServer().getPluginManager().disablePlugin(this);
            return false;
        }
        logger.info("NMS version : " + AdvancedUtils.getNMSVersion());
        if(!AdvancedUtils.getNMSVersion().equals("v1_19_R3")){
            logger.warning("This plugin is designed to support v1_19_R3 (1.19.4)");
            logger.warning("Bugs may crawl up in this version.");
        }
        if(beta){
            logger.warning("You are running beta version of this plugin. (Plugin that has -dev on the end)");
            logger.warning("I suggest run stable version if you are NOT developer.");
        }
        return true;
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
        FrozenModifierConfig.init();
        FrozenModifierConfig.saveConfig();
        FrozenModifierConfig.reloadConfig();
        LightingModifierConfig.init();
        LightingModifierConfig.saveConfig();
        LightingModifierConfig.reloadConfig();
        RevitalizeModifierConfig.init();
        RevitalizeModifierConfig.saveConfig();
        RevitalizeModifierConfig.reloadConfig();
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
