package kro.dodoworld.advancedmonsters.modifier.ability.custom;

import kro.dodoworld.advancedmonsters.AdvancedMonsters;
import kro.dodoworld.advancedmonsters.core.registry.RegisterResult;
import kro.dodoworld.advancedmonsters.modifier.ability.Ability;
import kro.dodoworld.advancedmonsters.util.AbilityUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Biome;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.AbstractSkeleton;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AimingAbility extends Ability implements Listener {
    public AimingAbility(@NotNull NamespacedKey id, @Nullable Component symbol, @NotNull Component name, @Nullable FileConfiguration abilityConfig, @Nullable TextColor displayColor, int spawnWeight) {
        super(id, symbol, name, abilityConfig, displayColor, spawnWeight);
    }

    @EventHandler
    public void onShoot(ProjectileLaunchEvent event){
        if(!(event.getEntity() instanceof AbstractSkeleton skeleton)) return;
        if(!AbilityUtils.hasAbility(skeleton, this)) return;

        Projectile projectile = event.getEntity();
        new BukkitRunnable(){

            @Override
            public void run(){
                if(projectile.isOnGround() || projectile.isDead() || !projectile.isValid() || skeleton.isDead() || !skeleton.isValid()){
                    cancel();
                }else{
                    for(Entity target : projectile.getNearbyEntities(5, 5, 5)){
                        if(skeleton.getTarget() != null && skeleton.getTarget() == target && skeleton.hasLineOfSight(target) && target.isValid() && target != skeleton){
                            projectile.setVelocity(target.getLocation().toVector().subtract(projectile.getLocation().toVector()));
                        }
                    }
                }
            }
        }.runTaskTimer(AdvancedMonsters.getPlugin(AdvancedMonsters.class), 0L, 4L);
    }

    @Override
    public @NotNull RegisterResult init() {
        if(getConfig() == null) return RegisterResult.FAIL;
        Bukkit.getPluginManager().registerEvents(this, AdvancedMonsters.getPlugin(AdvancedMonsters.class));
        return RegisterResult.SUCCESS;
    }

    @Override
    public boolean canSpawn(Monster monster){
        return monster instanceof AbstractSkeleton && (monster.getLocation().getBlock().getBiome().equals(Biome.SWAMP) || monster.getLocation().getBlock().getBiome().equals(Biome.MANGROVE_SWAMP));
    }
}
