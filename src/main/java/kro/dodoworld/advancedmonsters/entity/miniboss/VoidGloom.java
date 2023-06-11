package kro.dodoworld.advancedmonsters.entity.miniboss;

import kro.dodoworld.advancedmonsters.AdvancedMonsters;
import kro.dodoworld.advancedmonsters.event.MonsterAbilityUnlockEvent;
import kro.dodoworld.advancedmonsters.modifier.ability.type.LaserModifier;
import kro.dodoworld.advancedmonsters.util.AdvancedUtils;
import kro.dodoworld.advancedmonsters.util.MonsterAbility;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Entity;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class VoidGloom implements Listener {
    public static void createVoidGloom(Location loc){
        Enderman enderman = loc.getWorld().spawn(loc, Enderman.class, CreatureSpawnEvent.SpawnReason.CUSTOM);
        enderman.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(200);
        enderman.setHealth(200);
        enderman.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(25);
        enderman.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).setBaseValue(10);
        enderman.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(15);
        enderman.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).setBaseValue(12);
        enderman.addScoreboardTag("adm_miniboss_voidgloom");
        enderman.addScoreboardTag("adm_remove_when_reload");
        enderman.customName(Component.text("⚛MINIBOSS ").color(TextColor.color(219, 42, 216)).decorate(TextDecoration.BOLD).append(Component.text("Voidgloom").color(TextColor.color(153, 79, 227)).decorate(TextDecoration.BOLD)));
        enderman.setCustomNameVisible(true);
        endermanRunnable(enderman);
    }


    private static void endermanRunnable(Enderman enderman){
        new BukkitRunnable(){
            int i = 0;
            @Override
            public void run() {
                if(enderman.isDead()){
                    if(!AdvancedUtils.isUnlocked(MonsterAbility.TELEPORTER)){
                        MonsterAbilityUnlockEvent event = new MonsterAbilityUnlockEvent(MonsterAbility.TELEPORTER);
                        Bukkit.getServer().getPluginManager().callEvent(event);
                        if(!event.isCancelled()) {
                            AdvancedUtils.setUnlocked(event.getAbility(), true);
                        }
                    }
                    cancel();
                    return;
                }
                enderman.getWorld().spawnParticle(Particle.SPELL_WITCH, enderman.getLocation(), 4, null);
                if(enderman.getTarget() != null){
                    if(i % 80 == 0){
                        Location before = enderman.getLocation();
                        Location after = enderman.getTarget().getLocation().add(Math.random() * 10, 0, Math.random() * 10);
                        if(!enderman.getWorld().getBlockAt(after).getType().isAir()){
                            enderman.teleport(after);
                            LaserModifier.spawnLaser(before.add(0, 2, 0), enderman.getLocation().add(0, 3, 0), org.bukkit.Color.fromRGB(144, 3, 252));
                            LaserModifier.spawnLaser(before.add(0, 1, 0), enderman.getLocation().add(0, 2, 0), org.bukkit.Color.fromRGB(144, 3, 252));
                        }
                    }
                    if(i % 200 == 0){
                        for(Entity entity : enderman.getNearbyEntities(15, 15, 15)){
                            if(entity instanceof Enderman){
                                entity.teleport(enderman.getLocation(), PlayerTeleportEvent.TeleportCause.PLUGIN);
                                ((Enderman) entity).setTarget(enderman.getTarget());
                            }
                        }
                    }
                }
                i++;
                if(i >= (Integer.MAX_VALUE - 100)){
                    i = 0;
                }
            }
        }.runTaskTimer(AdvancedMonsters.getPlugin(AdvancedMonsters.class), 0L, 1L);
    }
}
