package kro.dodoworld.advancedmonsters.modifier.ability.custom;

import kro.dodoworld.advancedmonsters.AdvancedMonsters;
import kro.dodoworld.advancedmonsters.core.registry.RegisterResult;
import kro.dodoworld.advancedmonsters.modifier.ability.Ability;
import kro.dodoworld.advancedmonsters.util.AbilityUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PunchyAbility extends Ability implements Listener {
    /**
     * Constructor for this class.
     *
     * @param id            id for ability
     * @param symbol        symbol for ability. if null, the symbol will not appear on the monster's name
     * @param name          ability's user-friendly name
     * @param abilityConfig configuration for the ability
     * @param displayColor  color used on monster's name
     */
    public PunchyAbility(@NotNull NamespacedKey id, @Nullable Component symbol, @NotNull Component name, @Nullable FileConfiguration abilityConfig, @Nullable TextColor displayColor) {
        super(id, symbol, name, abilityConfig, displayColor);
    }

    @Override
    public @NotNull RegisterResult init() {
        if(getConfig() == null) return RegisterResult.FAIL;
        Bukkit.getPluginManager().registerEvents(this, AdvancedMonsters.getPlugin(AdvancedMonsters.class));
        return RegisterResult.SUCCESS;
    }

    @EventHandler
    public void onHit(EntityDamageByEntityEvent event){
        if(getConfig() == null) return;
        if(event.getDamager() instanceof Monster && event.getEntity() instanceof LivingEntity){
            if(!AbilityUtils.hasAbility(((Monster) event.getDamager()), this)) return;
            double rnd = Math.random() * 100;
            if(rnd <= getConfig().getDouble("punchy_punch_air_chance")){
                event.setCancelled(true);
                LivingEntity player = (LivingEntity) event.getEntity();
                Bukkit.getScheduler().scheduleSyncDelayedTask(AdvancedMonsters.getPlugin(AdvancedMonsters.class), () -> player.setVelocity(new Vector(0,2,0)), 1L);
                if(player instanceof Player && getConfig().getBoolean("punchy_show_punch_air_message")){
                    if(this.getSymbol() != null) player.sendMessage(this.getSymbol().append(this.getName().append(Component.text(" 능력으로 인해 하늘로 날려졌습니다!", NamedTextColor.RED))));
                    else player.sendMessage(this.getName().append(Component.text(" 능력으로 인해 하늘로 날려졌습니다!", NamedTextColor.RED)));
                }
            }
        }
        if(event.getDamager() instanceof Projectile && ((Projectile) event.getDamager()).getShooter() instanceof Monster){
            if(!AbilityUtils.hasAbility((((Monster) ((Projectile) event.getDamager()).getShooter())), this)) return;
            double rnd = Math.random() * 100;
            if(rnd <= getConfig().getDouble("punchy_punch_air_chance")) {
                event.setCancelled(true);
                LivingEntity player = (LivingEntity) event.getEntity();
                Bukkit.getScheduler().scheduleSyncDelayedTask(AdvancedMonsters.getPlugin(AdvancedMonsters.class), () -> player.setVelocity(new Vector(0, 2, 0)), 1L);
                if(player instanceof Player && getConfig().getBoolean("punchy_show_punch_air_message")){
                    if(this.getSymbol() != null) player.sendMessage(this.getSymbol().append(this.getName().append(Component.text(" 능력으로 인해 하늘로 날려졌습니다!", NamedTextColor.RED))));
                    else player.sendMessage(this.getName().append(Component.text(" 능력으로 인해 하늘로 날려졌습니다!", NamedTextColor.RED)));
                }
            }
        }
    }
}
