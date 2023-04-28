package kro.dodoworld.advancedmonsters.event;

import kro.dodoworld.advancedmonsters.util.MonsterAbility;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * Called when ability is unlocked by killing miniboss
 */
public class MonsterAbilityUnlockEvent extends Event implements Cancellable {
    private static final HandlerList handlerList = new HandlerList();
    private MonsterAbility ability;
    private boolean cancel = false;

    /**
     * Constructor for event
     * @param ability Unlocked ability
     */
    public MonsterAbilityUnlockEvent(MonsterAbility ability){
        this.ability = ability;
    }

    /**
     * @return HandlerList instance
     */
    @Override
    public @NotNull HandlerList getHandlers() {
        return handlerList;
    }

    /**
     * @return HandlerList instance
     */
    public static HandlerList getHandlerList() {
        return handlerList;
    }

    /**
     * Gets the ability
     * @return Unlocked ability
     */
    public MonsterAbility getAbility() {
        return ability;
    }

    /**
     * Sets the ability
     * @param ability Ability to set
     */
    public void setAbility(MonsterAbility ability) {
        this.ability = ability;
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
