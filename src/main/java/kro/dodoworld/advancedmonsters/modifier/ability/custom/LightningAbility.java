package kro.dodoworld.advancedmonsters.modifier.ability.custom;

import kro.dodoworld.advancedmonsters.AdvancedMonsters;
import kro.dodoworld.advancedmonsters.core.registry.RegisterResult;
import kro.dodoworld.advancedmonsters.modifier.ability.Ability;
import kro.dodoworld.advancedmonsters.util.AbilityUtils;
import org.bukkit.entity.LivingEntity;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.ThreadLocalRandom;

public class LightningAbility extends Ability implements Listener {
    /**
     * Constructor for this class.
     *
     * @param id            id for ability
     * @param symbol        symbol for ability. if null, the symbol will not appear on the monster's name
     * @param name          ability's user-friendly name
     * @param abilityConfig configuration for the ability
     * @param displayColor  color used on monster's name
     */
    public LightningAbility(@NotNull NamespacedKey id, @Nullable Component symbol, @NotNull Component name, @Nullable FileConfiguration abilityConfig, @Nullable TextColor displayColor) {
        super(id, symbol, name, abilityConfig, displayColor);
    }

    @Override
    public @NotNull RegisterResult init() {
        if(getConfig() == null) return RegisterResult.FAIL;
        Bukkit.getPluginManager().registerEvents(this, AdvancedMonsters.getPlugin(AdvancedMonsters.class));
        return RegisterResult.SUCCESS;
    }
    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event){
        if(getConfig() == null) return;
        if(!(event.getDamager() instanceof Monster monster)) return;
        if(!(event.getEntity() instanceof LivingEntity)) return;
        if(!AbilityUtils.hasAbility(monster, this)) return;

        FileConfiguration config = getConfig();
        double chance = config.getDouble("lighting_strike_chance");
        int maxAmount = config.getInt("lighting_max_lighting_strike_amount");
        double damageAmount = config.getDouble("lighting_damage_amount");

        if((Math.random() * 100) <= chance){
            int amount = ThreadLocalRandom.current().nextInt(0, maxAmount + 1);
            for(int i = 0; i<=amount; i++){
                event.getEntity().getWorld().strikeLightningEffect(event.getEntity().getLocation());
                ((LivingEntity) event.getEntity()).damage(damageAmount);
            }
        }
    }

    @EventHandler
    public void onArrowDamage(EntityDamageByEntityEvent event){
        if(getConfig() == null) return;
        if(!(event.getDamager() instanceof Projectile)) return;
        if(((Projectile) event.getDamager()).getShooter() == null) return;
        if(!(((Projectile) event.getDamager()).getShooter() instanceof Monster monster)) return;
        if(!(event.getEntity() instanceof LivingEntity)) return;
        if(!AbilityUtils.hasAbility(monster, this)) return;

        FileConfiguration config = getConfig();
        double chance = config.getDouble("lighting_strike_chance");
        int maxAmount = config.getInt("lighting_max_lighting_strike_amount");
        double damageAmount = config.getDouble("lighting_damage_amount");

        if((Math.random() * 100) <= chance){
            int amount = ThreadLocalRandom.current().nextInt(0, maxAmount + 1);
            for(int i = 0; i<=amount; i++){
                event.getEntity().getWorld().strikeLightningEffect(event.getEntity().getLocation());
                ((LivingEntity) event.getEntity()).damage(damageAmount);
            }
        }
    }
}
