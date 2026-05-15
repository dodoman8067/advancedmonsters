package kro.dodoworld.advancedmonsters.modifier.ability.custom;

import kro.dodoworld.advancedmonsters.AdvancedMonsters;
import kro.dodoworld.advancedmonsters.core.registry.RegisterResult;
import kro.dodoworld.advancedmonsters.modifier.ability.Ability;
import kro.dodoworld.advancedmonsters.util.AbilityUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.SpawnCategory;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class DuplexAbility extends Ability implements Listener {
    private static HashMap<UUID, Long> cooldown;

    public DuplexAbility(@NotNull NamespacedKey id, @Nullable Component symbol, @NotNull Component name, @Nullable FileConfiguration abilityConfig, @Nullable TextColor displayColor, int spawnWeight) {
        super(id, symbol, name, abilityConfig, displayColor, spawnWeight);
    }

    @Override
    public @NotNull RegisterResult init() {
        if(getConfig() == null) return RegisterResult.FAIL;
        cooldown = new HashMap<>();
        Bukkit.getPluginManager().registerEvents(this, AdvancedMonsters.getPlugin(AdvancedMonsters.class));
        return RegisterResult.SUCCESS;
    }

    @EventHandler
    public void onShoot(ProjectileLaunchEvent event){
        if(event.getEntity().getScoreboardTags().contains("adm_duplex_arrow")) return;
        if(!(event.getEntity().getShooter() instanceof Monster monster)) return;
        if(!AbilityUtils.hasAbility(monster, this)) return;
        if(cooldown.get(monster.getUniqueId()) == null || cooldown.get(monster.getUniqueId()) < System.currentTimeMillis()){
            new BukkitRunnable(){
                int timer = ThreadLocalRandom.current().nextInt(1, getConfig().getInt("duplex_arrow_max"));
                @Override
                public void run() {
                    Location eye = monster.getEyeLocation();
                    Location loc = eye.add(eye.getDirection().multiply(1.2));
                    Projectile projectile = monster.launchProjectile(event.getEntity().getClass());
                    projectile.setVelocity(loc.getDirection().normalize().multiply(2));
                    projectile.addScoreboardTag("adm_duplex_arrow");
                    projectile.setShooter(monster);
                    timer--;
                    if(timer == 0 && monster.isDead()){
                        cancel();
                    }
                }
            }.runTaskTimer(AdvancedMonsters.getPlugin(AdvancedMonsters.class), 0L, 4L);
            cooldown.put(monster.getUniqueId(), System.currentTimeMillis() + getConfig().getInt("duplex_cooldown_seconds") * 1000);
        }
    }

    @EventHandler
    public void onHit(ProjectileHitEvent event){
        if(!event.getEntity().getScoreboardTags().contains("adm_duplex_arrow")) return;
        if(event.getHitEntity() == null) return;
        if(event.getHitEntity().getSpawnCategory().equals(SpawnCategory.MONSTER)){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event){
        if(!(event.getDamager() instanceof Projectile projectile)) return;
        if(!projectile.getScoreboardTags().contains("adm_duplex_arrow")){
            event.setDamage(event.getFinalDamage() / 2);
        }
    }

    @Override
    public boolean canSpawn(Monster monster) {
        return (monster.getEquipment().getItemInMainHand().equals(ItemStack.of(Material.BOW)) || monster.getEquipment().getItemInMainHand().equals(ItemStack.of(Material.CROSSBOW)) || monster.getEquipment().getItemInMainHand().equals(ItemStack.of(Material.TRIDENT)));
    }
}
