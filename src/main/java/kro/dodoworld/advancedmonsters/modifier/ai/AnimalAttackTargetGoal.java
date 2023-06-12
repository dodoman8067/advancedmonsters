package kro.dodoworld.advancedmonsters.modifier.ai;

import com.destroystokyo.paper.entity.ai.Goal;
import com.destroystokyo.paper.entity.ai.GoalKey;
import com.destroystokyo.paper.entity.ai.GoalType;
import kro.dodoworld.advancedmonsters.AdvancedMonsters;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.EnumSet;

public class AnimalAttackTargetGoal implements Goal<Animals>, Listener {
    private final Plugin plugin = AdvancedMonsters.getPlugin(AdvancedMonsters.class);
    private final GoalKey<Animals> goalKey = GoalKey.of(Animals.class, new NamespacedKey(plugin, "animals_attack"));
    private final Animals animal;

    public AnimalAttackTargetGoal(Animals animal){
        this.animal = animal;
    }

    @Override
    public boolean shouldActivate() {
        return true;
    }

    @Override
    public boolean shouldStayActive() {
        return Goal.super.shouldStayActive();
    }

    @Override
    public void start() {
        Goal.super.start();
        Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @Override
    public void stop() {
        Goal.super.stop();
        HandlerList.unregisterAll(this);
    }

    @Override
    public void tick() {
        Goal.super.tick();
        if(AdvancedMonsters.getMonsterLevel().getMonsterEquipmentLevel(animal.getWorld()) >= 50.0){
            if(animal.getTarget() != null){
                if(animal.getTarget() instanceof Player){
                    if(((Player) animal.getTarget()).getGameMode().equals(GameMode.CREATIVE) || ((Player) animal.getTarget()).getGameMode().equals(GameMode.SPECTATOR)) {
                        animal.setTarget(null);
                        return;
                    }
                }
                animal.getPathfinder().moveTo(animal.getTarget());
                if(animal.hasLineOfSight(animal.getTarget())){
                    if(animal.getNearbyEntities(0.2, 0.2, 0.2).contains(animal.getTarget())){
                        if(!animal.isDead()){
                            animal.getTarget().damage(2.0, animal);
                        }
                    }
                }
            }else{
                for(Entity e : animal.getNearbyEntities(12, 12, 12)){
                    if(e instanceof Player player){
                        if(player.getGameMode().equals(GameMode.SURVIVAL) || player.getGameMode().equals(GameMode.ADVENTURE)){
                            animal.setTarget(player);
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event){
        if(!(event.getDamager() instanceof Animals)) return;
        if(!(event.getEntity() instanceof Animals)) return;
        ((Animals) event.getEntity()).setTarget((LivingEntity) event.getDamager());
        for(Entity e : event.getEntity().getNearbyEntities(16, 16, 16)){
            if(e instanceof Animals animals && e != event.getEntity()){
                animals.setTarget(((Animals) event.getEntity()).getTarget());
            }
        }
    }

    @Override
    public @NotNull GoalKey<Animals> getKey() {
        return goalKey;
    }

    @Override
    public @NotNull EnumSet<GoalType> getTypes() {
        return EnumSet.of(GoalType.TARGET);
    }
}
