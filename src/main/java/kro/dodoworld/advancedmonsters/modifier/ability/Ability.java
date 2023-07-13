package kro.dodoworld.advancedmonsters.modifier.ability;

import kro.dodoworld.advancedmonsters.core.registry.Registry;
import net.kyori.adventure.text.Component;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Monster ability class.
 */
public abstract class Ability {
    private final NamespacedKey id;
    private final Component symbol;
    private final Component name;
    protected final FileConfiguration abilityConfig;
    private final BukkitRunnable runnable;

    /**
     * Constructor for this class.
     * @param id id for ability
     * @param symbol symbol for ability. if null, the symbol will not appear on the monster's name
     * @param name ability's user-friendly name
     * @param abilityConfig configuration for the ability
     * @param runnable scheduler for the ability. starts when it's registered
     */
    public Ability(@NotNull NamespacedKey id, @Nullable Component symbol, @NotNull Component name, @Nullable FileConfiguration abilityConfig, @Nullable BukkitRunnable runnable) {
        this.id = id;
        this.runnable = runnable;
        this.symbol = symbol;
        this.name = name;
        this.abilityConfig = abilityConfig;
    }

    /**
     * Returns ability's id.
     * @return ability's id
     */
    @NotNull
    public NamespacedKey getId() {
        return id;
    }

    /**
     * Called when registered.
     */
    public void init(){}

    /**
     * Returns the config.
     * @return config instance, null if the ability didn't have config initialized.
     */
    @Nullable
    public FileConfiguration getConfig(){
        return abilityConfig;
    }

    /**
     * Returns the symbol for this ability.
     * @return symbol component. null if the ability didn't have the symbol initialized.
     */
    @Nullable
    public final Component getSymbol() {
        return symbol;
    }

    /**
     * Returns the user-friendly name of this ability.
     * @return the name
     */
    @NotNull
    public final Component getName() {
        return name;
    }

    /**
     * Checks this ability is registered.
     * @return true if ability is registered, otherwise false.
     */
    public boolean isRegistered(){
        for(Ability a : Registry.getRegisteredAbilities()){
            return a.getId().asString().equals(this.id.asString());
        }
        return false;
    }

    /**
     * Returns ability's scheduler.
     * @return {@link BukkitRunnable} instance, null if the ability didn't have scheduler initialized.
     */
    @Nullable
    public BukkitRunnable getRunnable() {
        return runnable;
    }
}
