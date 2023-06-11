package kro.dodoworld.advancedmonsters.modifier;

import kro.dodoworld.advancedmonsters.config.modifier.HealthyModifierConfig;
import kro.dodoworld.advancedmonsters.config.data.UnlockedEntityAbilities;
import kro.dodoworld.advancedmonsters.config.modifier.SpeedyModifierConfig;
import kro.dodoworld.advancedmonsters.config.modifier.StormyModifierConfig;
import kro.dodoworld.advancedmonsters.config.modifier.TankModifierConfig;
import kro.dodoworld.advancedmonsters.modifier.ability.type.RevitalizeModifier;
import kro.dodoworld.advancedmonsters.modifier.ability.type.TeleporterModifier;
import kro.dodoworld.advancedmonsters.util.AdvancedUtils;
import kro.dodoworld.advancedmonsters.util.MonsterAbility;
import net.kyori.adventure.text.Component;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Difficulty;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Monster;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Random;

public class EntityModifier implements Listener {

    private final EnumSet<MonsterAbility> possibleAbilities = EnumSet.of(
            MonsterAbility.HEALTHY, MonsterAbility.INVISIBLE, MonsterAbility.BOOMER, MonsterAbility.FLAMING, MonsterAbility.LASER,
            MonsterAbility.PUNCHY, MonsterAbility.SPEEDY, MonsterAbility.STORMY, MonsterAbility.STRONG, MonsterAbility.TELEPORTER, MonsterAbility.TANK, MonsterAbility.VENOMOUS,
            MonsterAbility.FROZEN, MonsterAbility.LIGHTING, MonsterAbility.REVITALIZE
    );
    private final Random random = new Random();

    @EventHandler
    public void onSpawn(CreatureSpawnEvent event) {
        if (event.getEntity().getWorld().getDifficulty().equals(Difficulty.PEACEFUL)) return;
        if (event.getSpawnReason() == CreatureSpawnEvent.SpawnReason.CUSTOM) return;
        if (!(event.getEntity() instanceof Monster monster)) return;
        if (monster.getScoreboardTags().contains("adm_miniboss")) return;
        if (random.nextDouble(0, 101) <= 20) return;
        FileConfiguration config = UnlockedEntityAbilities.getUnlockedEntityAbilityConfig();
        MonsterAbility ability = getRandomAbility(config);
        if(ability == null){
            Bukkit.getLogger().warning("[AdvancedMonsters] Plugin tried to add null ability to monster!");
            return;
        }
        applyAbility(ability, monster);
    }

    private MonsterAbility getRandomAbility(FileConfiguration config) {
        List<MonsterAbility> abilities = new ArrayList<>(possibleAbilities);
        Collections.shuffle(abilities);
        for (MonsterAbility ability : abilities) {
            if (config.getBoolean(ability.toString().toLowerCase())) {
                return ability;
            }
        }
        return null;
    }

