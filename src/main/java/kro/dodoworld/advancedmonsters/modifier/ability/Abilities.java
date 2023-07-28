package kro.dodoworld.advancedmonsters.modifier.ability;

import kro.dodoworld.advancedmonsters.AdvancedMonsters;
import kro.dodoworld.advancedmonsters.core.builder.ConfigBuilder;
import kro.dodoworld.advancedmonsters.core.registry.Registry;
import kro.dodoworld.advancedmonsters.event.registry.RegistryInitializeEvent;
import kro.dodoworld.advancedmonsters.modifier.ability.custom.HealthyAbility;
import kro.dodoworld.advancedmonsters.modifier.ability.custom.SpeedyAbility;
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
    private static Ability speedy = null;
    private static final AdvancedMonsters PLUGIN_INSTANCE = AdvancedMonsters.getPlugin(AdvancedMonsters.class);

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onRegister(RegistryInitializeEvent event){
        Registry registry = event.getRegistry();
        healthy = createHealthy();
        registry.register(healthy);
        strong = createStrong();
        registry.register(strong);
        speedy = createSpeedy();
        registry.register(speedy);
    }

    private Ability createHealthy(){
        File healthyFile = new File(PLUGIN_INSTANCE.getDataFolder() + "/ability_configs/advancedmonsters/healthy_modifier_config.yml");

        List<String> healthyDescription = new ArrayList<>();
        healthyDescription.add("체력이 %healthy_health_multiply_amount%배가 된다.");
        FileConfiguration healthyConfig = new ConfigBuilder(healthyFile).addOption("healthy_health_multiply_amount", 2).addOption("command_description", healthyDescription).build();

        ConfigUtils.saveAndReloadConfig(healthyConfig, healthyFile);

        return new HealthyAbility(
                new NamespacedKey(PLUGIN_INSTANCE, "healthy"),
                Component.text("❤", NamedTextColor.RED),
                Component.text("Healthy", NamedTextColor.RED),
                healthyConfig,
                null,
                null
        );
    }

    private Ability createStrong(){
        File file = new File(PLUGIN_INSTANCE.getDataFolder() + "/ability_configs/advancedmonsters/strong_modifier_config.yml");

        List<String> strongDescription = new ArrayList<>();
        strongDescription.add("%strong_damage_multiply_chance%% 확률로 대미지가 %strong_damage_multiply_amount%배가 된다.");
        FileConfiguration strongConfig = new ConfigBuilder(file)
                .addOption("strong_damage_multiply_chance", 100.0)
                .addOption("strong_damage_multiply_amount", 2.75)
                .addOption("command_description", strongDescription)
                .build();

        ConfigUtils.saveAndReloadConfig(strongConfig, file);

        return new StrongAbility(
                new NamespacedKey(PLUGIN_INSTANCE, "strong"),
                Component.text("\ud83d\udde1", NamedTextColor.DARK_RED),
                Component.text("Strong", NamedTextColor.DARK_RED),
                strongConfig,
                null,
                null
        );
    }

    private Ability createSpeedy(){
        File file = new File(PLUGIN_INSTANCE.getDataFolder() + "/ability_configs/advancedmonsters/speedy_modifier_config.yml");

        List<String> speedyDescription = new ArrayList<>();
        speedyDescription.add("속도가 %speedy_speed_multiply_amount%배가 되지만,");
        speedyDescription.add("체력은 %speedy_health_multiply_amount%배가 된다.");
        FileConfiguration speedyConfig = new ConfigBuilder(file)
                .addOption("speedy_speed_multiply_amount", 2.0)
                .addOption("speedy_health_multiply_amount", 0.5)
                .addOption("command_description", speedyDescription)
                .build();

        ConfigUtils.saveAndReloadConfig(speedyConfig, file);

        return new SpeedyAbility(
                new NamespacedKey(PLUGIN_INSTANCE, "speedy"),
                Component.text("✴", NamedTextColor.WHITE),
                Component.text("Speedy", NamedTextColor.WHITE),
                speedyConfig,
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

    public static Ability getSpeedy() {
        return speedy;
    }
}
