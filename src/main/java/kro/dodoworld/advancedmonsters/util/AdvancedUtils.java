package kro.dodoworld.advancedmonsters.util;

import kro.dodoworld.advancedmonsters.config.data.RevealedAbilities;
import kro.dodoworld.advancedmonsters.config.data.UnlockedEntityAbilities;
import kro.dodoworld.advancedmonsters.config.modifier.*;
import kro.dodoworld.advancedmonsters.modifier.ability.MonsterAbility;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

import java.util.HashSet;
import java.util.Set;

/**
 * Utility methods related to ability system.
 */
public class AdvancedUtils {

    /**
     * Returns entity's set of ability.
     * @param entity entity to check abilities
     * @return set of MonsterAbility enum, null when entity doesn't have abilities
     */
    public static Set<MonsterAbility> getAbilities(LivingEntity entity){
        Set<MonsterAbility> returnValue = new HashSet<>();
        //Iterates through entity's scoreboard tags
        for (String tag : entity.getScoreboardTags()) {
            //Adds MonsterAbility enum from string
            switch (tag) {
                case "adm_modifier_healthy" -> returnValue.add(MonsterAbility.HEALTHY);
                case "adm_modifier_strong" -> returnValue.add(MonsterAbility.STRONG);
                case "adm_modifier_tank" -> returnValue.add(MonsterAbility.TANK);
                case "adm_modifier_speedy" -> returnValue.add(MonsterAbility.SPEEDY);
                case "adm_modifier_teleporter" -> returnValue.add(MonsterAbility.TELEPORTER);
                case "adm_modifier_invisible" -> returnValue.add(MonsterAbility.INVISIBLE);
                case "adm_modifier_punchy" -> returnValue.add(MonsterAbility.PUNCHY);
                case "adm_modifier_bomber" -> returnValue.add(MonsterAbility.BOMBER);
                case "adm_modifier_flaming" -> returnValue.add(MonsterAbility.FLAMING);
                case "adm_modifier_laser" -> returnValue.add(MonsterAbility.LASER);
                case "adm_modifier_venomous" -> returnValue.add(MonsterAbility.VENOMOUS);
                case "adm_modifier_stormy" -> returnValue.add(MonsterAbility.STORMY);
                case "adm_modifier_frozen" -> returnValue.add(MonsterAbility.FROZEN);
                case "adm_modifier_lighting" -> returnValue.add(MonsterAbility.LIGHTING);
                case "adm_modifier_revitalize" -> returnValue.add(MonsterAbility.REVITALIZE);
            }
        }
        if(!returnValue.isEmpty()){
            return returnValue;
        }else{
            return null;
        }
    }

    /**
     * Returns NMS version this server is running.
     * @return NMS version
     */
    public static String getNMSVersion(){
        String v = Bukkit.getServer().getClass().getPackage().getName();
        return v.substring(v.lastIndexOf('.') + 1);
    }

