package kro.dodoworld.advancedmonsters.command.tab;

import kro.dodoworld.advancedmonsters.util.AdvancedMonstersUtilMethods;
import kro.dodoworld.advancedmonsters.util.MonsterAbility;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class AbilityTabCompleter implements TabCompleter {
    private final List<String> returnArgs;
    public AbilityTabCompleter(){
        returnArgs = new ArrayList<>();
        for(MonsterAbility ability : MonsterAbility.values()){
            returnArgs.add(ability.toString().toLowerCase());
        }
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(command.getName().equals("ability") && sender instanceof Player player){
            if(args.length < 2){
                if(player.isOp()){
                    return returnArgs;
                }else{
                    List<String> modifiedList = new ArrayList<>();
                    for(String s : returnArgs){
                        if(!AdvancedMonstersUtilMethods.isRevealed(MonsterAbility.valueOf(s.toUpperCase()))) continue;
                        modifiedList.add(s);
                    }
                    return modifiedList;
                }
            }
        }
        return null;
    }
}
