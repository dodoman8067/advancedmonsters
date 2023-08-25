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
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FlamingAbility extends Ability implements Listener {

    public FlamingAbility(@NotNull NamespacedKey id, @Nullable Component symbol, @NotNull Component name, @Nullable FileConfiguration abilityConfig, @Nullable TextColor displayColor) {
        super(id, symbol, name, abilityConfig, displayColor);
    }

    @Override
    public void onSpawn(Monster monster){
        super.onSpawn(monster);
    }

    @Override
    public @NotNull RegisterResult init() {
        if(getConfig() == null) return RegisterResult.FAIL;
        Bukkit.getPluginManager().registerEvents(this, AdvancedMonsters.getPlugin(AdvancedMonsters.class));
        return RegisterResult.SUCCESS;
    }

    @EventHandler
    public void onAttack(EntityDamageByEntityEvent event){
        if(!(event.getDamager() instanceof Monster monster)) return;
        if(!(event.getEntity() instanceof LivingEntity entity)) return;
        if(getConfig() == null) return;
        if(!AbilityUtils.hasAbility(monster, this)) return;
        double chance = Math.random() * 100;
        if(!(chance <= getConfig().getDouble("flaming_fire_effect_chance"))) return;
        int ticks = getConfig().getInt("flaming_fire_effect_ticks");
        entity.setFireTicks(ticks);
    }

    @EventHandler
    public void onShoot(ProjectileLaunchEvent event){
        if(event.getEntity().getShooter() == null) return;
        if(!(event.getEntity().getShooter() instanceof Monster monster)) return;
        if(getConfig() == null) return;
        if(!AbilityUtils.hasAbility(monster, this)) return;
        Arrow arrow = (Arrow) event.getEntity();
        arrow.setFireTicks(Integer.MAX_VALUE - 10000000);
    }

    @EventHandler
    public void onExplode(ExplosionPrimeEvent event){
        if(!(event.getEntity() instanceof Monster monster)) return;
        if(getConfig() == null) return;
        if(!AbilityUtils.hasAbility(monster, this)) return;
        if(!getConfig().getBoolean("flaming_set_fire_on_explode")) return;
        event.setFire(true);
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event){
        if(!(event.getEntity() instanceof Monster)) return;
        if(!(event.getCause().equals(EntityDamageEvent.DamageCause.FIRE) || event.getCause().equals(EntityDamageEvent.DamageCause.FIRE_TICK)
                || event.getCause().equals(EntityDamageEvent.DamageCause.LAVA))) return;
        if(!event.getEntity().getScoreboardTags().contains("adm_modifier_flaming")) return;
        event.setCancelled(true);
    }
}
