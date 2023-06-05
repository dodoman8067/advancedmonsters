package kro.dodoworld.advancedmonsters.command.tab;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MiniBossSpawnTabCompleter implements TabCompleter {
    private final List<String> returnArgs;
    public MiniBossSpawnTabCompleter(){
        returnArgs = new ArrayList<>();
        returnArgs.add("diamond_zombie");
        returnArgs.add("leaping_spider");
        returnArgs.add("voidgloom");
        returnArgs.add("inferno");
        returnArgs.add("bombie");
        returnArgs.add("storm");
        returnArgs.add("earth_quaker");
        returnArgs.add("sludgegore");
    }
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(command.getName().equals("admminiboss")){
            if(args.length < 2){
                return returnArgs;
            }
        }
        return null;
    }
}
