package kro.dodoworld.advancedmonsters.modifier.ability;

import kro.dodoworld.advancedmonsters.AdvancedMonsters;
import kro.dodoworld.advancedmonsters.core.builder.ConfigBuilder;
import kro.dodoworld.advancedmonsters.core.registry.Registry;
import kro.dodoworld.advancedmonsters.event.registry.RegistryInitializeEvent;
import kro.dodoworld.advancedmonsters.modifier.ability.custom.HealthyAbility;
import kro.dodoworld.advancedmonsters.modifier.ability.custom.StrongAbility;
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
    private static Ability strong = null;

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onRegister(RegistryInitializeEvent event){
        Registry registry = event.getRegistry();
        healthy = createHealthy();
        registry.registerAbility(healthy);
        strong = createStrong();
        registry.registerAbility(strong);
    }

    private Ability createHealthy(){
        File healthyFile = new File(AdvancedMonsters.getPlugin(AdvancedMonsters.class).getDataFolder() + "/ability_configs/healthy_modifier_config.yml");
        List<String> healthyDescription = new ArrayList<>();
        healthyDescription.add("체력이 %healthy_health_multiply_amount%배가 된다.");
        FileConfiguration healthyConfig = new ConfigBuilder(healthyFile).addOption("healthy_health_multiply_amount", 2).addOption("command_description", healthyDescription).build();
        ConfigUtils.saveAndReloadConfig(healthyConfig, healthyFile);
        return new HealthyAbility(
                new NamespacedKey(AdvancedMonsters.getPlugin(AdvancedMonsters.class), "healthy"),
                Component.text("❤", NamedTextColor.RED),
                Component.text("Healthy", NamedTextColor.RED),
                healthyConfig,
                null,
                null
        );
    }

    private Ability createStrong(){
        File file = new File(AdvancedMonsters.getPlugin(AdvancedMonsters.class).getDataFolder() + "/ability_configs/strong_modifier_config.yml");

        List<String> strongDescription = new ArrayList<>();
        strongDescription.add("%strong_damage_multiply_chance%% 확률로 대미지가 %strong_damage_multiply_amount%배가 된다.");
        FileConfiguration strongConfig = new ConfigBuilder(file)
                .addOption("strong_damage_multiply_chance", 100.0)
                .addOption("strong_damage_multiply_amount", 2.75)
                .addOption("command_description", strongDescription)
                .build();

        ConfigUtils.saveAndReloadConfig(strongConfig, file);

        return new StrongAbility(
                new NamespacedKey(AdvancedMonsters.getPlugin(AdvancedMonsters.class), "strong"),
                Component.text("\ud83d\udde1", NamedTextColor.DARK_RED),
                Component.text("Strong", NamedTextColor.DARK_RED),
                strongConfig,
                null,
                null
        );
    }

    public static Ability getHealthy() {
        return healthy;
    }

    public static Ability getStrong() {
        return strong;
    }
}
