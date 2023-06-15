package kro.dodoworld.advancedmonsters.entity.miniboss;

import com.destroystokyo.paper.event.entity.WitchThrowPotionEvent;
import org.bukkit.Location;
import org.bukkit.entity.Witch;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class TheWitch implements Listener {
    public static void createWitch(Location loc){

    }


    private static void setAttackPhase(int phase, Witch witch){
        if(phase == 1){
            witch.setPotionUseTimeLeft(-1);
        }
    }

    @EventHandler
    public void onWitchThrow(WitchThrowPotionEvent event){
        if(!event.getEntity().getScoreboardTags().contains("adm_miniboss_thewitch")) return;

    }

}
