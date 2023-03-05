package kro.dodoworld.advancedmonsters.command;

import kro.dodoworld.advancedmonsters.util.AdvancedMonstersUtilMethods;
import kro.dodoworld.advancedmonsters.util.MonsterAbility;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class UnlockedMobs implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(command.getName().equalsIgnoreCase("ability")){
            if(args.length < 1){
                sender.sendMessage(ChatColor.AQUA + "-------------------------------------------");
                sender.sendMessage(ChatColor.GOLD + "해제된 몬스터 능력들");
                sender.spigot().sendMessage(getMessage());
                sender.sendMessage(ChatColor.AQUA + "-------------------------------------------");
            }else{
                try{
                    sendAbilityDescriptionMessage(MonsterAbility.valueOf(args[0].toUpperCase()), sender);
                }catch (IllegalArgumentException e){
                    sender.sendMessage(ChatColor.RED + "알 수 없는 능력입니다!");
                }
            }
        }
        return true;
    }


    private BaseComponent[] getMessage(){
        ComponentBuilder builder = new ComponentBuilder();
        for(MonsterAbility ability : MonsterAbility.values()){
            if(AdvancedMonstersUtilMethods.isRevealed(ability)){
                builder.append(AdvancedMonstersUtilMethods.getAbilitySymbol(ability) + getUnlockedMessage(ability)).event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.YELLOW + "클릭하여 "+ AdvancedMonstersUtilMethods.getAbilitySymbol(ability) + ability.toString() + ChatColor.YELLOW + " 능력의 정보 보기").create()));
            }else{
                builder.append(ChatColor.GRAY + "?" + getUnlockedMessage(ability)).event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("알 수 없는 능력입니다!").color(net.md_5.bungee.api.ChatColor.GRAY).create()));

            }
            builder.append(" ");
        }
        return builder.create();
    }

    private String getUnlockedMessage(MonsterAbility ability){
        String returnValue;
        if(AdvancedMonstersUtilMethods.isUnlocked(ability) && AdvancedMonstersUtilMethods.isRevealed(ability)){
            returnValue = ChatColor.GREEN + "" + ChatColor.BOLD + "✓";
        }else {
            returnValue = ChatColor.RED + "" + ChatColor.BOLD + "✗";
        }
        return returnValue;
    }


    private void sendAbilityDescriptionMessage(MonsterAbility ability, CommandSender sender){
        if(sender.isOp()){
            sender.sendMessage(ChatColor.AQUA + "-------------------------------------------");
            sender.sendMessage(AdvancedMonstersUtilMethods.getAbilitySymbol(ability) + ability.toString() + ChatColor.GOLD + " 능력에 대한 정보");
            sender.sendMessage(" ");
            for(String s : AdvancedMonstersUtilMethods.getAbilityConfig(ability).getStringList("command_description")){
                sender.sendMessage(AdvancedMonstersUtilMethods.replace(s));
            }
            sender.sendMessage(ChatColor.AQUA + "-------------------------------------------");
        }else{
            if(!AdvancedMonstersUtilMethods.isUnlocked(ability)){
                sender.sendMessage(ChatColor.RED + "알 수 없는 능력입니다!");
                return;
            }
            sender.sendMessage(ChatColor.AQUA + "-------------------------------------------");
            sender.sendMessage(AdvancedMonstersUtilMethods.getAbilitySymbol(ability) + ability.toString() + ChatColor.GOLD + " 능력에 대한 정보");
            sender.sendMessage(" ");
            for(String s : AdvancedMonstersUtilMethods.getAbilityConfig(ability).getStringList("command_description")){
                sender.sendMessage(AdvancedMonstersUtilMethods.replace(s));
            }
            sender.sendMessage(ChatColor.AQUA + "-------------------------------------------");
        }
    }
}
