package kro.dodoworld.advancedmonsters.modifier.ability.custom;

import kro.dodoworld.advancedmonsters.AdvancedMonsters;
import kro.dodoworld.advancedmonsters.core.registry.RegisterResult;
import kro.dodoworld.advancedmonsters.modifier.ability.Ability;
import kro.dodoworld.advancedmonsters.util.AbilityUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Monster;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SplitterAbility extends Ability implements Listener {
    public SplitterAbility(@NotNull NamespacedKey id, @Nullable Component symbol, @NotNull Component name, @Nullable FileConfiguration abilityConfig, @Nullable TextColor displayColor, int spawnWeight) {
        super(id, symbol, name, abilityConfig, displayColor, spawnWeight);
    }

    @Override
    public @NotNull RegisterResult init() {
        if(getConfig() == null) return RegisterResult.FAIL;
        Bukkit.getPluginManager().registerEvents(this, AdvancedMonsters.getPlugin(AdvancedMonsters.class));
        return RegisterResult.SUCCESS;
    }

    @EventHandler
    public void onDeath(EntityDeathEvent event){
        if(event.isCancelled()) return;
        if(event.getEntity() instanceof Monster monster){
            if(!AbilityUtils.hasAbility(monster, this)) return;
            event.setShouldPlayDeathSound(false);
            split(monster);
        }
    }

    private void split(Monster monster){
        if(getConfig() == null) return;
        AttributeInstance attribute = monster.getAttribute(Attribute.GENERIC_SCALE);
        if(attribute == null) return;
        double size = attribute.getValue() / 2;
        if(size < getConfig().getDouble("splitter_minimum_size")) return;

        for(int i = 0; i<2; i++){
            Monster monster1 = (Monster) monster.getWorld().spawnEntity(monster.getLocation(), monster.getType());
            AttributeInstance instance = monster1.getAttribute(Attribute.GENERIC_SCALE);
            if(instance == null) continue;
            instance.setBaseValue(size);
            AttributeInstance instance1 = monster1.getAttribute(Attribute.GENERIC_MAX_HEALTH);
            if(instance1 == null) continue;
            instance1.setBaseValue(instance1.getValue() / 2);
            monster1.setHealth(instance1.getBaseValue());
        }
    }

    @Override
    public boolean canSpawn(Monster monster){
        if(getConfig() == null) return false;
        AttributeInstance attribute = monster.getAttribute(Attribute.GENERIC_SCALE);
        if(attribute == null) return false;

        return attribute.getValue() >= getConfig().getDouble("splitter_minimum_size");
    }
}
