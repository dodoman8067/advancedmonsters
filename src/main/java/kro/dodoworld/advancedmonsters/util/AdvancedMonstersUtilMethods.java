package kro.dodoworld.advancedmonsters.util;

import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class AdvancedMonstersUtilMethods {
    public static List<MonsterAbility> getAbilities(LivingEntity entity){
        List<MonsterAbility> returnValue;

        returnValue = new ArrayList<>();
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
            }
        }
        return returnValue;
    }

    public static String getAbilitySymbols(MonsterAbility monsterAbility){
        String returnValue = null;
            switch (monsterAbility) {
                case HEALTHY -> returnValue = ChatColor.RED + "❤";
                case STRONG -> returnValue = ChatColor.DARK_RED + "🗡";
                case TANK -> returnValue = ChatColor.DARK_GRAY + "❇";
                case SPEEDY -> returnValue = ChatColor.WHITE + "✴";
                case TELEPORTER -> returnValue = ChatColor.DARK_AQUA + "☯";
                case INVISIBLE -> returnValue = ChatColor.DARK_GRAY + "▫";
                case PUNCHY -> returnValue = ChatColor.GREEN + "⇧";
                case BOOMER -> returnValue = ChatColor.RED + "■";
                case FLAMING -> returnValue = ChatColor.GOLD + "\uD83D\uDD25";
                case LASER -> returnValue = net.md_5.bungee.api.ChatColor.of(new Color(250, 74, 20)) + "◎";
                case VENOMOUS -> returnValue = net.md_5.bungee.api.ChatColor.of(new Color(199, 204, 53)) + "☣";
                case STORMY -> returnValue = net.md_5.bungee.api.ChatColor.of(new Color(22, 184, 162)) + "\uD83C\uDF27";
            }
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
}
