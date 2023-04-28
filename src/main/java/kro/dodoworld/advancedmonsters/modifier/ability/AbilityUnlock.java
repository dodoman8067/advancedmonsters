package kro.dodoworld.advancedmonsters.modifier.ability;

import kro.dodoworld.advancedmonsters.event.MonsterAbilityUnlockEvent;
import kro.dodoworld.advancedmonsters.util.AdvancedMonstersUtilMethods;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class AbilityUnlock implements Listener {
    @EventHandler
    public void onUnlock(MonsterAbilityUnlockEvent event){
        sendMessage(event);
    }

    private void sendMessage(MonsterAbilityUnlockEvent event){
        Bukkit.broadcast(Component.text("----------------------------------------", NamedTextColor.YELLOW, TextDecoration.BOLD));
        Bukkit.broadcast(Component.text("              능력 해제", NamedTextColor.YELLOW, TextDecoration.BOLD));
        Bukkit.broadcast(Component.text("               ").append(AdvancedMonstersUtilMethods.getAbilitySymbolWithColor(event.getAbility()).append(Component.text(event.getAbility().toString()))));
        Bukkit.broadcast(Component.text("               "));
        for(String s : AdvancedMonstersUtilMethods.getAbilityConfig(event.getAbility()).getStringList("command_description")){
            Bukkit.broadcast(Component.text(s));
        }
        Bukkit.broadcast(Component.text("-----------------------------------------", NamedTextColor.YELLOW, TextDecoration.BOLD));
    }
}
