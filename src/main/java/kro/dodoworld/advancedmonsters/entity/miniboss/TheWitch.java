package kro.dodoworld.advancedmonsters.entity.miniboss;

import com.destroystokyo.paper.event.entity.WitchConsumePotionEvent;
import com.destroystokyo.paper.event.entity.WitchReadyPotionEvent;
import com.destroystokyo.paper.event.entity.WitchThrowPotionEvent;
import kro.dodoworld.advancedmonsters.AdvancedMonsters;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Witch;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class TheWitch implements Listener {
    private static Plugin plugin = AdvancedMonsters.getPlugin(AdvancedMonsters.class);
    public static void createWitch(Location loc){

    }


    private static void setAttackPhase(int phase, Witch witch){
        if(!witch.getPersistentDataContainer().has(new NamespacedKey(plugin, "witch_phase"), PersistentDataType.INTEGER)) return;
        witch.getPersistentDataContainer().set(new NamespacedKey(plugin, "witch_phase"), PersistentDataType.INTEGER, phase);
    }

    private static int getPhase(Witch witch){
        if(!witch.getPersistentDataContainer().has(new NamespacedKey(plugin, "witch_phase"), PersistentDataType.INTEGER)) throw new IllegalArgumentException("Witch does not have witch_phase tag");
        return witch.getPersistentDataContainer().get(new NamespacedKey(plugin, "witch_phase"), PersistentDataType.INTEGER);
    }

    @EventHandler
    public void onWitchReady(WitchReadyPotionEvent event){
        if(!event.getEntity().getScoreboardTags().contains("adm_miniboss_thewitch")) return;
        if(getPhase(event.getEntity()) == 1 || getPhase(event.getEntity()) == 3) return;
        if(event.getPotion() == null) return;
        event.setCancelled(true);
    }

    @EventHandler
    public void onWitchThrowPotion(WitchThrowPotionEvent event){
        if(!event.getEntity().getScoreboardTags().contains("adm_miniboss_thewitch")) return;
        if(getPhase(event.getEntity()) != 1) return;
        ItemStack potion = new ItemStack(Material.SPLASH_POTION);
        if(event.getPotion() == null) {

        }
    }
}
