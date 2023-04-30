package kro.dodoworld.advancedmonsters.util;

import kro.dodoworld.advancedmonsters.config.data.RevealedAbilities;
import kro.dodoworld.advancedmonsters.config.data.UnlockedEntityAbilities;
import kro.dodoworld.advancedmonsters.config.modifier.*;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

import java.util.HashSet;
import java.util.Set;

public class AdvancedMonstersUtilMethods {


    public static Set<MonsterAbility> getAbilities(LivingEntity entity){
        Set<MonsterAbility> returnValue;

        returnValue = new HashSet<>();
        for (String tag : entity.getScoreboardTags()) {
            switch (tag) {
                case "adm_modifier_healthy" -> returnValue.add(MonsterAbility.HEALTHY);
                case "adm_modifier_strong" -> returnValue.add(MonsterAbility.STRONG);
                case "adm_modifier_tank" -> returnValue.add(MonsterAbility.TANK);
                case "adm_modifier_speedy" -> returnValue.add(MonsterAbility.SPEEDY);
                case "adm_modifier_teleporter" -> returnValue.add(MonsterAbility.TELEPORTER);
                case "adm_modifier_invisible" -> returnValue.add(MonsterAbility.INVISIBLE);
                case "adm_modifier_punchy" -> returnValue.add(MonsterAbility.PUNCHY);
                case "adm_modifier_boomer" -> returnValue.add(MonsterAbility.BOOMER);
                case "adm_modifier_flaming" -> returnValue.add(MonsterAbility.FLAMING);
                case "adm_modifier_laser" -> returnValue.add(MonsterAbility.LASER);
                case "adm_modifier_venomous" -> returnValue.add(MonsterAbility.VENOMOUS);
                case "adm_modifier_stormy" -> returnValue.add(MonsterAbility.STORMY);
                case "adm_modifier_frozen" -> returnValue.add(MonsterAbility.FROZEN);
                case "adm_modifier_lighting" -> returnValue.add(MonsterAbility.LIGHTING);
                case "adm_modifier_revenant" -> returnValue.add(MonsterAbility.REVENANT);
            }
        }
        return returnValue;
    }

    public static String getNMSVersion(){
        String v = Bukkit.getServer().getClass().getPackage().getName();
        return v.substring(v.lastIndexOf('.') + 1);
    }

    public static String getAbilitySymbol(MonsterAbility monsterAbility){
        String returnValue = null;
            switch (monsterAbility) {
                case HEALTHY -> returnValue = "❤";
                case STRONG -> returnValue = "🗡";
                case TANK -> returnValue = "❇";
                case SPEEDY -> returnValue = "✴";
                case TELEPORTER -> returnValue = "☯";
                case INVISIBLE -> returnValue = "▫";
                case PUNCHY -> returnValue = "⇧";
                case BOOMER -> returnValue = "■";
                case FLAMING -> returnValue = "\uD83D\uDD25";
                case LASER -> returnValue = "◎";
                case VENOMOUS -> returnValue = "☣";
                case STORMY -> returnValue = "\uD83C\uDF27";
                case FROZEN -> returnValue = "❄";
                case LIGHTING -> returnValue = "⚡";
                case REVENANT -> returnValue = "☠";
            }
        return returnValue;
    }


    public static Component getAbilitySymbolWithColor(MonsterAbility monsterAbility){
        Component returnValue = null;
        switch (monsterAbility) {
            case HEALTHY -> returnValue = Component.text("❤").color(TextColor.color(0xFF5555)).asComponent();
            case STRONG -> returnValue = Component.text("🗡").color(TextColor.color(0xAA0000)).asComponent();
            case TANK -> returnValue = Component.text("❇").color(TextColor.color(0x555555)).asComponent();
            case SPEEDY -> returnValue = Component.text("✴").color(TextColor.color(0xFFFFFF)).asComponent();
            case TELEPORTER -> returnValue = Component.text("☯").color(TextColor.color(0x00AAAA)).asComponent();
            case INVISIBLE -> returnValue = Component.text("▫").color(TextColor.color(0x555555)).asComponent();
            case PUNCHY -> returnValue = Component.text("⇧").color(TextColor.color(0x55FF55)).asComponent();
            case BOOMER -> returnValue = Component.text("■").color(TextColor.color(0xFF5555)).asComponent();
            case FLAMING -> returnValue = Component.text("\uD83D\uDD25").color(TextColor.color(0xFFAA00)).asComponent();
            case LASER -> returnValue = Component.text("◎").color(TextColor.color(250, 74, 20)).asComponent();
            case VENOMOUS -> returnValue = Component.text("☣").color(TextColor.color(199, 204, 53)).asComponent();
            case STORMY -> returnValue = Component.text("\uD83C\uDF27").color(TextColor.color(22, 184, 162)).asComponent();
            case FROZEN -> returnValue = Component.text("❄").color(TextColor.color(165, 197, 217)).asComponent();
            case LIGHTING -> returnValue = Component.text("⚡").color(TextColor.color(251, 242, 198)).asComponent();
            case REVENANT -> returnValue = Component.text("☠").color(TextColor.color(86, 32, 125)).asComponent();
        }
        return returnValue;
    }

