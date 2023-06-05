package kro.dodoworld.advancedmonsters.command;

import kro.dodoworld.advancedmonsters.util.AdvancedMonstersUtilMethods;
import kro.dodoworld.advancedmonsters.util.MonsterAbility;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class AbilityCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(command.getName().equalsIgnoreCase("ability")){
            if(args.length < 1){
                sender.sendMessage(ChatColor.AQUA + "-------------------------------------------");
                sender.sendMessage(ChatColor.GOLD + "해제된 몬스터 능력들");
                sender.sendMessage(getMessage());
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

    private Component getMessage(){
        TextComponent.Builder returnValue = Component.text();
        for(MonsterAbility ability : MonsterAbility.values()){
            if(AdvancedMonstersUtilMethods.isRevealed(ability)){
                returnValue.append(AdvancedMonstersUtilMethods.getAbilitySymbolWithColor(ability).append(getUnlockedMessage(ability)).hoverEvent(HoverEvent.hoverEvent(HoverEvent.Action.SHOW_TEXT, AdvancedMonstersUtilMethods.getAbilitySymbolWithColor(ability).append(Component.text(ability.toString() + ChatColor.YELLOW + " 능력의 설명 보기"))))
                        .clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/ability " + ability.toString().toLowerCase())) );
            }else{
                returnValue.append(Component.text(ChatColor.GRAY + "?").append(getUnlockedMessage(ability))).hoverEvent(HoverEvent.hoverEvent(HoverEvent.Action.SHOW_TEXT, Component.text(ChatColor.GRAY + "아직 발견되지 않은 능력입니다!")));
            }
            returnValue.append(Component.text(" "));
        }

        return returnValue.build().asComponent();
    }

    private Component getUnlockedMessage(MonsterAbility ability){
        Component returnValue;
        if(AdvancedMonstersUtilMethods.isUnlocked(ability) && AdvancedMonstersUtilMethods.isRevealed(ability)){
            returnValue = Component.text(ChatColor.GREEN + "" + ChatColor.BOLD + "✓");
        }else {
            returnValue = Component.text(ChatColor.RED + "" + ChatColor.BOLD + "✗");
        }
        return returnValue;
    }

    private void sendAbilityDescriptionMessage(MonsterAbility ability, CommandSender sender){
        if(sender.isOp()){
            sender.sendMessage(ChatColor.AQUA + "-------------------------------------------");
            sender.sendMessage(AdvancedMonstersUtilMethods.getAbilitySymbolWithColor(ability).append(Component.text(ability.toString()).append(Component.text(" 능력에 대한 정보").color(TextColor.color(0xFFAA00)))));
            sender.sendMessage(" ");
            for(String s : AdvancedMonstersUtilMethods.getAbilityConfig(ability).getStringList("command_description")){
                sender.sendMessage(AdvancedMonstersUtilMethods.replace(s));
            }
            sender.sendMessage(ChatColor.AQUA + "-------------------------------------------");
        }else{
            if(!AdvancedMonstersUtilMethods.isRevealed(ability)){
                sender.sendMessage(ChatColor.RED + "알 수 없는 능력입니다!");
                return;
            }
            sender.sendMessage(ChatColor.AQUA + "-------------------------------------------");
            sender.sendMessage(AdvancedMonstersUtilMethods.getAbilitySymbolWithColor(ability).append(Component.text(ability.toString()).append(Component.text(" 능력에 대한 정보").color(TextColor.color(0xFFAA00)))));
            sender.sendMessage(" ");
            for(String s : AdvancedMonstersUtilMethods.getAbilityConfig(ability).getStringList("command_description")){
                sender.sendMessage(AdvancedMonstersUtilMethods.replace(s));
            }
            sender.sendMessage(ChatColor.AQUA + "-------------------------------------------");
        }
    }
}
