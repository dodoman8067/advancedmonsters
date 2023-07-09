package kro.dodoworld.advancedmonsters.modifier.infect;

import io.papermc.paper.event.entity.EntityMoveEvent;
import kro.dodoworld.advancedmonsters.AdvancedMonsters;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Drowned;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Husk;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Zombie;
import org.bukkit.entity.ZombieVillager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

public class VillagerInfection implements Listener {

    private final Plugin plugin = AdvancedMonsters.getPlugin(AdvancedMonsters.class);

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event){
        if(event.getDamager() instanceof Zombie || event.getDamager() instanceof PigZombie
                || event.getDamager() instanceof ZombieVillager || event.getDamager() instanceof Drowned || event.getDamager() instanceof Husk){
            if(!(event.getEntity() instanceof Villager)) return;
            if(!event.getEntity().getPersistentDataContainer().has(new NamespacedKey(plugin, "is_infected"), PersistentDataType.INTEGER)) return;
            if(event.getEntity().getPersistentDataContainer().get(new NamespacedKey(plugin, "is_infected"), PersistentDataType.INTEGER) != 0) return;
            if(((int) (Math.random() * 20)) <= 7){
                event.getEntity().getPersistentDataContainer().set(new NamespacedKey(plugin, "is_infected"), PersistentDataType.INTEGER, 1);
            }
        }
    }

    @EventHandler
    public void onTouch(EntityMoveEvent event){
        if(!(event.getEntity() instanceof Villager villager)) return;
        if(!event.getEntity().getPersistentDataContainer().has(new NamespacedKey(plugin, "is_infected"), PersistentDataType.INTEGER)) return;
        if(event.getEntity().getPersistentDataContainer().get(new NamespacedKey(plugin, "is_infected"), PersistentDataType.INTEGER) == 0) return;
        for(Entity entity : villager.getNearbyEntities(1.5, 0, 1.5)){
            if(!(entity instanceof Villager)) continue;
            if(!entity.getPersistentDataContainer().has(new NamespacedKey(plugin, "is_infected"), PersistentDataType.INTEGER)) continue;
            if(entity.getPersistentDataContainer().get(new NamespacedKey(plugin, "is_infected"), PersistentDataType.INTEGER) != 0) continue;
            if(((int) (Math.random() * 20)) <= 14){
                entity.getPersistentDataContainer().set(new NamespacedKey(plugin, "is_infected"), PersistentDataType.INTEGER, 1);
            }
        }
    }

    @EventHandler
    public void onDeath(EntityDamageByEntityEvent event){
        if(!(event.getEntity() instanceof Villager villager)) return;
        if(event.getDamager() instanceof Zombie || event.getDamager() instanceof PigZombie
                || event.getDamager() instanceof ZombieVillager || event.getDamager() instanceof Drowned || event.getDamager() instanceof Husk) return;
        if(villager.getHealth() >= event.getFinalDamage())  return;
        if(event.getCause().equals(EntityDamageEvent.DamageCause.VOID)) return;
        if(!event.getEntity().getPersistentDataContainer().has(new NamespacedKey(plugin, "is_infected"), PersistentDataType.INTEGER)) return;
        if(event.getEntity().getPersistentDataContainer().get(new NamespacedKey(plugin, "is_infected"), PersistentDataType.INTEGER) == 0) return;
        villager.zombify();
    }
}