    public static boolean isMiniboss(Entity entity){
        for(String s : entity.getScoreboardTags()){
            if(s.startsWith("adm_miniboss")){
                return true;
            }
        }
        return false;
    }

    public static boolean isUnlocked(MonsterAbility monsterAbility){
        FileConfiguration configuration = UnlockedEntityAbilities.getUnlockedEntityAbilityConfig();
        return configuration.getBoolean(monsterAbility.toString().toLowerCase());
    }

    public static void setUnlocked(MonsterAbility monsterAbility, boolean value){
        FileConfiguration configuration = UnlockedEntityAbilities.getUnlockedEntityAbilityConfig();
        configuration.set(monsterAbility.toString().toLowerCase(), value);
        if(value) setRevealed(monsterAbility, true);
        UnlockedEntityAbilities.saveConfig();
        UnlockedEntityAbilities.reloadConfig();
    }

    public static boolean isRevealed(MonsterAbility monsterAbility){
        FileConfiguration configuration = RevealedAbilities.getRevealedAbilityConfig();
        return configuration.getBoolean(monsterAbility.toString().toLowerCase());
    }

    public static void setRevealed(MonsterAbility monsterAbility, boolean value){
        FileConfiguration configuration = RevealedAbilities.getRevealedAbilityConfig();
        configuration.set(monsterAbility.toString().toLowerCase(), value);
        RevealedAbilities.saveConfig();
        RevealedAbilities.reloadConfig();
    }

