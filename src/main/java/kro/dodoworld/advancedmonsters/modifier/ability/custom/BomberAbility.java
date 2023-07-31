package kro.dodoworld.advancedmonsters.modifier.ability.custom;

import kro.dodoworld.advancedmonsters.AdvancedMonsters;
import kro.dodoworld.advancedmonsters.core.registry.RegisterResult;
import kro.dodoworld.advancedmonsters.modifier.ability.Ability;
import kro.dodoworld.advancedmonsters.util.AbilityUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Monster;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BomberAbility extends Ability implements Listener {

    public BomberAbility(@NotNull NamespacedKey id, @Nullable Component symbol, @NotNull Component name, @Nullable FileConfiguration abilityConfig, @Nullable TextColor displayColor) {
        super(id, symbol, name, abilityConfig, displayColor);
    }

    @Override
    public @NotNull RegisterResult init() {
        if(getConfig() == null) return RegisterResult.FAIL;
        Bukkit.getPluginManager().registerEvents(this, AdvancedMonsters.getPlugin(AdvancedMonsters.class));
        return RegisterResult.SUCCESS;
    }

    @Override
    public void onSpawn(Monster monster){
        super.onSpawn(monster);
    }

    @EventHandler
    public void onKill(EntityDeathEvent event){
        if(getConfig() == null) return;
        if(!(event.getEntity() instanceof Monster monster)) return;
        if(!AbilityUtils.hasAbility(monster, this)) return;
        if((Math.random() * 100) <= getConfig().getDouble("bomber_tnt_drop_chance")){
            TNTPrimed tnt = monster.getLocation().getWorld().spawn(event.getEntity().getLocation(), TNTPrimed.class);
            tnt.setSource(monster);
            tnt.setFuseTicks(getConfig().getInt("bomber_tnt_fuse_ticks"));
        }
    }

    @EventHandler
    public void onShoot(ProjectileHitEvent event) {
        if(getConfig() == null) return;
        if (event.getEntity().getShooter() == null) return;
        if(event.getEntity().getShooter() instanceof Monster && AbilityUtils.hasAbility(((Monster)event.getEntity().getShooter()), this)) {
            if (event.getHitEntity() != null) {
                if(!((Math.random() * 100) <= getConfig().getDouble("bomber_projectile_explode_chance"))) return;
                this.createExplosion(event.getHitEntity().getLocation(), event.getEntity().getFireTicks() >= 1, (Monster)event.getEntity().getShooter());
            } else {
                if(event.getHitBlock() == null) return;
                if(!((Math.random() * 100) <= getConfig().getDouble("bomber_projectile_explode_chance"))) return;
                this.createExplosion(event.getHitBlock().getLocation(), event.getEntity().getFireTicks() >= 1, (Monster)event.getEntity().getShooter());
            }
        }
    }

    private void createExplosion(Location loc, boolean fire, Monster shooter) {
        loc.getWorld().createExplosion(loc, 4.0F, fire, false, shooter);
    }
}
