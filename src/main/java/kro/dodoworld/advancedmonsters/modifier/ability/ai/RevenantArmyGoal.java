package kro.dodoworld.advancedmonsters.modifier.ability.ai;

import com.destroystokyo.paper.entity.ai.Goal;
import com.destroystokyo.paper.entity.ai.GoalKey;
import com.destroystokyo.paper.entity.ai.GoalType;
import io.papermc.paper.event.entity.EntityMoveEvent;
import kro.dodoworld.advancedmonsters.AdvancedMonsters;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.entity.Creature;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.EnumSet;

@Deprecated
public class RevenantArmyGoal implements Listener, Goal<Creature> {
    private final Plugin plugin = AdvancedMonsters.getPlugin(AdvancedMonsters.class);
    private final GoalKey<Creature> key = GoalKey.of(Creature.class, new NamespacedKey(plugin, "revenant_army"));
    private final Creature entity;
    private final Creature summoner;
    private int i = 0;

    public RevenantArmyGoal(Creature summoner, Creature entity){
        this.entity = entity;
        this.summoner = summoner;
    }

    @Override
    public boolean shouldActivate() {
        return true;
    }

    @Override
    public boolean shouldStayActive() {
        return true;
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
        i++;
        if(i % 1000 == 0){
            i = 0;
        }
        if(summoner.getTarget() != null){
            entity.setTarget(summoner.getTarget());
            if(i % 200 == 0){
                entity.teleport(summoner.getLocation(), PlayerTeleportEvent.TeleportCause.PLUGIN);
            }
        }
        if(summoner.isDead()){
            entity.getWorld().strikeLightningEffect(entity.getLocation());
            entity.remove();
        }
    }

    @EventHandler
    public void onMove(EntityMoveEvent event){
        if(event.getEntity() != entity) return;
        if((Math.random() * 10) <= 4){
            if(event.getEntity().isDead()) return;
            event.getEntity().getWorld().spawnParticle(Particle.SOUL, event.getEntity().getLocation(), 5, 0, 0, 0, 2);
        }
    }

    @Override
    public @NotNull GoalKey<Creature> getKey() {
        return key;
    }

    @Override
    public @NotNull EnumSet<GoalType> getTypes() {
        return EnumSet.of(GoalType.LOOK);
    }
}
