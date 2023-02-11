package kro.dodoworld.advancedmonsters;

import kro.dodoworld.advancedmonsters.entity.MiniBossSpawn;
import kro.dodoworld.advancedmonsters.entity.miniboss.LeapingSpider;
import kro.dodoworld.advancedmonsters.modifiers.EntityModifier;
import kro.dodoworld.advancedmonsters.modifiers.ability.type.*;
import org.bukkit.plugin.java.JavaPlugin;
public final class AdvancedMonsters extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        TeleporterModifier.run(this);
        LaserModifier.run(this);
        LeapingSpider.registerLeapingSpider(this);
        getServer().getPluginManager().registerEvents(new MiniBossSpawn(), this);
        getServer().getPluginManager().registerEvents(new PunchyModifier(this), this);
        getServer().getPluginManager().registerEvents(new TankModifier(), this);
        getServer().getPluginManager().registerEvents(new StrongModifier(), this);
        getServer().getPluginManager().registerEvents(new BoomerModifier(), this);
        getServer().getPluginManager().registerEvents(new FlamingModifier(), this);
        getServer().getPluginManager().registerEvents(new EntityModifier(), this);
        getServer().getPluginManager().registerEvents(new LeapingSpider(this), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