    public static String replace(String value){
        String returnValue;
        returnValue = value.replaceAll("\\{healthMultiplyAmount}", String.valueOf(HealthyModifierConfig.getHealthyModifierConfig().getDouble("health_multiply_amount")))
                .replaceAll("\\{damageMultiplyChance}", String.valueOf(StrongModifierConfig.getStrongModifierConfig().getDouble("damage_multiply_chance")))
                .replaceAll("\\{damageMultiplyAmount}", String.valueOf(StrongModifierConfig.getStrongModifierConfig().getDouble("damage_multiply_amount")))
                .replaceAll("\\{ignoreDamageChance}", String.valueOf(TankModifierConfig.getTankModifierConfig().getDouble("ignore_damage_chance")))
                .replaceAll("\\{bounsDefenceAmount}", String.valueOf(TankModifierConfig.getTankModifierConfig().getInt("bouns_defence_amount")))
                .replaceAll("\\{tankSpeedMultiplyAmount}", String.valueOf(TankModifierConfig.getTankModifierConfig().getDouble("speed_multiply_amount")))
                .replaceAll("\\{speedMultiplyAmount}", String.valueOf(SpeedyModifierConfig.getSpeedyModifierConfig().getDouble("speed_multiply_amount")))
                .replaceAll("\\{speedyHealthMultiplyAmount}", String.valueOf(SpeedyModifierConfig.getSpeedyModifierConfig().getDouble("health_multiply_amount")))
                .replaceAll("\\{teleportRange}", String.valueOf(TeleportModifierConfig.getTeleporterModifierConfig().getDouble("teleport_range")))
                .replaceAll("\\{punchAirChance}", String.valueOf(PunchyModifierConfig.getPunchyModifierConfig().getDouble("punch_air_chance")))
                .replaceAll("\\{tntDropChance}", String.valueOf(BoomerModifierConfig.getBoomerModifierConfig().getDouble("tnt_drop_chance")))
                .replaceAll("\\{fireEffectChance}", String.valueOf(FlamingModifierConfig.getFlamingModifierConfig().getDouble("fire_effect_chance")))
                .replaceAll("\\{fireEffectTicks}", String.valueOf(FlamingModifierConfig.getFlamingModifierConfig().getDouble("fire_effect_ticks")))
                .replaceAll("\\{laserShootRange}", String.valueOf(LaserModifierConfig.getLaserModifierConfig().getDouble("laser_shoot_range")))
                .replaceAll("\\{laserDamage}", String.valueOf(LaserModifierConfig.getLaserModifierConfig().getDouble("laser_damage")))
                .replaceAll("\\{applyEffectChance}", String.valueOf(VenomousModifierConfig.getVenomousModifierConfig().getDouble("apply_effect_chance")))
                .replaceAll("\\{stormyLightingRange}", String.valueOf(StormyModifierConfig.getStormyModifierConfig().getDouble("stormy_lighting_range")))
                .replaceAll("\\{stormyLightingCooldown}", String.valueOf(StormyModifierConfig.getStormyModifierConfig().getInt("stormy_lighting_cooldown")))
                .replaceAll("\\{stormyLightingDamage}", String.valueOf(StormyModifierConfig.getStormyModifierConfig().getDouble("stormy_lighting_damage")))
                .replaceAll("\\{freezeEffectChance}", String.valueOf(FrozenModifierConfig.getFrozenModifierConfig().getDouble("freeze_effect_chance")))
                .replaceAll("\\{freezeEffectTicks}", String.valueOf(FrozenModifierConfig.getFrozenModifierConfig().getInt("freeze_effect_ticks")))
                .replaceAll("\\{lightingStrikeChance}", String.valueOf(LightingModifierConfig.getLightingModifierConfig().getDouble("lighting_strike_chance")))
                .replaceAll("\\{maxLightingStrikeAmount}", String.valueOf(LightingModifierConfig.getLightingModifierConfig().getInt("max_lighting_strike_amount")))
                .replaceAll("\\{lightingDamageAmount}", String.valueOf(LightingModifierConfig.getLightingModifierConfig().getDouble("lighting_damage_amount")))
                .replaceAll("\\{monsterReviveRange}", String.valueOf(RevenantModifierConfig.getRevenantModifierConfig().getDouble("monster_revive_range")))
                .replaceAll("\\{monsterReviveChance}", String.valueOf(RevenantModifierConfig.getRevenantModifierConfig().getDouble("monster_revive_chance")));

        return returnValue;
    }



    public static boolean hasAbility(LivingEntity entity, MonsterAbility ability){
        boolean value = false;
        for(MonsterAbility ability1 : getAbilities(entity)){
            if(ability1.equals(ability)){
                value = true;
            }
        }
        return value;
    }


    public static FileConfiguration getAbilityConfig(MonsterAbility ability){
        FileConfiguration returnValue;
        switch (ability){
            case HEALTHY -> returnValue = HealthyModifierConfig.getHealthyModifierConfig();
            case STRONG -> returnValue = StrongModifierConfig.getStrongModifierConfig();
            case TANK -> returnValue = TankModifierConfig.getTankModifierConfig();
            case SPEEDY -> returnValue = SpeedyModifierConfig.getSpeedyModifierConfig();
            case TELEPORTER -> returnValue = TeleportModifierConfig.getTeleporterModifierConfig();
            case INVISIBLE -> returnValue = InvisibleModifierConfig.getInvisibleModifierConfig();
            case PUNCHY -> returnValue = PunchyModifierConfig.getPunchyModifierConfig();
            case BOOMER -> returnValue = BoomerModifierConfig.getBoomerModifierConfig();
            case FLAMING -> returnValue = FlamingModifierConfig.getFlamingModifierConfig();
            case LASER -> returnValue = LaserModifierConfig.getLaserModifierConfig();
            case VENOMOUS -> returnValue = VenomousModifierConfig.getVenomousModifierConfig();
            case STORMY -> returnValue = StormyModifierConfig.getStormyModifierConfig();
            case FROZEN -> returnValue = FrozenModifierConfig.getFrozenModifierConfig();
            case LIGHTING -> returnValue = LightingModifierConfig.getLightingModifierConfig();
            case REVENANT -> returnValue = RevenantModifierConfig.getRevenantModifierConfig();
            default -> throw new IllegalArgumentException("Unknown MonsterAbility enum value");
        }
        return returnValue;
    }
}
