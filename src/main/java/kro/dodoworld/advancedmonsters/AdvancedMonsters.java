package kro.dodoworld.advancedmonsters;

import kro.dodoworld.advancedmonsters.entity.MiniBossSpawn;
import kro.dodoworld.advancedmonsters.entity.miniboss.LeapingSpider;
import kro.dodoworld.advancedmonsters.entity.miniboss.VoidGloom;
import kro.dodoworld.advancedmonsters.modifiers.EntityModifier;
import kro.dodoworld.advancedmonsters.modifiers.ability.type.*;
import kro.dodoworld.advancedmonsters.modifiers.unlock.EntityAbilityConfig;
import org.bukkit.plugin.java.JavaPlugin;
public final class AdvancedMonsters extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        VoidGloom voidGloom = new VoidGloom(this);
        voidGloom.registerVoidGloom();
        TeleporterModifier.run(this);
        LaserModifier.run(this);
        LeapingSpider.registerLeapingSpider(this);
        StormyModifier.run(this);
        EntityAbilityConfig.init();
        EntityAbilityConfig.saveConfig();
        EntityAbilityConfig.reloadConfig();
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
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
