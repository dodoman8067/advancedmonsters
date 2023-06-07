package kro.dodoworld.advancedmonsters.modifier.ability.type;

import kro.dodoworld.advancedmonsters.AdvancedMonsters;
import kro.dodoworld.advancedmonsters.config.modifier.StormyModifierConfig;
import kro.dodoworld.advancedmonsters.util.AdvancedUtils;
import kro.dodoworld.advancedmonsters.util.MonsterAbility;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class StormyModifier implements Listener {

    public static void run(AdvancedMonsters plugin){
        FileConfiguration config = StormyModifierConfig.getStormyModifierConfig();
        double lightingRange = config.getDouble("stormy_lighting_range");
        double lightingDamage = config.getDouble("stormy_lighting_damage");
        int ticks = config.getInt("stormy_slow_effect_ticks");
        int amplifier = config.getInt("stormy_slow_effect_amplifier");
        int cooldown = config.getInt("stormy_lighting_cooldown");
        new BukkitRunnable(){

            @Override
            public void run() {
                for(World world : Bukkit.getWorlds()){
                    for(LivingEntity entity : world.getLivingEntities()){
                        if(entity instanceof Monster monster) {
                            if(monster.getScoreboardTags().contains("adm_modifier_stormy")){
                                if(monster.isDead()) cancel();
                                if(monster.getTarget() != null){
                                    if(monster.getNearbyEntities(lightingRange, lightingRange, lightingRange).contains(monster.getTarget()) && monster.hasLineOfSight(monster.getTarget())){
                                        monster.getTarget().getWorld().strikeLightning(monster.getTarget().getLocation());
                                        monster.getTarget().damage(lightingDamage, monster);
                                        monster.getTarget().addPotionEffect(new PotionEffect(PotionEffectType.SLOW, ticks, amplifier));
                                        if(config.getBoolean("stormy_show_lighting_damage_message")) monster.getTarget().sendMessage(
                                                AdvancedUtils.getAbilitySymbolWithColor(MonsterAbility.STORMY).append(Component.text(MonsterAbility.STORMY.toString())).append(Component.text(" 능력에 의해 번개에 맞았습니다!")
                                                        .color(TextColor.color(0xFF5555))));
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }.runTaskTimer(plugin, 0L, cooldown);
    }

}
