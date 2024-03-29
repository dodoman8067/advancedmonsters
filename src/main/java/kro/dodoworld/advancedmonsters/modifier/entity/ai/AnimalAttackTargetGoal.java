package kro.dodoworld.advancedmonsters.modifier.entity.ai;

import com.destroystokyo.paper.entity.ai.Goal;
import com.destroystokyo.paper.entity.ai.GoalKey;
import com.destroystokyo.paper.entity.ai.GoalType;
import kro.dodoworld.advancedmonsters.AdvancedMonsters;
import org.bukkit.GameMode;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Tameable;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.EnumSet;
import java.util.Set;

public class AnimalAttackTargetGoal implements Goal<Animals> {
    private final Plugin plugin = AdvancedMonsters.getPlugin(AdvancedMonsters.class);
    private final GoalKey<Animals> goalKey = GoalKey.of(Animals.class, new NamespacedKey(plugin, "animal_attack"));
    private final Animals animal;
    private final double damage;
    private final boolean shouldAttackWhileHoldingItem;
    private final Set<EntityType> targets;

    public AnimalAttackTargetGoal(Animals animal, double damage, boolean shouldAttackWhileHoldingItem, Set<EntityType> targets){
        this.animal = animal;
        this.damage = damage;
        this.shouldAttackWhileHoldingItem = shouldAttackWhileHoldingItem;
        this.targets = targets;
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
        if(!animal.getScoreboardTags().contains("adm_animal_deadly")){
            if(AdvancedMonsters.getMonsterLevel().getMonsterEquipmentLevel(animal.getWorld()) <= 50.0) return;
        }
        if(animal.getTarget() != null){
            if(animal.getTarget().isDead()){
                animal.setTarget(null);
            }
            damage(animal);
        }else{
            findTarget(animal);
        }
    }

    private boolean canTarget(LivingEntity entity, Animals animal){
        if(entity instanceof Animals) return false;
        if(entity instanceof Player player){
            if(player.getGameMode().equals(GameMode.CREATIVE) || player.getGameMode().equals(GameMode.SPECTATOR)) return false;
            if(player.isDead()) return false;
            if(!shouldAttackWhileHoldingItem){
                if(animal.isBreedItem(player.getInventory().getItemInMainHand()) || animal.isBreedItem(player.getInventory().getItemInOffHand())) return false;
            }
            if(animal instanceof Tameable){
                return !((Tameable) animal).isTamed() && !entity.isDead();
            }
        }else{
            return entity instanceof Creature && !entity.isDead();
        }
        return true;
    }

    private void findTarget(Animals animal){
        for(Entity e : animal.getNearbyEntities(12, 12, 12)){
            if(!(e instanceof LivingEntity entity)) continue;
            if(!targets.contains(entity.getType())) continue;
            if(canTarget(entity, animal)){
                animal.setTarget(entity);
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
