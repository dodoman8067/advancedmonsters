package kro.dodoworld.advancedmonsters.entity.miniboss;

import kro.dodoworld.advancedmonsters.AdvancedMonsters;
import kro.dodoworld.advancedmonsters.modifiers.ability.type.LaserModifier;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.awt.Color;

public class VoidGloom implements Listener {
    private AdvancedMonsters plugin;

    public VoidGloom(AdvancedMonsters plugin){
        this.plugin = plugin;
    }
    public void createVoidGloom(Location loc){
        Enderman enderman = loc.getWorld().spawn(loc, Enderman.class);
        enderman.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(200);
        enderman.setHealth(200);
        enderman.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(25);
        enderman.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).setBaseValue(10);
        enderman.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(15);
        enderman.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).setBaseValue(12);
        enderman.addScoreboardTag("adm_miniboss_voidgloom");
        enderman.addScoreboardTag("sw_entity_remove_when_reload");
        enderman.setCustomName(net.md_5.bungee.api.ChatColor.of(new Color(219, 42, 216)) + "" + ChatColor.BOLD + "⚛MINIBOSS " + net.md_5.bungee.api.ChatColor.of(new Color(153, 79, 227)) + "Voidgloom");
        enderman.setCustomNameVisible(true);
        endermanRunnable(enderman);
    }


    private void endermanRunnable(Enderman enderman){
        new BukkitRunnable(){
            int i = 0;
            @Override
            public void run() {
                if(enderman.isDead()) cancel();
                enderman.getWorld().spawnParticle(Particle.SPELL_WITCH, enderman.getLocation(), 4, null);
                if(enderman.getTarget() != null){
                    if(i % 80 == 0){
                        Location before = enderman.getLocation();
                        enderman.teleport(enderman.getTarget().getLocation().add(Math.random() * 10, 0, Math.random() * 10));
                        LaserModifier.spawnLaser(before.add(0, 2, 0), enderman.getLocation().add(0, 3, 0), org.bukkit.Color.fromRGB(144, 3, 252));
                        LaserModifier.spawnLaser(before.add(0, 1, 0), enderman.getLocation().add(0, 2, 0), org.bukkit.Color.fromRGB(144, 3, 252));
                    }
                    if(i % 200 == 0){
                        for(Entity entity : enderman.getNearbyEntities(15, 15, 15)){
                            if(entity instanceof Enderman){
                                entity.teleport(enderman.getLocation(), PlayerTeleportEvent.TeleportCause.PLUGIN);
                                ((Enderman) entity).setTarget(enderman.getTarget());
                            }
                        }
                    }
                    if(enderman.getLocation().getY() <= -5){
                        enderman.teleport(enderman.getTarget().getLocation());
                    }
                }
                i++;
                if(i >= (Integer.MAX_VALUE - 100)){
                    i = 0;
                }
            }
        }.runTaskTimer(plugin, 0L, 1L);
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event){
        if(!(event.getDamager() instanceof Enderman)) return;
        if(!(event.getEntity() instanceof LivingEntity)) return;
        if(!event.getDamager().getScoreboardTags().contains("adm_miniboss_voidgloom")) return;
        if((Math.random() * 100) <= 50) return;
        event.getEntity().teleport(event.getEntity().getLocation().add(Math.random() * 20, 0, Math.random() * 20));
    }
}
