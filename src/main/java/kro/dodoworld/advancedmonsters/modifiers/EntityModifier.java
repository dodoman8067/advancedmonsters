package kro.dodoworld.advancedmonsters.modifiers;

import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Difficulty;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftLivingEntity;
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
        if(!(rnd.nextInt(0, 11) == 1)){
            int modifiedEntityType = rnd.nextInt(0,11);
            if(modifiedEntityType == 1){
                entity.addScoreboardTag("adm_modifier_healthy");
                entity.setCustomNameVisible(true);
                entity.setCustomName(ChatColor.RED + "❤Healthy " + ChatColor.GRAY + StringUtils.capitalize(entity.getType().name().toLowerCase()));
                entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() * 2);
                entity.setHealth(entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());
            }
            if(modifiedEntityType == 2){
                entity.addScoreboardTag("adm_modifier_strong");
                entity.setCustomNameVisible(true);
                entity.setCustomName(ChatColor.DARK_RED + "🗡Strong " + ChatColor.GRAY + StringUtils.capitalize(entity.getType().name().toLowerCase()));
            }
            if(modifiedEntityType == 3){
                entity.addScoreboardTag("adm_modifier_tank");
                entity.setCustomNameVisible(true);
                entity.setCustomName(ChatColor.DARK_GRAY + "❇Tank " + ChatColor.GRAY + StringUtils.capitalize(entity.getType().name().toLowerCase()));
                entity.getAttribute(Attribute.GENERIC_ARMOR).addModifier(new AttributeModifier("generic.Armor", 15, AttributeModifier.Operation.ADD_NUMBER));
                entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getBaseValue() * 0.4);
            }
            if(modifiedEntityType == 4){
                entity.addScoreboardTag("adm_modifier_speedy");
                entity.setCustomNameVisible(true);
                entity.setCustomName(ChatColor.WHITE + "✴Speedy " + ChatColor.GRAY + StringUtils.capitalize(entity.getType().name().toLowerCase()));
                entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() * 0.5);
                entity.setHealth(entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());
                entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getBaseValue() * 2);
            }
            if(modifiedEntityType == 5){
                Monster monster = (Monster) entity;
                monster.addScoreboardTag("adm_modifier_teleporter");
                monster.setCustomNameVisible(true);
                monster.setCustomName(ChatColor.DARK_AQUA + "☯Teleporter " + ChatColor.GRAY + StringUtils.capitalize(entity.getType().name().toLowerCase()));
            }
            if(modifiedEntityType == 6){
                entity.addScoreboardTag("adm_modifier_invisible");
                entity.setInvisible(true);
                entity.setCustomNameVisible(true);
                entity.setCustomName(ChatColor.DARK_GRAY + "Invisible " +StringUtils.capitalize(entity.getType().name().toLowerCase()));
            }
            if(modifiedEntityType == 7){
                entity.addScoreboardTag("adm_modifier_punchy");
                entity.setCustomNameVisible(true);
                entity.setCustomName(ChatColor.GREEN + "Punchy " + ChatColor.GRAY + StringUtils.capitalize(entity.getType().name().toLowerCase()));
            }
            if(modifiedEntityType == 8){
                entity.addScoreboardTag("adm_modifier_boomer");
                entity.setCustomNameVisible(true);
                entity.setCustomName(ChatColor.RED + "Boomer " + ChatColor.GRAY +StringUtils.capitalize(entity.getType().name().toLowerCase()));
            }
            if(modifiedEntityType == 9){
                entity.addScoreboardTag("adm_modifier_flaming");
                entity.setCustomNameVisible(true);
                entity.setCustomName(ChatColor.GOLD + "\uD83D\uDD25Flaming " + ChatColor.GRAY +StringUtils.capitalize(entity.getType().name().toLowerCase()));
            }
            if(modifiedEntityType == 10){
                entity.addScoreboardTag("adm_modifier_laser");
                entity.setCustomNameVisible(true);
                entity.setCustomName(net.md_5.bungee.api.ChatColor.of(new Color(250, 74, 20)) + "◎Laser " + ChatColor.GRAY + StringUtils.capitalize(entity.getType().name().toLowerCase()));
            }
            //💣⚔ ️⚡▶️➖💠❇ ️🔰⚙️💥🎆🎈🎇🧨✨🎉🎎🎍🎋🎄🎃🎁🍖🍗🍩🍪🥄🚗❤ ️🧡💛💚💢♒♑☯ ️☦ ️🛐⛎♈♎🆔♑⚛ ️♾️✴ ️✳ ️
        }
    }
}
