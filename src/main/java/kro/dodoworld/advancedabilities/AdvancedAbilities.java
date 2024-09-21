package kro.dodoworld.advancedabilities;

import kro.dodoworld.advancedabilities.ability.ItemAbilities;
import org.bukkit.plugin.java.JavaPlugin;

public class AdvancedAbilities {
    public static void init(JavaPlugin plugin){
        plugin.getServer().getPluginManager().registerEvents(new ItemAbilities(), plugin);
    }
}
