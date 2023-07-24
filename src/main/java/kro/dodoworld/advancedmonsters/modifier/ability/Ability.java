package kro.dodoworld.advancedmonsters.modifier.ability;

import kro.dodoworld.advancedmonsters.AdvancedMonsters;
import kro.dodoworld.advancedmonsters.core.registry.RegisterResult;
import kro.dodoworld.advancedmonsters.core.registry.Registrable;
import kro.dodoworld.advancedmonsters.core.registry.Registry;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.apache.commons.lang.WordUtils;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Monster;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Monster ability class.
 */
public abstract class Ability implements Registrable {
    private final NamespacedKey id;
    private final Component symbol;
    private final Component name;
    protected final FileConfiguration abilityConfig;
    private final BukkitRunnable runnable;
    private final TextColor displayColor;

    /**
     * Constructor for this class.
     * @param id            id for ability
     * @param symbol        symbol for ability. if null, the symbol will not appear on the monster's name
     * @param name          ability's user-friendly name
     * @param abilityConfig configuration for the ability
     * @param runnable      scheduler for the ability. starts when it's registered
     * @param displayColor color used on monster's name
     */
    public Ability(@NotNull NamespacedKey id, @Nullable Component symbol, @NotNull Component name, @Nullable FileConfiguration abilityConfig, @Nullable BukkitRunnable runnable, @Nullable TextColor displayColor) {
        this.id = id;
        this.runnable = runnable;
        this.symbol = symbol;
        this.name = name;
        this.abilityConfig = abilityConfig;
        this.displayColor = displayColor;
    }

    /**
     * Returns ability's id.
     * @return ability's id
     */
    @NotNull
    @Override
    public NamespacedKey getId() {
        return id;
    }

    /**
     * Called when registered.
     */
    @Override
    public abstract RegisterResult init();

    /**
     * Called when monster spawned with this ability.
     * @param monster the monster spawned with this ability
     */
    public void onSpawn(Monster monster){
        monster.getPersistentDataContainer().set(new NamespacedKey(AdvancedMonsters.getPlugin(AdvancedMonsters.class), "ability"), PersistentDataType.STRING, this.id.asString());
        monster.setCustomNameVisible(true);
        if(this.symbol != null){
            monster.customName(this.symbol.append(this.name).append(Component.text(" ").append(toMobName(monster))));
        }
    }

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
    @Override
    public boolean isRegistered(){
        for(Registrable r : Registry.getRegisteredObjects()){
            if(!r.getId().asString().equals(this.id.asString())) continue;
            return true;
        }
        return false;
    }

    /**
     * Returns ability's scheduler.
     * @return {@link BukkitRunnable} instance, null if the ability didn't have a scheduler initialized.
     */
    @Nullable
    public final BukkitRunnable getRunnable() {
        return runnable;
    }

    /**
     * Returns monster name color.
     * @return gray if ability was initialized as null, otherwise color initialized by constructor
     */
    @NotNull
    public TextColor getDisplayColor() {
        if(displayColor == null) return NamedTextColor.GRAY;
        return displayColor;
    }

    private Component toMobName(Monster monster){
        return Component.text(WordUtils.capitalize(monster.getType().name().toLowerCase()), this.getDisplayColor());
    }
}
