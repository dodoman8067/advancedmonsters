package kro.dodoworld.advancedmonsters.modifier.ability;

import kro.dodoworld.advancedmonsters.AdvancedMonsters;
import kro.dodoworld.advancedmonsters.core.builder.ConfigBuilder;
import kro.dodoworld.advancedmonsters.core.registry.Registry;
import kro.dodoworld.advancedmonsters.event.registry.RegistryInitializeEvent;
import kro.dodoworld.advancedmonsters.modifier.ability.custom.HealthyAbility;
import kro.dodoworld.advancedmonsters.util.ConfigUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Abilities implements Listener {

    private static Ability healthy = null;

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onRegister(RegistryInitializeEvent event){
        healthy = registerHealthy(event.getRegistry());
    }

    private Ability registerHealthy(Registry registry){
        File healthyFile = new File(AdvancedMonsters.getPlugin(AdvancedMonsters.class).getDataFolder() + "/ability_configs/advancedmonsters:healthy_modifier_config.yml");
        List<String> healthyDescription = new ArrayList<>();
        healthyDescription.add("체력이 %healthy_health_multiply_amount%배가 된다.");
        FileConfiguration healthyConfig = new ConfigBuilder(healthyFile).addOption("healthy_health_multiply_amount", 2).addOption("command_description", healthyDescription).build();
        ConfigUtils.saveAndReloadConfig(healthyConfig, healthyFile);
        HealthyAbility ability = new HealthyAbility(
                new NamespacedKey(AdvancedMonsters.getPlugin(AdvancedMonsters.class), "healthy"),
                Component.text("❤", NamedTextColor.RED),
                Component.text("Healthy", NamedTextColor.RED),
                healthyConfig,
                null,
                null
        );
        registry.registerAbility(ability);
        return ability;
    }

    public static Ability getHealthy() {
        return healthy;
    }
}
