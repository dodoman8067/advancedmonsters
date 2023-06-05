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
import java.util.UUID;

public class TeleporterModifier {

    private static final Set<UUID> TELEPORTING_MONSTERS = new HashSet<>();

    public static Set<UUID> getTeleportingMonsters() {
        return TELEPORTING_MONSTERS;
    }

    public static void run(AdvancedMonsters plugin) {
        FileConfiguration config = TeleportModifierConfig.getTeleporterModifierConfig();
        double range = config.getDouble("teleporter_teleport_range");

        new BukkitRunnable() {
            @Override
            public void run() {
                for (World world : Bukkit.getWorlds()) {
                    for (LivingEntity entity : world.getLivingEntities()) {
                        if (!(entity instanceof Monster monster)) {
                            continue;
                        }
                        if (!TELEPORTING_MONSTERS.contains(monster.getUniqueId())) {
                            continue;
                        }
                        if (monster.isDead()) {
                            TELEPORTING_MONSTERS.remove(monster.getUniqueId());
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
                    TELEPORTING_MONSTERS.add(monster.getUniqueId());
                }
            }
        }
    }
}
