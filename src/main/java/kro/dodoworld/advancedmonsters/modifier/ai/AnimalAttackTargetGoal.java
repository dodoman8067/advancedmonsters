package kro.dodoworld.advancedmonsters.modifier.ai;

import com.destroystokyo.paper.entity.ai.Goal;
import com.destroystokyo.paper.entity.ai.GoalKey;
import com.destroystokyo.paper.entity.ai.GoalType;
import kro.dodoworld.advancedmonsters.AdvancedMonsters;
import org.bukkit.GameMode;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Tameable;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.EnumSet;

public class AnimalAttackTargetGoal implements Goal<Animals>{
    private final Plugin plugin = AdvancedMonsters.getPlugin(AdvancedMonsters.class);
    private final GoalKey<Animals> goalKey = GoalKey.of(Animals.class, new NamespacedKey(plugin, "animal_attack"));
    private final Animals animal;
    private final double damage;
    private final boolean shouldAttackWhileHoldingItem;

    public AnimalAttackTargetGoal(Animals animal, double damage, boolean shouldAttackWhileHoldingItem){
        this.animal = animal;
        this.damage = damage;
        this.shouldAttackWhileHoldingItem = shouldAttackWhileHoldingItem;
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
    }

    @Override
    public void stop() {
        Goal.super.stop();
    }

    @Override
    public void tick() {
        Goal.super.tick();
        if(AdvancedMonsters.getMonsterLevel().getMonsterEquipmentLevel(animal.getWorld()) <= 50.0) return;
        if(animal.getTarget() != null){
            damage(animal);
        }else{
            findTarget(animal);
        }
    }

    private boolean canTarget(LivingEntity entity, Animals animal){
        if(entity instanceof Animals) return false;
        if(entity instanceof Player player){
            if(player.getGameMode().equals(GameMode.CREATIVE) || player.getGameMode().equals(GameMode.SPECTATOR)) return false;
            if(!shouldAttackWhileHoldingItem){
                if(animal.isBreedItem(player.getInventory().getItemInMainHand()) || animal.isBreedItem(player.getInventory().getItemInOffHand())) return false;
            }
            if(animal instanceof Tameable){
                return !((Tameable) animal).isTamed();
            }
        }else{
            return entity instanceof Creature;
        }
        return true;
    }

    private void findTarget(Animals animal){
        for(Entity e : animal.getNearbyEntities(12, 12, 12)){
            if(e instanceof Player player){
                if(canTarget(player, animal)){
                    animal.setTarget(player);
                }
            }
        }
    }

    private void damage(Animals animal){
        if(animal.getTarget() == null) return;
        if(!canTarget(animal.getTarget(), animal)) return;
        if(animal.hasLineOfSight(animal.getTarget())){
            animal.getPathfinder().moveTo(animal.getTarget());
            if(animal.getNearbyEntities(0.2, 0.2, 0.2).contains(animal.getTarget())){
                if(animal.isDead()) {
                    stop();
                    return;
                }
                animal.getTarget().damage(damage, animal);
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
