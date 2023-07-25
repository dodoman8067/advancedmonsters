package kro.dodoworld.advancedmonsters.system.entity.miniboss;

import kro.dodoworld.advancedmonsters.AdvancedMonsters;
import kro.dodoworld.advancedmonsters.core.registry.RegisterResult;
import kro.dodoworld.advancedmonsters.core.registry.Registrable;
import kro.dodoworld.advancedmonsters.core.registry.Registry;
import kro.dodoworld.advancedmonsters.modifier.ability.Ability;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Creature;
import org.bukkit.entity.EntityType;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents miniboss class for plugin.
 */
public abstract class MiniBoss implements Registrable {

    private final NamespacedKey id;
    private final Component name;
    private final Ability unlockAbility;
    private final BukkitRunnable entityTask;
    private final FileConfiguration config;
    private final EntityType requiredType;

    /**
     * Constructor for this class.
     *
     * @param id             miniboss's id
     * @param name           miniboss's user-friendly name
     * @param unlockAbility  ability that is going to be unlocked when killed
     * @param entityTask     {@link BukkitRunnable} instance per entity
     * @param config         {@link FileConfiguration} instance
     * @param requiredType   entity type that world attempts to convert this entity into miniboss
     */
    public MiniBoss(@NotNull NamespacedKey id, @NotNull Component name, @Nullable Ability unlockAbility, @Nullable BukkitRunnable entityTask, @Nullable FileConfiguration config, @NotNull EntityType requiredType){
        this.id = id;
        this.name = Component.text("⚛MINIBOSS ").color(TextColor.color(219, 42, 216)).decorate(TextDecoration.BOLD).append(name);
        this.unlockAbility = unlockAbility;
        this.entityTask = entityTask;
        this.config = config;
        this.requiredType = requiredType;
    }

    @Override
    public @NotNull RegisterResult init() {
        return RegisterResult.SUCCESS;
    }

    @Override
    public boolean isRegistered() {
        return Registry.getRegisteredObjects().contains(this);
    }

    @Override
    @NotNull
    public NamespacedKey getId() {
        return id;
    }

    /**
     * Called when miniboss spawned.
     * @param miniboss the spawned miniboss
     */
    public void onSpawn(Creature miniboss){
        miniboss.getPersistentDataContainer().set(new NamespacedKey(AdvancedMonsters.getPlugin(AdvancedMonsters.class), "miniboss"), PersistentDataType.STRING, this.id.asString());
        miniboss.setCustomNameVisible(true);
        miniboss.customName(this.name);
        miniboss.setRemoveWhenFarAway(false);
        initAttributes(miniboss);
        initEquipments(miniboss);
    }

    /**
     * Method for equipment-related tasks.
     * This method exists to organize spawn related tasks.
     * @param miniboss the miniboss
     */
    protected void initEquipments(Creature miniboss){}

    /**
     * Method for attribute-related tasks.
     * This method exists to organize spawn related tasks.
     * @param miniboss the miniboss
     */
    protected void initAttributes(Creature miniboss){}

    /**
     * Returns miniboss's user-friendly name.
     * @return the name
     */
    @NotNull
    public Component getName() {
        return name;
    }

    /**
     * Returns ability that is going to be unlocked when killed.
     * @return the ability, null if this instance was constructed with null value
     */
    @Nullable
    public Ability getUnlockAbility() {
        return unlockAbility;
    }

    /**
     * Returns {@link BukkitRunnable} instance that runs per entity.
     * @return the instance, null if this instance was constructed with null value
     */
    @Nullable
    public BukkitRunnable getEntityTask() {
        return entityTask;
    }

    /**
     * Returns {@link FileConfiguration} instance used by miniboss.
     * @return the config instance, null if this instance was constructed with null value
     */
    @Nullable
    public FileConfiguration getConfig() {
        return config;
    }

    /**
     * Returns entity type that world attempts to convert this entity into miniboss.
     * @return the entity
     */
    @NotNull
    public EntityType getRequiredType() {
        return requiredType;
    }
}
