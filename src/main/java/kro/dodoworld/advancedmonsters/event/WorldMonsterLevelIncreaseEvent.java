package kro.dodoworld.advancedmonsters.event;

import org.bukkit.World;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.world.WorldEvent;
import org.jetbrains.annotations.NotNull;

public class WorldMonsterLevelIncreaseEvent extends WorldEvent implements Cancellable {


    private final HandlerList handlerList = new HandlerList();
    private final World world;
    private double amount;
    private boolean cancel = false;


    public WorldMonsterLevelIncreaseEvent(World world, double amount){
        super(world);
        this.world = world;
        this.amount = amount;
    }

    @NotNull
    @Override
    public World getWorld() {
        return world;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlerList;
    }

    @Override
    public boolean isCancelled() {
        return cancel;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancel = cancel;
    }
}
