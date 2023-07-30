package kro.dodoworld.advancedmonsters.modifier.ability.custom;

import kro.dodoworld.advancedmonsters.AdvancedMonsters;
import kro.dodoworld.advancedmonsters.core.registry.RegisterResult;
import kro.dodoworld.advancedmonsters.modifier.ability.Ability;
import kro.dodoworld.advancedmonsters.modifier.ability.AbilityRunnable;
import kro.dodoworld.advancedmonsters.util.AbilityUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Monster;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class TankAbility extends Ability implements Listener {

    public TankAbility(@NotNull NamespacedKey id, @Nullable Component symbol, @NotNull Component name, @Nullable FileConfiguration abilityConfig, @Nullable AbilityRunnable runnable, @Nullable TextColor displayColor) {
        super(id, symbol, name, abilityConfig, runnable, displayColor);
    }

    @Override
    public @NotNull RegisterResult init() {
        if(getConfig() == null) return RegisterResult.FAIL;
        Bukkit.getPluginManager().registerEvents(this, AdvancedMonsters.getPlugin(AdvancedMonsters.class));
        return RegisterResult.SUCCESS;
    }

    @Override
    public void onSpawn(Monster monster){
        if(getConfig() == null) return;
        super.onSpawn(monster);
        AttributeModifier armor = new AttributeModifier(UUID.randomUUID(), "tank_bonus_defence", getConfig().getInt("tank_bonus_defence_amount"), AttributeModifier.Operation.ADD_NUMBER);
        monster.getAttribute(Attribute.GENERIC_ARMOR).addModifier(armor);
        monster.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(monster.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getBaseValue() * getConfig().getInt("tank_speed_multiply_amount"));
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onHit(EntityDamageByEntityEvent event){
        if(getConfig() == null) return;
        if(!(event.getEntity() instanceof Monster monster)) return;
        if(!AbilityUtils.hasAbility(monster, this)) return;
        if((Math.random() * 100) <= getConfig().getDouble("tank_ignore_damage_chance")){
            event.setCancelled(true);
            if(getConfig().getBoolean("tank_send_damage_nullify_message")){
                if(this.getSymbol() != null) event.getDamager().sendMessage(this.getSymbol().append(this.getName().append(Component.text(" 능력에 의해서 대미지가 무력화되었습니다!", NamedTextColor.RED))));
                else event.getDamager().sendMessage(this.getName().append(Component.text(" 능력에 의해서 대미지가 무력화되었습니다!", NamedTextColor.RED)));
            }
        }
    }
}
