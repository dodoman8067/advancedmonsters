package kro.dodoworld.advancedmonsters.modifier.ability.custom;

import kro.dodoworld.advancedmonsters.AdvancedMonsters;
import kro.dodoworld.advancedmonsters.core.registry.RegisterResult;
import kro.dodoworld.advancedmonsters.modifier.ability.Abilities;
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
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class StrongAbility extends Ability implements Listener {

    public StrongAbility(@NotNull NamespacedKey id, @Nullable Component symbol, @NotNull Component name, @Nullable FileConfiguration abilityConfig, @Nullable BukkitRunnable runnable, @Nullable TextColor displayColor) {
        super(id, symbol, name, abilityConfig, runnable, displayColor);
    }

    @Override
    public RegisterResult init() {
        if(getConfig() == null) return RegisterResult.FAIL;
        Bukkit.getServer().getPluginManager().registerEvents(this, AdvancedMonsters.getPlugin(AdvancedMonsters.class));
        return RegisterResult.SUCCESS;
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event){
        if(event.getDamager() instanceof Monster monster) {
            if(!AbilityUtils.hasAbility(monster, Abilities.getStrong())) return;
            if((Math.random() * 100) <= getConfig().getDouble("strong_damage_multiply_chance")){
                event.setDamage(event.getFinalDamage() * getConfig().getDouble("strong_damage_multiply_amount"));
                Bukkit.getLogger().info("a");
            }
        }
        if(event.getDamager() instanceof Projectile projectile){
            if(!(projectile.getShooter() != null && projectile.getShooter() instanceof Monster monster)) return;
            if(!AbilityUtils.hasAbility(monster, Abilities.getStrong())) return;
            if((Math.random() * 100) <= getConfig().getDouble("strong_damage_multiply_chance")){
                event.setDamage(event.getFinalDamage() * getConfig().getDouble("strong_damage_multiply_amount"));
            }
        }
    }
}
