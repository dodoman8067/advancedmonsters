package kro.dodoworld.advancedmonsters.event.ability;

import kro.dodoworld.advancedmonsters.modifier.ability.Ability;
import org.bukkit.entity.Monster;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class AbilityApplyEvent extends Event implements Cancellable {
    private boolean canceled = false;
    private Ability ability; // Applied ability
    private final Monster monster; // Applied monster
    private static final HandlerList HANDLERS = new HandlerList();

    /**
     * Fired when an ability is applied to a monster.
     * @param ability the ability
     * @param monster the monster
     */
    public AbilityApplyEvent(Ability ability, Monster monster){
        this.ability = ability;
        this.monster = monster;
    }

    public Ability getAbility() {
        return ability;
    }

    public void setAbility(Ability ability) {
        this.ability = ability;
    }

    public Monster getMonster() {
        return monster;
    }

    @Override
    public boolean isCancelled() {
        return this.canceled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.canceled = cancel;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }
}
