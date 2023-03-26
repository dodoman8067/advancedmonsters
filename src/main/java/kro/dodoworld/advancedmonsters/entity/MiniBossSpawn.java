package kro.dodoworld.advancedmonsters.entity;

import kro.dodoworld.advancedmonsters.entity.miniboss.Bombie;
import kro.dodoworld.advancedmonsters.entity.miniboss.DiamondZombie;
import kro.dodoworld.advancedmonsters.entity.miniboss.EarthQuaker;
import kro.dodoworld.advancedmonsters.entity.miniboss.Inferno;
import kro.dodoworld.advancedmonsters.entity.miniboss.LeapingSpider;
import kro.dodoworld.advancedmonsters.entity.miniboss.Sbe;
import kro.dodoworld.advancedmonsters.entity.miniboss.Storm;
import kro.dodoworld.advancedmonsters.entity.miniboss.VoidGloom;
import kro.dodoworld.advancedmonsters.util.BlockUtilMethods;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Monster;
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
        if(!(event.getEntity() instanceof Monster entity)) return;
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
        }
    }

    @EventHandler
    public void onHit(ProjectileHitEvent event){
        if(!(event.getHitEntity() instanceof PolarBear)) return;
        if(event.getEntity().getShooter() != null && event.getEntity().getShooter() instanceof Stray){
            if(!BlockUtilMethods.isSnowy(event.getHitEntity().getWorld().getBiome(event.getHitEntity().getLocation()))) return;
            event.getHitEntity().getWorld().strikeLightningEffect(event.getHitEntity().getLocation());
            event.getHitEntity().remove();
            Sbe.createSbe(event.getHitEntity().getLocation());
            Bukkit.broadcast(Component.text("⚛MINIBOSS ").color(TextColor.color(219, 42, 216)).decorate(TextDecoration.BOLD)
                    .append(Component.text("S_be").color(NamedTextColor.WHITE).decorate(TextDecoration.BOLD)).append(Component.text("가 스폰되었습니다!").color(NamedTextColor.YELLOW).decorate(TextDecoration.BOLD)));
        }
    }

    private boolean isMiniboss(Entity entity){
        for(String s : entity.getScoreboardTags()){
            if(s.startsWith("adm_miniboss")){
                return true;
            }
        }
        return false;
    }
}