    private void applyAbility(MonsterAbility ability, Monster monster) {
        switch (ability) {
            case HEALTHY -> {
                FileConfiguration healthyConfig = HealthyModifierConfig.getHealthyModifierConfig();
                monster.addScoreboardTag("adm_modifier_healthy");
                monster.setCustomNameVisible(true);
                monster.customName(AdvancedUtils.getAbilitySymbolWithColor(MonsterAbility.HEALTHY)
                        .append(Component.text(MonsterAbility.HEALTHY + " " + toMobName(monster.getType().name()))));
                AttributeInstance maxHealthAttribute = monster.getAttribute(Attribute.GENERIC_MAX_HEALTH);
                double healthMultiplier = healthyConfig.getDouble("healthy_health_multiply_amount");
                maxHealthAttribute.setBaseValue(maxHealthAttribute.getBaseValue() * healthMultiplier);
                monster.setHealth(maxHealthAttribute.getBaseValue());
            }
            case STRONG -> {
                monster.addScoreboardTag("adm_modifier_strong");
                monster.setCustomNameVisible(true);
                monster.customName(AdvancedUtils.getAbilitySymbolWithColor(MonsterAbility.STRONG)
                        .append(Component.text(MonsterAbility.STRONG.toString() + " " + toMobName(monster.getType().name()))));
            }
            case TANK -> {
                FileConfiguration tankConfig = TankModifierConfig.getTankModifierConfig();
                monster.addScoreboardTag("adm_modifier_tank");
                monster.setCustomNameVisible(true);
                monster.customName(AdvancedUtils.getAbilitySymbolWithColor(MonsterAbility.TANK).append(Component.text(MonsterAbility.TANK.toString() + " ")
                        .append(Component.text(toMobName(monster.getType().name())))));
                monster.getAttribute(Attribute.GENERIC_ARMOR).addModifier(new AttributeModifier("generic.Armor", tankConfig.getDouble("tank_bonus_defence_amount"), AttributeModifier.Operation.ADD_NUMBER));
                monster.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(monster.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getBaseValue() * tankConfig.getDouble("tank_speed_multiply_amount"));
            }
            case SPEEDY -> {
                FileConfiguration speedyConfig = SpeedyModifierConfig.getSpeedyModifierConfig();
                monster.addScoreboardTag("adm_modifier_speedy");
                monster.setCustomNameVisible(true);
                monster.customName(AdvancedUtils.getAbilitySymbolWithColor(MonsterAbility.SPEEDY).append(Component.text(MonsterAbility.SPEEDY.toString() + " ")
                        .append(Component.text(toMobName(monster.getType().name())))));
                monster.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(monster.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() * speedyConfig.getDouble("speedy_health_multiply_amount"));
                monster.setHealth(monster.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());
                monster.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(monster.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getBaseValue() * speedyConfig.getDouble("speedy_speed_multiply_amount"));
            }
            case TELEPORTER -> {
                monster.addScoreboardTag("adm_modifier_teleporter");
                monster.setCustomNameVisible(true);
                monster.customName(AdvancedUtils.getAbilitySymbolWithColor(MonsterAbility.TELEPORTER).append(Component.text(MonsterAbility.TELEPORTER.toString() + " ").append(Component.text(toMobName(monster.getType().name())))));
                TeleporterModifier.getTeleportingMonsters().add(monster.getUniqueId());
            }
            case INVISIBLE -> {
                monster.addScoreboardTag("adm_modifier_invisible");
                monster.setInvisible(true);
                monster.setCustomNameVisible(true);
                monster.customName(AdvancedUtils.getAbilitySymbolWithColor(MonsterAbility.INVISIBLE).append(Component.text(MonsterAbility.INVISIBLE.toString() + " ").append(Component.text(toMobName(monster.getType().name())))));
            }
            case PUNCHY -> {
                monster.addScoreboardTag("adm_modifier_punchy");
                monster.setCustomNameVisible(true);
                monster.customName(AdvancedUtils.getAbilitySymbolWithColor(MonsterAbility.PUNCHY).append(Component.text(MonsterAbility.PUNCHY.toString() + " ").append(Component.text(toMobName(monster.getType().name())))));
            }
            case BOOMER -> {
                monster.addScoreboardTag("adm_modifier_boomer");
                monster.setCustomNameVisible(true);
                monster.customName(AdvancedUtils.getAbilitySymbolWithColor(MonsterAbility.BOOMER).append(Component.text(MonsterAbility.BOOMER.toString() + " ").append(Component.text(toMobName(monster.getType().name())))));
            }
            case FLAMING -> {
                monster.addScoreboardTag("adm_modifier_flaming");
                monster.setCustomNameVisible(true);
                monster.customName(AdvancedUtils.getAbilitySymbolWithColor(MonsterAbility.FLAMING).append(Component.text(MonsterAbility.FLAMING.toString() + " ").append(Component.text(toMobName(monster.getType().name())))));
            }
            case LASER -> {
                monster.addScoreboardTag("adm_modifier_laser");
                monster.setCustomNameVisible(true);
                monster.customName(AdvancedUtils.getAbilitySymbolWithColor(MonsterAbility.LASER).append(Component.text(MonsterAbility.LASER.toString() + " ").append(Component.text(toMobName(monster.getType().name())))));
            }
            case VENOMOUS -> {
                monster.addScoreboardTag("adm_modifier_venomous");
                monster.setCustomNameVisible(true);
                monster.customName(AdvancedUtils.getAbilitySymbolWithColor(MonsterAbility.VENOMOUS).append(Component.text(MonsterAbility.VENOMOUS.toString() + " ").append(Component.text(toMobName(monster.getType().name())))));
            }
            case STORMY -> {
                FileConfiguration stormyConfig = StormyModifierConfig.getStormyModifierConfig();
                if(stormyConfig.getBoolean("stormy_only_spawn_when_storming")){
                    if(!monster.getWorld().hasStorm()) return;
                }
                monster.addScoreboardTag("adm_modifier_stormy");
                monster.setCustomNameVisible(true);
                monster.customName(AdvancedUtils.getAbilitySymbolWithColor(MonsterAbility.STORMY).append(Component.text(MonsterAbility.STORMY.toString() + " ").append(Component.text(toMobName(monster.getType().name())))));
            }
            case FROZEN -> {
                monster.addScoreboardTag("adm_modifier_frozen");
                monster.setCustomNameVisible(true);
                monster.customName(AdvancedUtils.getAbilitySymbolWithColor(MonsterAbility.FROZEN).append(Component.text(MonsterAbility.FROZEN.toString() + " ").append(Component.text(toMobName(monster.getType().name())))));
            }
            case LIGHTING -> {
                monster.addScoreboardTag("adm_modifier_lighting");
                monster.setCustomNameVisible(true);
                monster.customName(AdvancedUtils.getAbilitySymbolWithColor(MonsterAbility.LIGHTING).append(Component.text(MonsterAbility.LIGHTING.toString() + " ").append(Component.text(toMobName(monster.getType().name())))));
            }
            case REVITALIZE -> {
                monster.addScoreboardTag("adm_modifier_revitalize");
                monster.setCustomNameVisible(true);
                monster.customName(AdvancedUtils.getAbilitySymbolWithColor(MonsterAbility.REVITALIZE).append(Component.text(MonsterAbility.REVITALIZE.toString() + " ").append(Component.text(toMobName(monster.getType().name())))));
                RevitalizeModifier.getRevitalizeMonsters().add(monster.getUniqueId());
            }
        }
    }

    private String toMobName(String typeName){
        return ChatColor.GRAY + WordUtils.capitalize(typeName.toLowerCase().replace("_", " "));
    }
}