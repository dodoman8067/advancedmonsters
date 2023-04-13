package kro.dodoworld.advancedmonsters.event;

import org.bukkit.World;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.world.WorldEvent;
import org.jetbrains.annotations.NotNull;

/**
 * Called when monster level is increased
 */
public class WorldMonsterLevelIncreaseEvent extends WorldEvent implements Cancellable {

    private final HandlerList handlerList = new HandlerList();
    private final World world;
    private double amount;
    private boolean cancel = false;

    /**
     * Constructor for event
     * @param world Increased world
     * @param amount Increased amount
     */
    public WorldMonsterLevelIncreaseEvent(World world, double amount){
        super(world);
        this.world = world;
        this.amount = amount;
    }

    /**
     * @return Increased world
     */
    @NotNull
    @Override
    public World getWorld() {
        return world;
    }

    /**
     * @return Increased amount
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Sets the increased amount
     * @param amount amount to set
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * @return HandlerList instance
     */
    @Override
    public @NotNull HandlerList getHandlers() {
        return handlerList;
    }

    /**
     * @return Returns true when cancelled, otherwise false
     */
    @Override
    public boolean isCancelled() {
        return cancel;
    }

    /**
     * @param cancel true if you wish to cancel this event
     */
    @Override
    public void setCancelled(boolean cancel) {
        this.cancel = cancel;
    }
}