    /**
     * Returns ability symbol.
     * @param monsterAbility ability to return its symbol
     * @return string of ability symbol
     */
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
                case BOMBER -> returnValue = "■";
                case FLAMING -> returnValue = "\uD83D\uDD25";
                case LASER -> returnValue = "◎";
                case VENOMOUS -> returnValue = "☣";
                case STORMY -> returnValue = "\uD83C\uDF27";
                case FROZEN -> returnValue = "❄";
                case LIGHTING -> returnValue = "⚡";
                case REVITALIZE -> returnValue = "✙";
            }
        return returnValue;
    }

    /**
     * Returns ability symbol with color.
     * @param monsterAbility ability to return its symbol
     * @return component of ability symbol with color
     */
    public static Component getAbilitySymbolWithColor(MonsterAbility monsterAbility){
        Component returnValue = null;
        switch (monsterAbility) {
            case HEALTHY -> returnValue = Component.text("❤", TextColor.color(0xFF5555));
            case STRONG -> returnValue = Component.text("🗡", TextColor.color(0xAA0000));
            case TANK -> returnValue = Component.text("❇", TextColor.color(0x555555));
            case SPEEDY -> returnValue = Component.text("✴", TextColor.color(0xFFFFFF));
            case TELEPORTER -> returnValue = Component.text("☯", TextColor.color(0x00AAAA));
            case INVISIBLE -> returnValue = Component.text("▫", TextColor.color(0x555555));
            case PUNCHY -> returnValue = Component.text("⇧", TextColor.color(0x55FF55));
            case BOMBER -> returnValue = Component.text("■", TextColor.color(0xFF5555));
            case FLAMING -> returnValue = Component.text("\uD83D\uDD25", TextColor.color(0xFFAA00));
            case LASER -> returnValue = Component.text("◎", TextColor.color(250, 74, 20));
            case VENOMOUS -> returnValue = Component.text("☣", TextColor.color(199, 204, 53));
            case STORMY -> returnValue = Component.text("\uD83C\uDF27", TextColor.color(22, 184, 162));
            case FROZEN -> returnValue = Component.text("❄", TextColor.color(165, 197, 217));
            case LIGHTING -> returnValue = Component.text("⚡", TextColor.color(251, 242, 198));
            case REVITALIZE -> returnValue = Component.text("✙", TextColor.color(25, 189, 63));
        }
        return returnValue;
    }

    /**
     * Checks entity is miniboss.
     * @param entity entity to check
     * @return true if entity is miniboss, otherwise false
     */
    public static boolean isMiniboss(Entity entity){
        //Iterates through entity's scoreboard tags
        for(String s : entity.getScoreboardTags()){
            //Returns true when entity has scoreboard tag that starts with "adm_miniboss"
            if(s.startsWith("adm_miniboss")){
                return true;
            }
        }
        return false;
    }

    /**
     * Checks ability is unlocked.
     * @param monsterAbility ability to check
     * @return true when unlocked, otherwise false
     */
    public static boolean isUnlocked(MonsterAbility monsterAbility){
        FileConfiguration configuration = UnlockedEntityAbilities.getUnlockedEntityAbilityConfig();
        return configuration.getBoolean(monsterAbility.toString().toLowerCase());
    }

    /**
     * Changes abilities unlock state.
     * @param monsterAbility ability to change
     * @param value new state of ability's unlock state
     */
    public static void setUnlocked(MonsterAbility monsterAbility, boolean value){
        FileConfiguration configuration = UnlockedEntityAbilities.getUnlockedEntityAbilityConfig();
        //Sets the value
        configuration.set(monsterAbility.toString().toLowerCase(), value);
        //Reveals ability when value is true
        if(value) setRevealed(monsterAbility, true);
        //Saves config
        UnlockedEntityAbilities.saveConfig();
        UnlockedEntityAbilities.reloadConfig();
    }

    /**
     * Checks ability is revealed.
     * @param monsterAbility ability to check
     * @return true when revealed, otherwise false
     */
    public static boolean isRevealed(MonsterAbility monsterAbility){
        FileConfiguration configuration = RevealedAbilities.getRevealedAbilityConfig();
        return configuration.getBoolean(monsterAbility.toString().toLowerCase());
    }

    /**
     * Changes abilities reveal state.
     * @param monsterAbility ability to change
     * @param value new state of ability's reveal state
     */
    public static void setRevealed(MonsterAbility monsterAbility, boolean value){
        FileConfiguration configuration = RevealedAbilities.getRevealedAbilityConfig();
        //Sets the value
        configuration.set(monsterAbility.toString().toLowerCase(), value);
        //Saves config
        RevealedAbilities.saveConfig();
        RevealedAbilities.reloadConfig();
    }

    public static String replace(String value){
        String returnValue;
        returnValue = value.replaceAll("\\{bomber_tnt_drop_chance}", String.valueOf(BomberModifierConfig.getBomberModifierConfig().getDouble("bomber_tnt_drop_chance")))
                .replaceAll("\\{bomber_tnt_fuse_ticks}", String.valueOf(BomberModifierConfig.getBomberModifierConfig().getInt("bomber_tnt_fuse_ticks")))

                .replaceAll("\\{flaming_fire_effect_chance}", String.valueOf(FlamingModifierConfig.getFlamingModifierConfig().getDouble("flaming_fire_effect_chance")))
                .replaceAll("\\{flaming_fire_effect_ticks}", String.valueOf(FlamingModifierConfig.getFlamingModifierConfig().getInt("flaming_fire_effect_ticks")))
                .replaceAll("\\{flaming_set_fire_on_explode}", String.valueOf(FlamingModifierConfig.getFlamingModifierConfig().getBoolean("flaming_set_fire_on_explode")))

                .replaceAll("\\{frozen_freeze_effect_chance}", String.valueOf(FrozenModifierConfig.getFrozenModifierConfig().getDouble("frozen_freeze_effect_chance")))
                .replaceAll("\\{frozen_freeze_effect_ticks}", String.valueOf(FrozenModifierConfig.getFrozenModifierConfig().getInt("frozen_freeze_effect_ticks")))

                .replaceAll("\\{healthy_health_multiply_amount}", String.valueOf(HealthyModifierConfig.getHealthyModifierConfig().getDouble("healthy_health_multiply_amount")))

                .replaceAll("\\{laser_shoot_range}", String.valueOf(LaserModifierConfig.getLaserModifierConfig().getDouble("laser_shoot_range")))
                .replaceAll("\\{laser_damage}", String.valueOf(LaserModifierConfig.getLaserModifierConfig().getDouble("laser_damage")))

                .replaceAll("\\{lighting_strike_chance}", String.valueOf(LightingModifierConfig.getLightingModifierConfig().getDouble("lighting_strike_chance")))
                .replaceAll("\\{lighting_max_lighting_strike_amount}", String.valueOf(LightingModifierConfig.getLightingModifierConfig().getInt("lighting_max_lighting_strike_amount")))
                .replaceAll("\\{lighting_damage_amount}", String.valueOf(LightingModifierConfig.getLightingModifierConfig().getDouble("lighting_damage_amount")))

                .replaceAll("\\{punchy_punch_air_chance}", String.valueOf(PunchyModifierConfig.getPunchyModifierConfig().getDouble("punchy_punch_air_chance")))
                .replaceAll("\\{punchy_show_punch_air_message}", String.valueOf(PunchyModifierConfig.getPunchyModifierConfig().getBoolean("punchy_show_punch_air_message")))

                .replaceAll("\\{stormy_lighting_range}", String.valueOf(StormyModifierConfig.getStormyModifierConfig().getDouble("stormy_lighting_range")))
                .replaceAll("\\{stormy_lighting_damage}", String.valueOf(StormyModifierConfig.getStormyModifierConfig().getDouble("stormy_lighting_damage")))
                .replaceAll("\\{stormy_show_lighting_damage_message}", String.valueOf(StormyModifierConfig.getStormyModifierConfig().getBoolean("stormy_show_lighting_damage_message")))
                .replaceAll("\\{stormy_slow_effect_ticks}", String.valueOf(StormyModifierConfig.getStormyModifierConfig().getInt("stormy_slow_effect_ticks")))
                .replaceAll("\\{stormy_slow_effect_amplifier}", String.valueOf(StormyModifierConfig.getStormyModifierConfig().getInt("stormy_slow_effect_amplifier")))
                .replaceAll("\\{stormy_lighting_cooldown}", String.valueOf(StormyModifierConfig.getStormyModifierConfig().getInt("stormy_lighting_cooldown")))
                .replaceAll("\\{stormy_only_spawn_when_storming}", String.valueOf(StormyModifierConfig.getStormyModifierConfig().getBoolean("stormy_only_spawn_when_storming")))

                .replaceAll("\\{strong_damage_multiply_chance}", String.valueOf(StrongModifierConfig.getStrongModifierConfig().getDouble("strong_damage_multiply_chance")))
                .replaceAll("\\{strong_damage_multiply_amount}", String.valueOf(StrongModifierConfig.getStrongModifierConfig().getDouble("strong_damage_multiply_amount")))

                .replaceAll("\\{tank_ignore_damage_chance}", String.valueOf(TankModifierConfig.getTankModifierConfig().getDouble("tank_ignore_damage_chance")))
                .replaceAll("\\{tank_bonus_defence_amount}", String.valueOf(TankModifierConfig.getTankModifierConfig().getInt("tank_bonus_defence_amount")))
                .replaceAll("\\{tank_send_damage_nullify_message}", String.valueOf(TankModifierConfig.getTankModifierConfig().getBoolean("tank_send_damage_nullify_message")))
                .replaceAll("\\{tank_ignore_damage_chance}", String.valueOf(TankModifierConfig.getTankModifierConfig().getDouble("tank_bouns_defence_amount")))
                .replaceAll("\\{tank_speed_multiply_amount}", String.valueOf(TankModifierConfig.getTankModifierConfig().getDouble("tank_speed_multiply_amount")))

                .replaceAll("\\{speedy_health_multiply_amount}", String.valueOf(SpeedyModifierConfig.getSpeedyModifierConfig().getDouble("speedy_health_multiply_amount")))
                .replaceAll("\\{speedy_speed_multiply_amount}", String.valueOf(SpeedyModifierConfig.getSpeedyModifierConfig().getDouble("speedy_speed_multiply_amount")))

                .replaceAll("\\{teleporter_teleport_range}", String.valueOf(TeleportModifierConfig.getTeleporterModifierConfig().getDouble("teleporter_teleport_range")))

                .replaceAll("\\{venomous_apply_effect_chance}", String.valueOf(VenomousModifierConfig.getVenomousModifierConfig().getDouble("venomous_apply_effect_chance")))
                .replaceAll("\\{venomous_poison_effect_ticks}", String.valueOf(VenomousModifierConfig.getVenomousModifierConfig().getInt("venomous_poison_effect_ticks")))
                .replaceAll("\\{venomous_poison_effect_amplifier}", String.valueOf(VenomousModifierConfig.getVenomousModifierConfig().getInt("venomous_poison_effect_amplifier")))
                .replaceAll("\\{venomous_weakness_effect_ticks}", String.valueOf(VenomousModifierConfig.getVenomousModifierConfig().getInt("venomous_weakness_effect_ticks")))
                .replaceAll("\\{venomous_weakness_effect_amplifier}", String.valueOf(VenomousModifierConfig.getVenomousModifierConfig().getInt("venomous_weakness_effect_amplifier")))
                .replaceAll("\\{venomous_attack_damage_multiply_amount}", String.valueOf(VenomousModifierConfig.getVenomousModifierConfig().getDouble("venomous_attack_damage_multiply_amount")))

                .replaceAll("\\{revitalize_effect_apply_range}", String.valueOf(RevitalizeModifierConfig.getRevitalizeModifierConfig().getDouble("revitalize_effect_apply_range")))
                .replaceAll("\\{revitalize_effect_apply_chance}", String.valueOf(RevitalizeModifierConfig.getRevitalizeModifierConfig().getDouble("revitalize_effect_apply_chance")))
                .replaceAll("\\{revitalize_effect_max_ticks}", String.valueOf(RevitalizeModifierConfig.getRevitalizeModifierConfig().getInt("revitalize_effect_max_ticks")))
                .replaceAll("\\{revitalize_apply_effects_per_tick_amount}", String.valueOf(RevitalizeModifierConfig.getRevitalizeModifierConfig().getInt("revitalize_apply_effects_per_tick_amount")))
                .replaceAll("\\{revitalize_max_effect_amount_per_entity}", String.valueOf(RevitalizeModifierConfig.getRevitalizeModifierConfig().getInt("revitalize_max_effect_amount_per_entity")));
        return returnValue;
    }

    /**
     * Check entity has ability.
     * @deprecated Use {@link AdvancedUtils#getAbilities(LivingEntity)} for similar effects.
     * @param entity entity to check
     * @param ability ability to check if
     * @return true when entity has ability, otherwise false
     */
    @Deprecated
    public static boolean hasAbility(LivingEntity entity, MonsterAbility ability){
        boolean value = false;
        for(MonsterAbility ability1 : getAbilities(entity)){
            if(ability1.equals(ability)){
                value = true;
            }
        }
        return value;
    }

    /**
     * Returns ability config instance.
     * @param ability ability to get instance from it
     * @return ability {@link FileConfiguration} config instance
     */
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
            case BOMBER -> returnValue = BomberModifierConfig.getBomberModifierConfig();
            case FLAMING -> returnValue = FlamingModifierConfig.getFlamingModifierConfig();
            case LASER -> returnValue = LaserModifierConfig.getLaserModifierConfig();
            case VENOMOUS -> returnValue = VenomousModifierConfig.getVenomousModifierConfig();
            case STORMY -> returnValue = StormyModifierConfig.getStormyModifierConfig();
            case FROZEN -> returnValue = FrozenModifierConfig.getFrozenModifierConfig();
            case LIGHTING -> returnValue = LightingModifierConfig.getLightingModifierConfig();
            case REVITALIZE -> returnValue = RevitalizeModifierConfig.getRevitalizeModifierConfig();
            default -> throw new IllegalArgumentException("Unknown MonsterAbility enum value");
        }
        return returnValue;
    }
}
