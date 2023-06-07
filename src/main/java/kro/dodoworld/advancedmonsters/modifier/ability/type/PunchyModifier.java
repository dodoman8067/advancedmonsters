package kro.dodoworld.advancedmonsters.modifier.ability.type;

import kro.dodoworld.advancedmonsters.AdvancedMonsters;
import kro.dodoworld.advancedmonsters.config.modifier.PunchyModifierConfig;
import kro.dodoworld.advancedmonsters.util.AdvancedUtils;
import kro.dodoworld.advancedmonsters.util.MonsterAbility;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.util.Vector;

public class PunchyModifier implements Listener {

    private final AdvancedMonsters plugin;

    public PunchyModifier(AdvancedMonsters plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onHit(EntityDamageByEntityEvent event){
        if(event.getDamager() instanceof Monster && event.getEntity() instanceof LivingEntity){
            if(event.getDamager().getScoreboardTags().contains("adm_modifier_punchy")){
                double rnd = Math.random() * 100;
                if(rnd <= PunchyModifierConfig.getPunchyModifierConfig().getDouble("punchy_punch_air_chance")){
                    event.setCancelled(true);
                    LivingEntity player = (LivingEntity) event.getEntity();
                    Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                        player.setVelocity(new Vector(0,2,0));
                    }, 1L);
                    if(player instanceof Player && PunchyModifierConfig.getPunchyModifierConfig().getBoolean("punchy_show_punch_air_message")){
                        player.sendMessage(AdvancedUtils.getAbilitySymbolWithColor(MonsterAbility.PUNCHY).append(Component.text(MonsterAbility.PUNCHY.toString() + " ").append(Component.text("능력으로 의해 하늘로 날려졌습니다!", NamedTextColor.RED))));
                    }
                }
            }
        }
        if(event.getDamager() instanceof Projectile && ((Projectile) event.getDamager()).getShooter() instanceof Monster){
            if(!(((Monster) ((Projectile) event.getDamager()).getShooter()).getScoreboardTags().contains("adm_modifier_punchy"))) return;
            double rnd = Math.random() * 100;
            if(rnd <= PunchyModifierConfig.getPunchyModifierConfig().getDouble("punchy_punch_air_chance")) {
                event.setCancelled(true);
                LivingEntity player = (LivingEntity) event.getEntity();
                Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                    player.setVelocity(new Vector(0, 2, 0));
                }, 1L);
                if (player instanceof Player && PunchyModifierConfig.getPunchyModifierConfig().getBoolean("punchy_show_punch_air_message")) {
                    player.sendMessage(AdvancedUtils.getAbilitySymbolWithColor(MonsterAbility.PUNCHY).append(Component.text(MonsterAbility.PUNCHY.toString() + " ").append(Component.text("능력으로 의해 하늘로 날려졌습니다!", NamedTextColor.RED))));
                }
            }
        }
    }
}
