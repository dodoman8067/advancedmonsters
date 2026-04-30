package kro.dodoworld.advancedmonsters.modifier.ability.custom;

import com.destroystokyo.paper.entity.ai.GoalKey;
import com.destroystokyo.paper.entity.ai.PaperGoal;
import com.destroystokyo.paper.entity.ai.VanillaGoal;
import kro.dodoworld.advancedmonsters.AdvancedMonsters;
import kro.dodoworld.advancedmonsters.core.registry.RegisterResult;
import kro.dodoworld.advancedmonsters.modifier.ability.Ability;
import kro.dodoworld.advancedmonsters.modifier.ability.goal.VampireGoal;
import kro.dodoworld.advancedmonsters.modifier.ability.runnable.VampireRunnable;
import kro.dodoworld.advancedmonsters.util.AbilityUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.minecraft.world.entity.ai.goal.FleeSunGoal;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Biome;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.craftbukkit.entity.CraftMonster;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class VampireAbility extends Ability implements Listener {
    private static final Set<UUID> VAMPIRE_MONSTERS = new HashSet<>();

    public VampireAbility(@NotNull NamespacedKey id, @Nullable Component symbol, @NotNull Component name, @Nullable FileConfiguration abilityConfig, @Nullable TextColor displayColor, int spawnWeight) {
        super(id, symbol, name, abilityConfig, displayColor, spawnWeight);
    }

    @Override
    public @NotNull RegisterResult init() {
        if(getConfig() == null) return RegisterResult.FAIL;
        Bukkit.getPluginManager().registerEvents(this, AdvancedMonsters.getPlugin(AdvancedMonsters.class));
        new VampireRunnable(this, 3).runTaskTimer(AdvancedMonsters.getPlugin(AdvancedMonsters.class), 0L, 10L);
        return RegisterResult.SUCCESS;
    }

    @Override
    public void onSpawn(Monster monster){
        super.onSpawn(monster);
        if(Bukkit.getMobGoals().hasGoal(monster, GoalKey.of(Mob.class, new NamespacedKey(AdvancedMonsters.getPlugin(AdvancedMonsters.class), "vampire_drain_blood")))) return;
        Bukkit.getMobGoals().addGoal(monster, 3, new VampireGoal(monster, 40, 4, 10));
        if(!Bukkit.getMobGoals().hasGoal(monster, VanillaGoal.FLEE_SUN)){
            Bukkit.getMobGoals().addGoal(monster, 0, new PaperGoal<Monster>(new FleeSunGoal(((CraftMonster) monster).getHandle(), 2)));
        }
        VAMPIRE_MONSTERS.add(monster.getUniqueId());
    }

    @EventHandler
    public void onRegen(EntityRegainHealthEvent event){
        if(!(event.getEntity() instanceof Monster monster)) return;
        if(!AbilityUtils.hasAbility(monster, this)) return;
        if(monster.getLocation().getBlock().getLightLevel() < 6){
            event.setAmount(event.getAmount() * 2);
        }else{
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDamageEntity(EntityDamageByEntityEvent event){
        if(event.getDamager() instanceof Monster monster) {
            if(!AbilityUtils.hasAbility(monster, this)) return;
            monster.heal(event.getFinalDamage() / 4, EntityRegainHealthEvent.RegainReason.MAGIC);
        }
        if(event.getDamager() instanceof Projectile projectile){
            if(!(projectile.getShooter() != null && projectile.getShooter() instanceof Monster monster)) return;
            if(!AbilityUtils.hasAbility(monster, this)) return;
            monster.heal(event.getFinalDamage() / 4, EntityRegainHealthEvent.RegainReason.MAGIC);
        }
    }

    @EventHandler
    public void onChunkLoad(ChunkLoadEvent event){
        if(getConfig() == null) return;
        for(Entity e : event.getChunk().getEntities()){
            if(!(e instanceof Monster monster)) continue;
            if(!AbilityUtils.hasAbility(monster, this)) continue;
            if(Bukkit.getMobGoals().hasGoal(monster, GoalKey.of(Mob.class, new NamespacedKey(AdvancedMonsters.getPlugin(AdvancedMonsters.class), "vampire_drain_blood")))) return;
            Bukkit.getMobGoals().addGoal(monster, 3, new VampireGoal(monster, 40, 4, 10));
        }
    }

    @Override
    public boolean canSpawn(Monster monster){
        return monster.getLocation().getBlock().getLightLevel() < 6;
    }

    @Override
    public int getSpawnWeight(Location spawnLoc) {
        if(spawnLoc.getBlock().getBiome().equals(Biome.SWAMP) || spawnLoc.getBlock().getBiome().equals(Biome.MANGROVE_SWAMP) || spawnLoc.getBlock().getBiome().equals(Biome.DARK_FOREST)){
            return 60;
        }else return 20;
    }

    public static Set<UUID> getVampireMonsters() {
        return VAMPIRE_MONSTERS;
    }
}
