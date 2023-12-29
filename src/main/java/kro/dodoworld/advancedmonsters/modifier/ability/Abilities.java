package kro.dodoworld.advancedmonsters.modifier.ability;

import kro.dodoworld.advancedmonsters.AdvancedMonsters;
import kro.dodoworld.advancedmonsters.core.builder.ConfigBuilder;
import kro.dodoworld.advancedmonsters.core.registry.Registry;
import kro.dodoworld.advancedmonsters.event.registry.RegistryInitializeEvent;
import kro.dodoworld.advancedmonsters.modifier.ability.custom.BomberAbility;
import kro.dodoworld.advancedmonsters.modifier.ability.custom.FlamingAbility;
import kro.dodoworld.advancedmonsters.modifier.ability.custom.HealthyAbility;
import kro.dodoworld.advancedmonsters.modifier.ability.custom.LaserAbility;
import kro.dodoworld.advancedmonsters.modifier.ability.custom.PunchyAbility;
import kro.dodoworld.advancedmonsters.modifier.ability.custom.SpeedyAbility;
import kro.dodoworld.advancedmonsters.modifier.ability.custom.StrongAbility;
import kro.dodoworld.advancedmonsters.modifier.ability.custom.TankAbility;
import kro.dodoworld.advancedmonsters.modifier.ability.custom.TeleporterAbility;
import kro.dodoworld.advancedmonsters.util.ConfigUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public final class Abilities implements Listener {

    private static Ability healthy = null;
    private static Ability strong = null;
    private static Ability speedy = null;
    private static Ability tank = null;
    private static Ability teleporter = null;
    private static Ability bomber = null;
    private static Ability laser = null;
    private static Ability flaming = null;
    private static Ability punchy = null;
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

        tank = createTank();
        registry.register(tank);

        teleporter = createTeleporter();
        registry.register(teleporter);

        bomber = createBomber();
        registry.register(bomber);

        laser = createLaser();
        registry.register(laser);

        flaming = createFlaming();
        registry.register(flaming);

        punchy = createPunchy();
        registry.register(punchy);
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
                null
        );
    }

    private Ability createTank(){
        File file = new File(PLUGIN_INSTANCE.getDataFolder() + "/ability_configs/advancedmonsters/tank_modifier_config.yml");

        List<String> description = new ArrayList<>();
        description.add("%tank_ignore_damage_chance%% 확률로 대미지를 무시한다.");
        description.add("%tank_bonus_defence_amount%의 추가 방어력을 갖지만, 속도는 %tank_speed_multiply_amount%배가 된다.");
        FileConfiguration config = new ConfigBuilder(file)
                .addOption("tank_ignore_damage_chance", 35.0)
                .addOption("tank_send_damage_nullify_message", true)
                .addOption("tank_bonus_defence_amount", 15)
                .addOption("tank_speed_multiply_amount", 0.4)
                .addOption("command_description", description)
                .build();

        ConfigUtils.saveAndReloadConfig(config, file);

        return new TankAbility(
                new NamespacedKey(PLUGIN_INSTANCE, "tank"),
                Component.text("❇", NamedTextColor.DARK_GRAY),
                Component.text("Tank", NamedTextColor.DARK_GRAY),
                config,
                null
        );
    }

    private Ability createTeleporter(){
        File file = new File(PLUGIN_INSTANCE.getDataFolder() + "/ability_configs/advancedmonsters/teleporter_modifier_config.yml");

        List<String> description = new ArrayList<>();
        description.add("적이 주변 %teleporter_teleport_range%블록 이내에 없다면 적의 위치로 텔레포트한다.");
        FileConfiguration config = new ConfigBuilder(file)
                .addOption("teleporter_teleport_range", 4.0)
                .addOption("command_description", description)
                .build();

        ConfigUtils.saveAndReloadConfig(config, file);

        return new TeleporterAbility(
                new NamespacedKey(PLUGIN_INSTANCE, "teleporter"),
                Component.text("☯", NamedTextColor.DARK_AQUA),
                Component.text("Teleporter", NamedTextColor.DARK_AQUA),
                config,
                null
        );
    }

    private Ability createBomber(){
        File file = new File(PLUGIN_INSTANCE.getDataFolder() + "/ability_configs/advancedmonsters/bomber_modifier_config.yml");

        List<String> description = new ArrayList<>();
        description.add("적이 주변 %teleporter_teleport_range%블록 이내에 없다면 적의 위치로 텔레포트한다.");
        FileConfiguration config = new ConfigBuilder(file)
                .addOption("bomber_tnt_drop_chance", 100.0)
                .addOption("bomber_projectile_explode_chance", 90.0)
                .addOption("bomber_tnt_fuse_ticks", 70)
                .addOption("command_description", description)
                .build();

        ConfigUtils.saveAndReloadConfig(config, file);

        return new BomberAbility(
                new NamespacedKey(PLUGIN_INSTANCE, "bomber"),
                Component.text("■", NamedTextColor.RED),
                Component.text("Bomber", NamedTextColor.RED),
                config,
                null
        );
    }

    private Ability createLaser(){
        File file = new File(PLUGIN_INSTANCE.getDataFolder() + "/ability_configs/advancedmonsters/laser_modifier_config.yml");

        List<String> description = new ArrayList<>();
        description.add("적이 %laser_shoot_range% 블록 이내에 있다면,");
        description.add("%laser_damage% 대미지를 주는 레이저를 쏜다.");
        FileConfiguration config = new ConfigBuilder(file)
                .addOption("laser_shoot_range", 50.0)
                .addOption("laser_damage", 4.0)
                .addOption("command_description", description)
                .build();

        ConfigUtils.saveAndReloadConfig(config, file);

        return new LaserAbility(
                new NamespacedKey(PLUGIN_INSTANCE, "laser"),
                Component.text("◎", TextColor.color(250, 74, 20)),
                Component.text("Laser", TextColor.color(250, 74, 20)),
                config,
                null
        );
    }

    private Ability createFlaming(){
        File file = new File(PLUGIN_INSTANCE.getDataFolder() + "/ability_configs/advancedmonsters/flaming_modifier_config.yml");

        List<String> description = new ArrayList<>();
        description.add("공격 시 %flaming_fire_effect_chance%% 확률로 %flaming_fire_effect_ticks%틱 동안 불에 붙는다.");
        FileConfiguration config = new ConfigBuilder(file)
                .addOption("flaming_fire_effect_chance", 100.0)
                .addOption("flaming_fire_effect_ticks", 200)
                .addOption("flaming_set_fire_on_explode", true)
                .addOption("command_description", description)
                .build();

        ConfigUtils.saveAndReloadConfig(config, file);

        return new FlamingAbility(
                new NamespacedKey(PLUGIN_INSTANCE, "flaming"),
                Component.text("\uD83D\uDD25", TextColor.color(0xFFAA00)),
                Component.text("Flaming", TextColor.color(0xFFAA00)),
                config,
                null
        );
    }

    private Ability createPunchy(){
        File file = new File(PLUGIN_INSTANCE.getDataFolder() + "/ability_configs/advancedmonsters/punchy_modifier_config.yml");

        List<String> description = new ArrayList<>();
        description.add("적을 %punchy_punch_air_chance%% 확률로 하늘로 날린다.");
        FileConfiguration config = new ConfigBuilder(file)
                .addOption("punchy_punch_air_chance", 40.0)
                .addOption("punchy_show_punch_air_message", true)
                .addOption("command_description", description)
                .build();

        ConfigUtils.saveAndReloadConfig(config, file);

        return new PunchyAbility(
                new NamespacedKey(PLUGIN_INSTANCE, "punchy"),
                Component.text("⇧", TextColor.color(0x55FF55)),
                Component.text("Punchy", TextColor.color(0x55FF55)),
                config,
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

    public static Ability getTank() {
        return tank;
    }

    public static Ability getTeleporter() {
        return teleporter;
    }

    public static Ability getBomber() {
        return bomber;
    }

    public static Ability getLaser() {
        return laser;
    }

    public static Ability getFlaming() {
        return flaming;
    }

    public static Ability getPunchy(){
        return punchy;
    }
}
