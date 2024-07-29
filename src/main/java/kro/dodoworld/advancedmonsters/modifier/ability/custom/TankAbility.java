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
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TankAbility extends Ability implements Listener {

    //TODO : Add tank diversion exceptions
    //currently it has a problem of taking damage when the mob is immune to the damage
    //what i am planning to do is make a list of pairs(ability type, list of damage causes) and if some ability-ized monster got damaged with cause in the list tank won't take the damage
    //example : flaming with lava, fire and fire tick

    public TankAbility(@NotNull NamespacedKey id, @Nullable Component symbol, @NotNull Component name, @Nullable FileConfiguration abilityConfig, @Nullable TextColor displayColor) {
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
        if(getConfig() == null) return;
        super.onSpawn(monster);
        AttributeModifier armor = new AttributeModifier(UUID.randomUUID(), "tank_bonus_defence", getConfig().getInt("tank_bonus_defence_amount"), AttributeModifier.Operation.ADD_NUMBER);
        monster.getAttribute(Attribute.GENERIC_ARMOR).addModifier(armor);
        monster.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(monster.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getBaseValue() * getConfig().getInt("tank_speed_multiply_amount"));
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onHit(EntityDamageByEntityEvent event) {
        if (getConfig() == null) return;
        if (!(event.getEntity() instanceof Monster monster)) return;
        if (!AbilityUtils.hasAbility(monster, this)) return;
        if ((Math.random() * 100) <= getConfig().getDouble("tank_ignore_damage_chance")) {
            event.setCancelled(true);
            if (getConfig().getBoolean("tank_send_damage_nullify_message")) {
                if (this.getSymbol() != null)
                    event.getDamager().sendMessage(this.getSymbol().append(this.getName().append(Component.text(" 능력에 의해서 대미지가 무력화되었습니다!", NamedTextColor.RED))));
                else
                    event.getDamager().sendMessage(this.getName().append(Component.text(" 능력에 의해서 대미지가 무력화되었습니다!", NamedTextColor.RED)));
            }
        }
    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    public void onDamage(EntityDamageEvent event){
        if(getConfig() == null) return;
        if(!(event.getEntity() instanceof Monster monster)) return;
        if(AbilityUtils.hasAbility(monster, this)) return;
        List<Monster> tanks = new ArrayList<>();

        double range = getConfig().getDouble("tank_monster_damage_protect_range");
        for(Entity e : monster.getNearbyEntities(range, 7, range)){
            if(!(e instanceof Monster monster1)) continue;
            if(AbilityUtils.hasAbility(monster1, this)){
                if((Math.random() * 100) <= getConfig().getDouble("tank_monster_damage_protect_chance")){
                    tanks.add(monster1);
                }
                /*
                double amount = event.getFinalDamage() * getConfig().getDouble("tank_monster_damage_protect_amount");
                if(amount <= 0) event.setCancelled(true);
                else{
                    event.setDamage(event.getFinalDamage() - amount);
                    monster1.damage(amount, monster1);
                }
                return;
                */
            }
        }

        double amount = event.getFinalDamage() * getConfig().getDouble("tank_monster_damage_protect_amount");
        if(amount <= 0) event.setCancelled(true);
        else{
            double tankDmgAmount = amount / tanks.size();
            event.setDamage(event.getFinalDamage() - amount);
            for(Monster m : tanks){
                m.damage(tankDmgAmount);
            }
        }
    }
}
