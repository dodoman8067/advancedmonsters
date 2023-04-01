package kro.dodoworld.advancedmonsters.modifiers;

import kro.dodoworld.advancedmonsters.config.modifier.HealthyModifierConfig;
import kro.dodoworld.advancedmonsters.config.modifier.SpeedyModifierConfig;
import kro.dodoworld.advancedmonsters.config.modifier.StormyModifierConfig;
import kro.dodoworld.advancedmonsters.config.modifier.TankModifierConfig;
import kro.dodoworld.advancedmonsters.config.data.UnlockedEntityAbilities;
import kro.dodoworld.advancedmonsters.util.AdvancedMonstersUtilMethods;
import kro.dodoworld.advancedmonsters.util.MonsterAbility;
import net.kyori.adventure.text.Component;
import org.apache.commons.lang.WordUtils;
import org.bukkit.ChatColor;
import org.bukkit.Difficulty;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Monster;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.util.concurrent.ThreadLocalRandom;

public class EntityModifier implements Listener {

    @EventHandler
    public void onSpawn(CreatureSpawnEvent event){
        if(event.getEntity().getWorld().getDifficulty().equals(Difficulty.PEACEFUL)) return;
        if(event.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.CUSTOM)) return;
        if(!(event.getEntity() instanceof Monster entity)) return;
        if(event.getEntity().getScoreboardTags().contains("adm_miniboss")) return;
        final ThreadLocalRandom rnd = ThreadLocalRandom.current();
        FileConfiguration config = UnlockedEntityAbilities.getUnlockedEntityAbilityConfig();
        if(!(rnd.nextInt(0, 11) == 1)){
            int modifiedEntityType = rnd.nextInt(0,13);
            if(modifiedEntityType == 1){
                if(!config.getBoolean("healthy")) return;
                FileConfiguration healthyConfig = HealthyModifierConfig.getHealthyModifierConfig();
                entity.addScoreboardTag("adm_modifier_healthy");
                entity.setCustomNameVisible(true);
                entity.customName(AdvancedMonstersUtilMethods.getAbilitySymbolWithColor(MonsterAbility.HEALTHY).append(Component.text(MonsterAbility.HEALTHY.toString() + " ").append(Component.text(toMobName(entity.getType().name())))));
                entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() * healthyConfig.getDouble("health_multiply_amount"));
                entity.setHealth(entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());
                
            }
            if(modifiedEntityType == 2){
                if(!config.getBoolean("strong")) return;
                entity.addScoreboardTag("adm_modifier_strong");
                entity.setCustomNameVisible(true);
                entity.customName(AdvancedMonstersUtilMethods.getAbilitySymbolWithColor(MonsterAbility.STRONG).append(Component.text(MonsterAbility.STRONG.toString() + " ").append(Component.text(toMobName(entity.getType().name())))));
            }
            if(modifiedEntityType == 3){
                if(!config.getBoolean("tank")) return;
                FileConfiguration tankConfig = TankModifierConfig.getTankModifierConfig();
                entity.addScoreboardTag("adm_modifier_tank");
                entity.setCustomNameVisible(true);
                entity.customName(AdvancedMonstersUtilMethods.getAbilitySymbolWithColor(MonsterAbility.TANK).append(Component.text(MonsterAbility.TANK.toString() + " ").append(Component.text(toMobName(entity.getType().name())))));
                entity.getAttribute(Attribute.GENERIC_ARMOR).addModifier(new AttributeModifier("generic.Armor", tankConfig.getDouble("bonus_defence_amount"), AttributeModifier.Operation.ADD_NUMBER));
                entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getBaseValue() * tankConfig.getDouble("speed_multiply_amount"));
            }
            if(modifiedEntityType == 4){
                if(!config.getBoolean("speedy")) return;
                FileConfiguration speedyConfig = SpeedyModifierConfig.getSpeedyModifierConfig();
                entity.addScoreboardTag("adm_modifier_speedy");
                entity.setCustomNameVisible(true);
                entity.customName(AdvancedMonstersUtilMethods.getAbilitySymbolWithColor(MonsterAbility.SPEEDY).append(Component.text(MonsterAbility.SPEEDY.toString() + " ").append(Component.text(toMobName(entity.getType().name())))));
                entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() * speedyConfig.getDouble("health_multiply_amount"));
                entity.setHealth(entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());
                entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getBaseValue() * speedyConfig.getDouble("speed_multiply_amount"));
            }
            if(modifiedEntityType == 5){
                if(!config.getBoolean("teleporter")) return;
                Monster monster = (Monster) entity;
                monster.addScoreboardTag("adm_modifier_teleporter");
                monster.setCustomNameVisible(true);
                entity.customName(AdvancedMonstersUtilMethods.getAbilitySymbolWithColor(MonsterAbility.TELEPORTER).append(Component.text(MonsterAbility.TELEPORTER.toString() + " ").append(Component.text(toMobName(entity.getType().name())))));
            }
            if(modifiedEntityType == 6){
                if(!config.getBoolean("invisible")) return;
                entity.addScoreboardTag("adm_modifier_invisible");
                entity.setInvisible(true);
                entity.setCustomNameVisible(true);
                entity.customName(AdvancedMonstersUtilMethods.getAbilitySymbolWithColor(MonsterAbility.INVISIBLE).append(Component.text(MonsterAbility.INVISIBLE.toString() + " ").append(Component.text(toMobName(entity.getType().name())))));
            }
            if(modifiedEntityType == 7){
                if(!config.getBoolean("punchy")) return;
                entity.addScoreboardTag("adm_modifier_punchy");
                entity.setCustomNameVisible(true);
                entity.customName(AdvancedMonstersUtilMethods.getAbilitySymbolWithColor(MonsterAbility.PUNCHY).append(Component.text(MonsterAbility.PUNCHY.toString() + " ").append(Component.text(toMobName(entity.getType().name())))));
            }
            if(modifiedEntityType == 8){
                if(!config.getBoolean("boomer")) return;
                entity.addScoreboardTag("adm_modifier_boomer");
                entity.setCustomNameVisible(true);
                entity.customName(AdvancedMonstersUtilMethods.getAbilitySymbolWithColor(MonsterAbility.BOOMER).append(Component.text(MonsterAbility.BOOMER.toString() + " ").append(Component.text(toMobName(entity.getType().name())))));
            }
            if(modifiedEntityType == 9){
                if(!config.getBoolean("flaming")) return;
                entity.addScoreboardTag("adm_modifier_flaming");
                entity.setCustomNameVisible(true);
                entity.customName(AdvancedMonstersUtilMethods.getAbilitySymbolWithColor(MonsterAbility.FLAMING).append(Component.text(MonsterAbility.FLAMING.toString() + " ").append(Component.text(toMobName(entity.getType().name())))));
            }
            if(modifiedEntityType == 10){
                if(!config.getBoolean("laser")) return;
                entity.addScoreboardTag("adm_modifier_laser");
                entity.setCustomNameVisible(true);
                entity.customName(AdvancedMonstersUtilMethods.getAbilitySymbolWithColor(MonsterAbility.LASER).append(Component.text(MonsterAbility.LASER.toString() + " ").append(Component.text(toMobName(entity.getType().name())))));
            }
            if(modifiedEntityType == 11){
                if(!config.getBoolean("venomous")) return;
                entity.addScoreboardTag("adm_modifier_venomous");
                entity.setCustomNameVisible(true);
                entity.customName(AdvancedMonstersUtilMethods.getAbilitySymbolWithColor(MonsterAbility.VENOMOUS).append(Component.text(MonsterAbility.VENOMOUS.toString() + " ").append(Component.text(toMobName(entity.getType().name())))));
            }
            if(modifiedEntityType == 12){
                if(!config.getBoolean("stormy")) return;
                FileConfiguration stormyConfig = StormyModifierConfig.getStormyModifierConfig();
                if(stormyConfig.getBoolean("only_spawn_when_storming")){
                    if(!entity.getWorld().hasStorm()) return;
                }
                entity.addScoreboardTag("adm_modifier_stormy");
                entity.setCustomNameVisible(true);
                entity.customName(AdvancedMonstersUtilMethods.getAbilitySymbolWithColor(MonsterAbility.STORMY).append(Component.text(MonsterAbility.STORMY.toString() + " ").append(Component.text(toMobName(entity.getType().name())))));
            }
            //💣⚔ ️⚡▶️➖💠❇ ️🔰⚙️💥🎆🎈🎇🧨✨🎉🎎🎍🎋🎄🎃🎁🍖🍗🍩🍪🥄🚗❤ ️🧡💛💚💢♒♑☯ ️☦ ️🛐⛎♈♎🆔♑⚛ ️♾️✴ ️✳ ️
        }
    }

    private String toMobName(String name){
        return ChatColor.GRAY + WordUtils.capitalizeFully(name.toLowerCase().replace('_', ' '));
    }
}
