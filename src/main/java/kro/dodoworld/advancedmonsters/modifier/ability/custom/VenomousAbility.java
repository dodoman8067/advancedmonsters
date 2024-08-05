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
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class VenomousAbility extends Ability implements Listener {
    /**
     * Constructor for this class.
     *
     * @param id            id for ability
     * @param symbol        symbol for ability. if null, the symbol will not appear on the monster's name
     * @param name          ability's user-friendly name
     * @param abilityConfig configuration for the ability
     * @param displayColor  color used on monster's name
     */
    public VenomousAbility(@NotNull NamespacedKey id, @Nullable Component symbol, @NotNull Component name, @Nullable FileConfiguration abilityConfig, @Nullable TextColor displayColor) {
        super(id, symbol, name, abilityConfig, displayColor);
    }

    @EventHandler
    public void onHit(EntityDamageByEntityEvent event){
        if(getConfig() == null) return;
        FileConfiguration config = getConfig();

        int poisonTicks = config.getInt("venomous_poison_effect_ticks");
        int poisonAmplifier = config.getInt("venomous_poison_effect_amplifier");
        int weaknessTicks = config.getInt("venomous_weakness_effect_ticks");
        int weaknessAmplifier = config.getInt("venomous_weakness_effect_amplifier");

        if(event.getDamager() instanceof Monster monster && event.getEntity() instanceof LivingEntity entity){
            if(!AbilityUtils.hasAbility(monster, this)) return;
            if(!(Math.random() * 100 <= config.getDouble("venomous_apply_effect_chance"))) return;

            entity.addPotionEffect(new PotionEffect(PotionEffectType.POISON,  poisonTicks, poisonAmplifier, true, true, true));
            entity.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, weaknessTicks, weaknessAmplifier, true, true, true));
        }
        if(event.getDamager() instanceof Projectile && ((Projectile) event.getDamager()).getShooter() instanceof Monster monster && event.getEntity() instanceof LivingEntity entity && ((Projectile) event.getDamager()).getShooter() != null){
            if(!AbilityUtils.hasAbility(monster, this)) return;
            if(!(Math.random() * 100 <= config.getDouble("venomous_apply_effect_chance"))) return;

            entity.addPotionEffect(new PotionEffect(PotionEffectType.POISON,  poisonTicks, poisonAmplifier, true, true, true));
            entity.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, weaknessTicks, weaknessAmplifier, true, true, true));
        }
    }

    @Override
    public @NotNull RegisterResult init() {
        if(getConfig() == null) return RegisterResult.FAIL;
        Bukkit.getPluginManager().registerEvents(this, AdvancedMonsters.getPlugin(AdvancedMonsters.class));
        return RegisterResult.SUCCESS;
    }
}
