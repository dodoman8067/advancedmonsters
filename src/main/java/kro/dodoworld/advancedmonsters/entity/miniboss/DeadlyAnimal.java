package kro.dodoworld.advancedmonsters.entity.miniboss;

import org.bukkit.entity.Animals;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.EquipmentSlot;

public class DeadlyAnimal implements Listener {
    @EventHandler
    public void onHoldItem(PlayerInteractAtEntityEvent event){
        if(!(event.getRightClicked() instanceof Animals animal)) return;
        if(!animal.getScoreboardTags().contains("adm_animal_deadly")) return;
        if(event.getHand().equals(EquipmentSlot.HAND)){
            if(!animal.isBreedItem(event.getPlayer().getInventory().getItemInMainHand())) return;
            event.setCancelled(true);
        }
        if(event.getHand().equals(EquipmentSlot.OFF_HAND)){
            if(!animal.isBreedItem(event.getPlayer().getInventory().getItemInOffHand())) return;
            event.setCancelled(true);
        }
    }
}
