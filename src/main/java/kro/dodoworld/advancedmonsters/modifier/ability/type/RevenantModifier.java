package kro.dodoworld.advancedmonsters.modifier.ability.type;

import kro.dodoworld.advancedmonsters.AdvancedMonsters;
import kro.dodoworld.advancedmonsters.config.modifier.RevenantModifierConfig;
import kro.dodoworld.advancedmonsters.modifier.ability.ai.RevenantArmyGoal;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class RevenantModifier {
    private static final Set<UUID> REVENANT_MONSTERS = new HashSet<>();

    public static Set<UUID> getRevenantMonsters() {
        return REVENANT_MONSTERS;
    }

    public static void run(AdvancedMonsters plugin){
        FileConfiguration config = RevenantModifierConfig.getRevenantModifierConfig();
        double range = config.getDouble("monster_revive_range");
        new BukkitRunnable(){

            @Override
            public void run() {
                for (World world : Bukkit.getWorlds()) {
                    for (LivingEntity entity : world.getLivingEntities()) {
                        if (!(entity instanceof Monster monster)) {
                            continue;
                        }
                        if (!REVENANT_MONSTERS.contains(monster.getUniqueId())) {
                            continue;
                        }
                        if (monster.isDead()) {
                            REVENANT_MONSTERS.remove(monster.getUniqueId());
                            continue;
                        }

                        for(Entity entity1 : monster.getNearbyEntities(range, range, range)){
                            if(!(entity1 instanceof Monster revenantArmy)) continue;
                            if(entity1.getScoreboardTags().contains("adm_revenant_revived")) continue;
                            if(!revenantArmy.isDead()) continue;
                            if((Math.random() * 100) <= config.getDouble("monster_revive_chance")){
                                revenantArmy.getWorld().strikeLightningEffect(revenantArmy.getLocation());
                                Monster army = revenantArmy.getWorld().spawn(revenantArmy.getLocation(), revenantArmy.getClass(), CreatureSpawnEvent.SpawnReason.CUSTOM);
                                army.addScoreboardTag("adm_revenant_revived");
                                Bukkit.getMobGoals().addGoal(army, 4, new RevenantArmyGoal(monster, army));
                                army.customName(Component.text("Revived ", TextColor.color(149, 19, 214), TextDecoration.BOLD).append(Component.text(toMobName(army.getType().name()))));
                            }
                        }
                    }
                }
            }
        }.runTaskTimer(plugin, 0L, 1L);

        for (World world : Bukkit.getWorlds()) {
            for (LivingEntity entity : world.getLivingEntities()) {
                if (!(entity instanceof Monster monster)) {
                    continue;
                }
                if (monster.getScoreboardTags().contains("adm_modifier_revenant")) {
                    REVENANT_MONSTERS.add(monster.getUniqueId());
                }
            }
        }
    }

    private static String toMobName(String typeName){
        return ChatColor.GRAY + WordUtils.capitalize(typeName.toLowerCase().replace("_", " "));
    }
}
