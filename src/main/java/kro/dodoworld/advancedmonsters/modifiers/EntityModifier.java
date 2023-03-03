package kro.dodoworld.advancedmonsters.modifiers;

import kro.dodoworld.advancedmonsters.config.modifier.HealthyModifierConfig;
import kro.dodoworld.advancedmonsters.config.modifier.SpeedyModifierConfig;
import kro.dodoworld.advancedmonsters.config.modifier.StormyModifierConfig;
import kro.dodoworld.advancedmonsters.config.modifier.TankModifierConfig;
import kro.dodoworld.advancedmonsters.config.unlock.EntityAbilityConfig;
import org.apache.commons.lang.WordUtils;
import org.bukkit.ChatColor;
import org.bukkit.Difficulty;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

import java.awt.Color;
import java.util.concurrent.ThreadLocalRandom;

public class EntityModifier implements Listener {

    @EventHandler
    public void onSpawn(EntitySpawnEvent event){
        if(event.getEntity().getWorld().getDifficulty().equals(Difficulty.PEACEFUL)) return;
        if(!(event.getEntity() instanceof Monster)) return;
        if(event.getEntity().getScoreboardTags().contains("adm_miniboss")) return;
        final ThreadLocalRandom rnd = ThreadLocalRandom.current();
        LivingEntity entity = (LivingEntity) event.getEntity();
        FileConfiguration config = EntityAbilityConfig.getEntityAbilityConfig();
        if(!(rnd.nextInt(0, 11) == 1)){
            int modifiedEntityType = rnd.nextInt(0,13);
            if(modifiedEntityType == 1){
                if(!config.getBoolean("healthy")) return;
                FileConfiguration healthyConfig = HealthyModifierConfig.getHealthyModifierConfig();
                entity.addScoreboardTag("adm_modifier_healthy");
                entity.setCustomNameVisible(true);
                entity.setCustomName(ChatColor.RED + "❤Healthy " + ChatColor.GRAY + toMobName(entity.getType().name()));
                entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() * healthyConfig.getDouble("health_multiply_amount"));
                entity.setHealth(entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());
            }
            if(modifiedEntityType == 2){
                if(!config.getBoolean("strong")) return;
                entity.addScoreboardTag("adm_modifier_strong");
                entity.setCustomNameVisible(true);
                entity.setCustomName(ChatColor.DARK_RED + "🗡Strong " + ChatColor.GRAY + toMobName(entity.getType().name()));
            }
            if(modifiedEntityType == 3){
                if(!config.getBoolean("tank")) return;
                FileConfiguration tankConfig = TankModifierConfig.getTankModifierConfig();
                entity.addScoreboardTag("adm_modifier_tank");
                entity.setCustomNameVisible(true);
                entity.setCustomName(ChatColor.DARK_GRAY + "❇Tank " + ChatColor.GRAY + toMobName(entity.getType().name()));
                entity.getAttribute(Attribute.GENERIC_ARMOR).addModifier(new AttributeModifier("generic.Armor", tankConfig.getDouble("bonus_defence_amount"), AttributeModifier.Operation.ADD_NUMBER));
                entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getBaseValue() * tankConfig.getDouble("speed_multiply_amount"));
            }
            if(modifiedEntityType == 4){
                if(!config.getBoolean("speedy")) return;
                FileConfiguration speedyConfig = SpeedyModifierConfig.getSpeedyModifierConfig();
                entity.addScoreboardTag("adm_modifier_speedy");
                entity.setCustomNameVisible(true);
                entity.setCustomName(ChatColor.WHITE + "✴Speedy " + ChatColor.GRAY + toMobName(entity.getType().name()));
                entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() * speedyConfig.getDouble("health_multiply_amount"));
                entity.setHealth(entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());
                entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getBaseValue() * speedyConfig.getDouble("speed_multiply_amount"));
            }
            if(modifiedEntityType == 5){
                if(!config.getBoolean("teleporter")) return;
                Monster monster = (Monster) entity;
                monster.addScoreboardTag("adm_modifier_teleporter");
                monster.setCustomNameVisible(true);
                monster.setCustomName(ChatColor.DARK_AQUA + "☯Teleporter " + ChatColor.GRAY + toMobName(entity.getType().name()));
            }
            if(modifiedEntityType == 6){
                if(!config.getBoolean("invisible")) return;
                entity.addScoreboardTag("adm_modifier_invisible");
                entity.setInvisible(true);
                entity.setCustomNameVisible(true);
                entity.setCustomName(ChatColor.DARK_GRAY + "▫Invisible " + ChatColor.GRAY + toMobName(entity.getType().name()));
            }
            if(modifiedEntityType == 7){
                if(!config.getBoolean("punchy")) return;
                entity.addScoreboardTag("adm_modifier_punchy");
                entity.setCustomNameVisible(true);
                entity.setCustomName(ChatColor.GREEN + "⇧Punchy " + ChatColor.GRAY + toMobName(entity.getType().name()));
            }
            if(modifiedEntityType == 8){
                if(!config.getBoolean("boomer")) return;
                entity.addScoreboardTag("adm_modifier_boomer");
                entity.setCustomNameVisible(true);
                entity.setCustomName(ChatColor.RED + "■Boomer " + ChatColor.GRAY + toMobName(entity.getType().name()));
            }
            if(modifiedEntityType == 9){
                if(!config.getBoolean("flaming")) return;
                entity.addScoreboardTag("adm_modifier_flaming");
                entity.setCustomNameVisible(true);
                entity.setCustomName(ChatColor.GOLD + "\uD83D\uDD25Flaming " + ChatColor.GRAY + toMobName(entity.getType().name()));
            }
            if(modifiedEntityType == 10){
                if(!config.getBoolean("laser")) return;
                entity.addScoreboardTag("adm_modifier_laser");
                entity.setCustomNameVisible(true);
                entity.setCustomName(net.md_5.bungee.api.ChatColor.of(new Color(250, 74, 20)) + "◎Laser " + ChatColor.GRAY + toMobName(entity.getType().name()));
            }
            if(modifiedEntityType == 11){
                if(!config.getBoolean("venomous")) return;
                entity.addScoreboardTag("adm_modifier_venomous");
                entity.setCustomNameVisible(true);
                entity.setCustomName(net.md_5.bungee.api.ChatColor.of(new Color(199, 204, 53)) + "☣Venomous " + ChatColor.GRAY + toMobName(entity.getType().name()));
            }
            if(modifiedEntityType == 12){
                if(!config.getBoolean("stormy")) return;
                FileConfiguration stormyConfig = StormyModifierConfig.getStormyModifierConfig();
                if(stormyConfig.getBoolean("only_spawn_when_storming")){
                    if(!entity.getWorld().hasStorm()) return;
                }
                entity.addScoreboardTag("adm_modifier_stormy");
                entity.setCustomNameVisible(true);
                entity.setCustomName(net.md_5.bungee.api.ChatColor.of(new Color(22, 184, 162)) + "\uD83C\uDF27Stormy " + ChatColor.GRAY + toMobName(entity.getType().name()));
            }
            //💣⚔ ️⚡▶️➖💠❇ ️🔰⚙️💥🎆🎈🎇🧨✨🎉🎎🎍🎋🎄🎃🎁🍖🍗🍩🍪🥄🚗❤ ️🧡💛💚💢♒♑☯ ️☦ ️🛐⛎♈♎🆔♑⚛ ️♾️✴ ️✳ ️
        }
    }

    private String toMobName(String name){
        return WordUtils.capitalizeFully(name.toLowerCase().replace('_', ' '));
    }
}
