package kro.dodoworld.advancedmonsters.entity;

import kro.dodoworld.advancedmonsters.entity.miniboss.*;
import kro.dodoworld.advancedmonsters.util.BlockUtilMethods;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.PolarBear;
import org.bukkit.entity.Stray;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.ProjectileHitEvent;

import java.util.concurrent.ThreadLocalRandom;

public class MiniBossSpawn implements Listener {
    @EventHandler
    public void onSpawn(EntitySpawnEvent event){
        if(event.getEntity().getWorld().getDifficulty().equals(Difficulty.PEACEFUL)) return;
        Entity entity = event.getEntity();
        final ThreadLocalRandom rnd = ThreadLocalRandom.current();
        if((rnd.nextInt(0, 51) == 1)){

            if(entity.getType().equals(EntityType.ZOMBIE)){
                int bossType = rnd.nextInt(0, 2);
                event.setCancelled(true);
                if(bossType == 0){
                    DiamondZombie.createZombie(event.getLocation());
                }
                if(bossType == 1){
                    Bombie.createBombie(event.getLocation());
                }
            }
            if(entity.getType().equals(EntityType.SPIDER)){
                event.setCancelled(true);
                LeapingSpider.createLeapingSpider(event.getLocation());
            }
            if(entity.getType().equals(EntityType.ENDERMAN)){
                event.setCancelled(true);
                VoidGloom.createVoidGloom(event.getLocation());
            }
            if(entity.getType().equals(EntityType.BLAZE)){
                event.setCancelled(true);
                Inferno.createInferno(event.getLocation());
            }
            if(entity.getType().equals(EntityType.SKELETON)){
                event.setCancelled(true);
                int bossType = rnd.nextInt(0, 2);
                if(bossType == 0){
                    Storm.createStorm(event.getLocation());
                }
                if(bossType == 1){
                    EarthQuaker.createEarthQuaker(event.getLocation());
                }
            }
            if(entity.getType().equals(EntityType.SLIME)){
                event.setCancelled(true);
                SludgeGore.createSludgeGore(event.getLocation());
            }
        }
    }

    @EventHandler
    public void onHit(ProjectileHitEvent event){
        if(!(event.getHitEntity() instanceof PolarBear) || event.getHitEntity() == null) return;
        if(!BlockUtilMethods.isSnowy(event.getHitEntity().getWorld().getBiome(event.getHitEntity().getLocation()))) return;
        if(event.getHitEntity().getScoreboardTags().contains("adm_miniboss_sbe")) return;
        if(event.getEntity().getShooter() != null && event.getEntity().getShooter() instanceof Stray){
            if(((Stray) event.getEntity().getShooter()).getScoreboardTags().contains("adm_miniboss_sbe")) return;
            event.getHitEntity().getWorld().strikeLightningEffect(event.getHitEntity().getLocation());
            event.getHitEntity().remove();
            Sbe.createSbe(event.getHitEntity().getLocation());
            Bukkit.broadcast(Component.text("⚛MINIBOSS ").color(TextColor.color(219, 42, 216)).decorate(TextDecoration.BOLD)
                    .append(Component.text("S_be").color(NamedTextColor.WHITE).decorate(TextDecoration.BOLD)).append(Component.text("가 스폰되었습니다!").color(NamedTextColor.YELLOW).decorate(TextDecoration.BOLD)));
        }
    }
}
