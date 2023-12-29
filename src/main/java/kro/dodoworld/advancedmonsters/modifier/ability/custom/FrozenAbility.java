package kro.dodoworld.advancedmonsters.modifier.ability.custom;

import kro.dodoworld.advancedmonsters.AdvancedMonsters;
import kro.dodoworld.advancedmonsters.core.registry.RegisterResult;
import kro.dodoworld.advancedmonsters.modifier.ability.Ability;
import kro.dodoworld.advancedmonsters.util.AbilityUtils;
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

public class FrozenAbility extends Ability implements Listener {
    /**
     * Constructor for this class.
     *
     * @param id            id for ability
     * @param symbol        symbol for ability. if null, the symbol will not appear on the monster's name
     * @param name          ability's user-friendly name
     * @param abilityConfig configuration for the ability
     * @param displayColor  color used on monster's name
     */
    public FrozenAbility(@NotNull NamespacedKey id, @Nullable Component symbol, @NotNull Component name, @Nullable FileConfiguration abilityConfig, @Nullable TextColor displayColor) {
        super(id, symbol, name, abilityConfig, displayColor);
    }

    @Override
    public @NotNull RegisterResult init() {
        if(getConfig() == null) return RegisterResult.FAIL;
        Bukkit.getPluginManager().registerEvents(this, AdvancedMonsters.getPlugin(AdvancedMonsters.class));
        return RegisterResult.SUCCESS;
    }

    @EventHandler
    public void onAttack(EntityDamageByEntityEvent event){
        if(getConfig() == null) return;
        if(!(event.getDamager() instanceof Monster monster)) return;
        if(!AbilityUtils.hasAbility(monster, this)) return;
        FileConfiguration config = getConfig();
        if((Math.random() * 100) <= config.getDouble("frozen_freeze_effect_chance")){
            event.getEntity().setFreezeTicks(config.getInt("frozen_freeze_effect_ticks"));
        }
    }

    @EventHandler
    public void onArrowDamage(EntityDamageByEntityEvent event){
        if(getConfig() == null) return;
        if(event.getDamager() instanceof Projectile projectile && ((Projectile) event.getDamager()).getShooter() != null && ((Projectile) event.getDamager()).getShooter() instanceof Monster){
            Monster monster = (Monster) projectile.getShooter();
            if(!AbilityUtils.hasAbility(monster, this)) return;
            FileConfiguration config = getConfig();
            if((Math.random() * 100) <= config.getDouble("frozen_freeze_effect_chance")){
                event.getEntity().setFreezeTicks(config.getInt("frozen_freeze_effect_ticks"));
            }
        }
    }

    @Override
    public boolean canSpawn(Monster monster){
        if(getConfig() == null) return false;
        if(monster.getWorld().getName().endsWith("_nether")){
            return getConfig().getBoolean("frozen_can_spawn_on_nether");
        }else{
            return true;
        }
    }
}
