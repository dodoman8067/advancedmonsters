package kro.dodoworld.advancedmonsters.modifier.ability;

import kro.dodoworld.advancedmonsters.AdvancedMonsters;
import kro.dodoworld.advancedmonsters.core.builder.ConfigBuilder;
import kro.dodoworld.advancedmonsters.core.registry.Registry;
import kro.dodoworld.advancedmonsters.event.registry.RegistryInitializeEvent;
import kro.dodoworld.advancedmonsters.modifier.ability.custom.*;
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
    private static Ability frozen = null;
    private static Ability lightning = null;
    private static Ability stormy = null;
    private static Ability venomous = null;
    private static Ability healer = null;
    private static Ability aiming = null;
    private static Ability vampire = null;
    private static Ability feral = null;
    private static Ability splitter = null;
    private static Ability leaping = null;
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

        frozen = createFrozen();
        registry.register(frozen);

        lightning = createLightning();
        registry.register(lightning);

        stormy = createStormy();
        registry.register(stormy);

        venomous = createVenomous();
        registry.register(venomous);

        healer = createHealer();
        registry.register(healer);

        aiming = createAiming();
        registry.register(aiming);

        vampire = createVampire();
        registry.register(vampire);

        feral = createFeral();
        registry.register(feral);

        splitter = createSplitter();
        registry.register(splitter);

        leaping = createLeaping();
        registry.register(leaping);
    }

    private Ability createHealthy(){
        File healthyFile = new File(PLUGIN_INSTANCE.getDataFolder() + "/ability_configs/advancedmonsters/healthy.yml");

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
        File file = new File(PLUGIN_INSTANCE.getDataFolder() + "/ability_configs/advancedmonsters/strong.yml");

        List<String> strongDescription = new ArrayList<>();
        strongDescription.add("%strong_damage_multiply_chance%% 확률로 대미지가 %strong_damage_multiply_amount%배가 된다.");
        FileConfiguration strongConfig = new ConfigBuilder(file)
                .addOption("strong_damage_multiply_chance", 80.0)
                .addOption("strong_damage_multiply_amount", 1.75)
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
        File file = new File(PLUGIN_INSTANCE.getDataFolder() + "/ability_configs/advancedmonsters/speedy.yml");

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
        File file = new File(PLUGIN_INSTANCE.getDataFolder() + "/ability_configs/advancedmonsters/tank.yml");

        List<String> description = new ArrayList<>();
        description.add("%tank_ignore_damage_chance%% 확률로 대미지를 무시한다.");
        description.add("%tank_bonus_defence_amount%의 추가 방어력을 갖지만, 속도는 %tank_speed_multiply_amount%배가 된다.");
        FileConfiguration config = new ConfigBuilder(file)
                .addOption("tank_ignore_damage_chance", 25.0)
                .addOption("tank_send_damage_nullify_message", true)
                .addOption("tank_bonus_defence_amount", 25)
                .addOption("tank_speed_multiply_amount", 0.5)
                .addOption("tank_monster_damage_protect_range", 25.0)
                .addOption("tank_monster_damage_protect_chance", 80.0)
                .addOption("tank_monster_damage_protect_amount", 0.8)
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
        File file = new File(PLUGIN_INSTANCE.getDataFolder() + "/ability_configs/advancedmonsters/teleporter.yml");

        List<String> description = new ArrayList<>();
        description.add("적이 주변 %teleporter_teleport_range%블록 이내에 없다면 적의 위치로 텔레포트한다.");
        FileConfiguration config = new ConfigBuilder(file)
                .addOption("teleporter_teleport_range", 5.0)
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
        File file = new File(PLUGIN_INSTANCE.getDataFolder() + "/ability_configs/advancedmonsters/bomber.yml");

        List<String> description = new ArrayList<>();
        description.add("죽을 시 %bomber_tnt_drop_chance%% 확률로 TNT를 드롭한다.");
        FileConfiguration config = new ConfigBuilder(file)
                .addOption("bomber_tnt_drop_chance", 50.0)
                .addOption("bomber_projectile_explode_chance", 50.0)
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
        File file = new File(PLUGIN_INSTANCE.getDataFolder() + "/ability_configs/advancedmonsters/laser.yml");

        List<String> description = new ArrayList<>();
        description.add("적이 %laser_shoot_range% 블록 이내에 있다면,");
        description.add("%laser_damage% 대미지를 주는 레이저를 쏜다.");
        FileConfiguration config = new ConfigBuilder(file)
                .addOption("laser_shoot_range", 15.0)
                .addOption("laser_cooldown_ticks", 60)
                .addOption("laser_damage", 2.0)
                .addOption("command_description", description)
                .build();

        ConfigUtils.saveAndReloadConfig(config, file);

        return new LaserAbility(
                new NamespacedKey(PLUGIN_INSTANCE, "laser"),
                Component.text("◎", TextColor.color(250, 74, 20)),
                Component.text("Laser", TextColor.color(250, 74, 20)),
                config,
                null,
                10
        );
    }

    private Ability createFlaming(){
        File file = new File(PLUGIN_INSTANCE.getDataFolder() + "/ability_configs/advancedmonsters/flaming.yml");

        List<String> description = new ArrayList<>();
        description.add("공격 시 %flaming_fire_effect_chance%% 확률로 %flaming_fire_effect_ticks%틱 동안 불에 붙는다.");
        FileConfiguration config = new ConfigBuilder(file)
                .addOption("flaming_fire_effect_chance", 70.0)
                .addOption("flaming_fire_effect_ticks", 100)
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
        File file = new File(PLUGIN_INSTANCE.getDataFolder() + "/ability_configs/advancedmonsters/punchy.yml");

        List<String> description = new ArrayList<>();
        description.add("적을 %punchy_punch_air_chance%% 확률로 하늘로 날린다.");
        FileConfiguration config = new ConfigBuilder(file)
                .addOption("punchy_punch_air_chance", 50.0)
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

    private Ability createFrozen(){
        File file = new File(PLUGIN_INSTANCE.getDataFolder() + "/ability_configs/advancedmonsters/frozen.yml");

        List<String> description = new ArrayList<>();
        description.add("공격 시 %frozen_freeze_effect_chance%% 확률로 %frozen_freeze_effect_ticks%틱 동안 몸이 얼어버린다.");
        FileConfiguration config = new ConfigBuilder(file)
                .addOption("frozen_freeze_effect_chance", 70.0)
                .addOption("frozen_freeze_effect_ticks", 100)
                .addOption("frozen_can_spawn_on_nether", false)
                .addOption("command_description", description)
                .build();

        ConfigUtils.saveAndReloadConfig(config, file);

        return new FrozenAbility(
                new NamespacedKey(PLUGIN_INSTANCE, "frozen"),
                Component.text("❄", TextColor.color(165, 197, 217)),
                Component.text("Frozen", TextColor.color(165, 197, 217)),
                config,
                null
        );
    }

    private Ability createLightning(){
        File file = new File(PLUGIN_INSTANCE.getDataFolder() + "/ability_configs/advancedmonsters/lightning.yml");

        List<String> description = new ArrayList<>();
        description.add("공격 시 %lighting_strike_chance%% 확률로 최대 %lighting_max_lighting_strike_amount%번 만큼");
        description.add("%lighting_damage_amount%대미지를 주는 번개를 생성한다.");
        FileConfiguration config = new ConfigBuilder(file)
                .addOption("lighting_strike_chance", 60.0)
                .addOption("lighting_max_lighting_strike_amount", 3)
                .addOption("lighting_damage_amount", 3.0)
                .addOption("command_description", description)
                .build();

        ConfigUtils.saveAndReloadConfig(config, file);

        return new LightningAbility(
                new NamespacedKey(PLUGIN_INSTANCE, "lightning"),
                Component.text("⚡", TextColor.color(251, 242, 198)),
                Component.text("Lightning", TextColor.color(251, 242, 198)),
                config,
                null
        );
    }

    private Ability createStormy(){
        File file = new File(PLUGIN_INSTANCE.getDataFolder() + "/ability_configs/advancedmonsters/stormy.yml");

        List<String> description = new ArrayList<>();
        description.add("적이 %stormy_lighting_range%블록 이내에 있다면,");
        description.add("%stormy_lighting_cooldown%틱 마다 번개 소환 + %stormy_lighting_damage%만큼의 대미지를 준다.");
        FileConfiguration config = new ConfigBuilder(file)
                .addOption("stormy_lighting_range", 25.0)
                .addOption("stormy_lighting_damage", 5.0)
                .addOption("stormy_show_lighting_damage_message", true)
                .addOption("stormy_slow_effect_ticks", 30)
                .addOption("stormy_slow_effect_amplifier", 3)
                .addOption("stormy_lighting_cooldown", 60)
                .addOption("stormy_only_spawn_when_storming", false)
                .addOption("command_description", description)
                .build();

        ConfigUtils.saveAndReloadConfig(config, file);

        return new StormyAbility(
                new NamespacedKey(PLUGIN_INSTANCE, "stormy"),
                Component.text("\uD83C\uDF27", TextColor.color(22, 184, 162)),
                Component.text("Stormy", TextColor.color(22, 184, 162)),
                config,
                null
        );
    }

    private Ability createVenomous(){
        File file = new File(PLUGIN_INSTANCE.getDataFolder() + "/ability_configs/advancedmonsters/venomous.yml");

        List<String> description = new ArrayList<>();
        description.add("공격 시 %venomous_apply_effect_chance%% 확률로 독에 걸린다.");
        FileConfiguration config = new ConfigBuilder(file)
                .addOption("venomous_apply_effect_chance", 100.0)
                .addOption("venomous_poison_effect_ticks", 200)
                .addOption("venomous_poison_effect_amplifier", 3)
                .addOption("venomous_weakness_effect_ticks", 240)
                .addOption("venomous_weakness_effect_amplifier", 2)
                .addOption("command_description", description)
                .build();

        ConfigUtils.saveAndReloadConfig(config, file);

        return new VenomousAbility(
                new NamespacedKey(PLUGIN_INSTANCE, "venomous"),
                Component.text("☣", TextColor.color(199, 204, 53)),
                Component.text("Venomous", TextColor.color(199, 204, 53)),
                config,
                null
        );
    }

    private Ability createHealer(){
        File file = new File(PLUGIN_INSTANCE.getDataFolder() + "/ability_configs/advancedmonsters/healer.yml");

        List<String> description = new ArrayList<>();
        description.add("%healer_circle_try_per_ticks%틱마다 %healer_circle_healing_amount% 체력을 회복하는 동그라미를 소환을 시도한다.");
        FileConfiguration config = new ConfigBuilder(file)
                .addOption("healer_circle_healing_amount", 8.0)
                .addOption("healer_circle_try_per_ticks", 100)
                .addOption("healer_circle_cooldown_ticks", 10000L)
                .addOption("command_description", description)
                .build();

        ConfigUtils.saveAndReloadConfig(config, file);

        return new HealerAbility(
                new NamespacedKey(PLUGIN_INSTANCE, "healer"),
                Component.text("✙", TextColor.color(30, 156, 38)),
                Component.text("Healer", TextColor.color(30, 156, 38)),
                config,
                null,
                20
        );
    }

    private Ability createAiming(){
        File file = new File(PLUGIN_INSTANCE.getDataFolder() + "/ability_configs/advancedmonsters/aiming.yml");

        List<String> description = new ArrayList<>();
        description.add("%aiming_arrow_homing_chance%% 확률로 유도 화살을 발사한다.");
        FileConfiguration config = new ConfigBuilder(file)
                .addOption("aiming_arrow_homing_chance", 80.0)
                .addOption("command_description", description)
                .build();

        ConfigUtils.saveAndReloadConfig(config, file);

        return new AimingAbility(
                new NamespacedKey(PLUGIN_INSTANCE, "aiming"),
                Component.text("\uD83C\uDFF9", TextColor.color(120, 67, 7)),
                Component.text("Aiming", TextColor.color(120, 67, 7)),
                config,
                null,
                40
        );
    }

    private Ability createVampire(){
        File file = new File(PLUGIN_INSTANCE.getDataFolder() + "/ability_configs/advancedmonsters/vampire.yml");

        List<String> description = new ArrayList<>();
        description.add("체력이 부족할 시 주변 몹의 체력을 빨아들이며 2배의 재생 속도를 가지지만 불빛에서 해당 능력들을 상실하며 밝기 레벨만큼 대미지를 입는다.");
        FileConfiguration config = new ConfigBuilder(file)
                .addOption("command_description", description)
                .build();

        ConfigUtils.saveAndReloadConfig(config, file);

        return new VampireAbility(
                new NamespacedKey(PLUGIN_INSTANCE, "vampire"),
                Component.text("\uD83E\uDD87", TextColor.color(108, 0, 0)),
                Component.text("Vampire", TextColor.color(108, 0, 0)),
                config,
                null,
                20
        );
    }

    private Ability createFeral(){
        File file = new File(PLUGIN_INSTANCE.getDataFolder() + "/ability_configs/advancedmonsters/feral.yml");

        List<String> description = new ArrayList<>();
        description.add("공격 시 최대 추가 %feral_max_attack_count%회 공격을 넣는다.");
        FileConfiguration config = new ConfigBuilder(file)
                .addOption("feral_max_attack_count", 3)
                .addOption("feral_speed_multiply_amount", 1.4)
                .addOption("command_description", description)
                .build();

        ConfigUtils.saveAndReloadConfig(config, file);

        return new FeralAbility(
                new NamespacedKey(PLUGIN_INSTANCE, "feral"),
                Component.text("⫽", TextColor.color(163, 11, 11)),
                Component.text("Feral", TextColor.color(163, 11, 11)),
                config,
                null,
                20
        );
    }

    private Ability createSplitter(){
        File file = new File(PLUGIN_INSTANCE.getDataFolder() + "/ability_configs/advancedmonsters/splitter.yml");

        List<String> description = new ArrayList<>();
        description.add("죽을 시 원래 크기의 절반만큼의 두 엔티티를 스폰한다.");
        FileConfiguration config = new ConfigBuilder(file)
                .addOption("splitter_minimum_size", 0.4)
                .addOption("command_description", description)
                .build();

        ConfigUtils.saveAndReloadConfig(config, file);

        return new SplitterAbility(
                new NamespacedKey(PLUGIN_INSTANCE, "splitter"),
                Component.text("➗", TextColor.color(242, 207, 7)),
                Component.text("Splitter", TextColor.color(242, 207, 7)),
                config,
                null,
                20
        );
    }

    private Ability createLeaping(){
        File file = new File(PLUGIN_INSTANCE.getDataFolder() + "/ability_configs/advancedmonsters/leaping.yml");

        List<String> description = new ArrayList<>();
        description.add("매 %leaping_cooldown_ticks%틱마다 대상이 %leaping_leap_range% 이상 떨어져 있을시 해당 대상으로 점프한다.");
        FileConfiguration config = new ConfigBuilder(file)
                .addOption("leaping_cooldown_ticks", 40)
                .addOption("leaping_leap_range", 6)
                .addOption("command_description", description)
                .build();

        ConfigUtils.saveAndReloadConfig(config, file);

        return new LeapingAbility(
                new NamespacedKey(PLUGIN_INSTANCE, "leaping"),
                Component.text("⤴", TextColor.color(199, 199, 46)),
                Component.text("Leaping", TextColor.color(199, 199, 46)),
                config,
                null,
                20
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

    public static Ability getFrozen() {
        return frozen;
    }

    public static Ability getLightning() {
        return lightning;
    }

    public static Ability getStormy() {
        return stormy;
    }

    public static Ability getVenomous() {
        return venomous;
    }

    public static Ability getHealer() {
        return healer;
    }

    public static Ability getAiming() {
        return aiming;
    }

    public static Ability getVampire() {
        return vampire;
    }

    public static Ability getFeral() {
        return feral;
    }

    public static Ability getSplitter() {
        return splitter;
    }

    public static Ability getLeaping() {
        return leaping;
    }
}
