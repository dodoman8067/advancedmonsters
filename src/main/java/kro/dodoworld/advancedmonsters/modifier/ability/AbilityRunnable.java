package kro.dodoworld.advancedmonsters.modifier.ability;

import org.bukkit.scheduler.BukkitRunnable;

public class AbilityRunnable extends BukkitRunnable {

    private final Ability ability;

    public AbilityRunnable(Ability ability){
        this.ability = ability;
    }

    protected Ability getAbility() {
        return ability;
    }

    @Override
    public void run() {

    }
}
