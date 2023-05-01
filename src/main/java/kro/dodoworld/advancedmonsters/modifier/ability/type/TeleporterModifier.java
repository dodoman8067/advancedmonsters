package kro.dodoworld.advancedmonsters.modifier.ability.type;

import kro.dodoworld.advancedmonsters.AdvancedMonsters;
import kro.dodoworld.advancedmonsters.config.modifier.TeleportModifierConfig;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.Set;

public class TeleporterModifier {

    private static final Set<Monster> teleportingMonsters = new HashSet<>();


    public static void run(AdvancedMonsters plugin) {
        FileConfiguration config = TeleportModifierConfig.getTeleporterModifierConfig();
        double range = config.getDouble("teleport_range");

        new BukkitRunnable() {
            @Override
            public void run() {
                for (World world : Bukkit.getWorlds()) {
                    for (LivingEntity entity : world.getLivingEntities()) {
                        if (!(entity instanceof Monster monster)) {
                            continue;
                        }
                        if (!teleportingMonsters.contains(monster)) {
                            continue;
                        }
                        if (monster.isDead()) {
                            teleportingMonsters.remove(monster);
                            continue;
                        }
                        if (monster.getTarget() != null) {
                            if (monster.getLocation().distance(monster.getTarget().getLocation()) > range) {
                                monster.teleport(monster.getTarget().getLocation(), PlayerTeleportEvent.TeleportCause.PLUGIN);
                            }
                        }
                    }
                }
            }
        }.runTaskTimer(plugin, 0L, 2L);

        for (World world : Bukkit.getWorlds()) {
            for (LivingEntity entity : world.getLivingEntities()) {
                if (!(entity instanceof Monster monster)) {
                    continue;
                }
                if (monster.getScoreboardTags().contains("adm_modifier_teleporter")) {
                    teleportingMonsters.add(monster);
                }
            }
        }
    }
}
