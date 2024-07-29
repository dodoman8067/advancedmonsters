package kro.dodoworld.advancedmonsters.modifier.ability.custom;

import com.destroystokyo.paper.event.entity.EntityZapEvent;
import kro.dodoworld.advancedmonsters.AdvancedMonsters;
import kro.dodoworld.advancedmonsters.core.registry.RegisterResult;
import kro.dodoworld.advancedmonsters.modifier.ability.Ability;
import kro.dodoworld.advancedmonsters.modifier.ability.runnable.StormyRunnable;
import kro.dodoworld.advancedmonsters.util.AbilityUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.minecraft.world.entity.npc.Villager;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Monster;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class StormyAbility extends Ability implements Listener {
    private static final Set<UUID> STORMING_MONSTERS = new HashSet<>();

    /**
     * Constructor for this class.
     *
     * @param id            id for ability
     * @param symbol        symbol for ability. if null, the symbol will not appear on the monster's name
     * @param name          ability's user-friendly name
     * @param abilityConfig configuration for the ability
     * @param displayColor  color used on monster's name
     */
    public StormyAbility(@NotNull NamespacedKey id, @Nullable Component symbol, @NotNull Component name, @Nullable FileConfiguration abilityConfig, @Nullable TextColor displayColor) {
        super(id, symbol, name, abilityConfig, displayColor);
    }

    @Override
    public @NotNull RegisterResult init() {
        if(getConfig() == null) return RegisterResult.FAIL;
        FileConfiguration config = getConfig();
        double lightingRange = config.getDouble("stormy_lighting_range");
        double lightingDamage = config.getDouble("stormy_lighting_damage");
        int ticks = config.getInt("stormy_slow_effect_ticks");
        int amplifier = config.getInt("stormy_slow_effect_amplifier");
        int cooldown = config.getInt("stormy_lighting_cooldown");

        new StormyRunnable(this, lightingRange, lightingDamage, ticks, amplifier).runTaskTimer(AdvancedMonsters.getPlugin(AdvancedMonsters.class), 0L, cooldown);
        Bukkit.getPluginManager().registerEvents(this, AdvancedMonsters.getPlugin(AdvancedMonsters.class));
        return RegisterResult.SUCCESS;
    }

    @Override
    public void onSpawn(Monster monster){
        super.onSpawn(monster);
        STORMING_MONSTERS.add(monster.getUniqueId());
    }

    @EventHandler
    public void onConvert(EntityZapEvent event){
        if(!(event.getEntity() instanceof Villager)) return;
        if(!event.getBolt().getScoreboardTags().contains("adm_storm_summoned")) return;
        event.setCancelled(true);
    }

    @EventHandler
    public void onExplosionDamage(EntityDamageEvent event){
        if(getConfig() == null) return;
        if(!(event.getCause().equals(EntityDamageEvent.DamageCause.LIGHTNING) || event.getCause().equals(EntityDamageEvent.DamageCause.FIRE) || event.getCause().equals(EntityDamageEvent.DamageCause.FIRE_TICK))) return;
        if(!(event.getEntity() instanceof Monster monster)) return;
        if(AbilityUtils.hasAbility(monster, this)){
            event.setCancelled(true);
        }
    }

    public static Set<UUID> getStormingMonsters() {
        return STORMING_MONSTERS;
    }
}
