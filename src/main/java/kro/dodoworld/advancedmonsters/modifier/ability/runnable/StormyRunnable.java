package kro.dodoworld.advancedmonsters.modifier.ability.runnable;

import kro.dodoworld.advancedmonsters.modifier.ability.Ability;
import kro.dodoworld.advancedmonsters.modifier.ability.AbilityRunnable;
import kro.dodoworld.advancedmonsters.modifier.ability.custom.StormyAbility;
import kro.dodoworld.advancedmonsters.util.AbilityUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class StormyRunnable extends AbilityRunnable {
    private final double lightingRange;
    private final double lightingDamage;
    private final int ticks;
    private final int amplifier;

    public StormyRunnable(Ability ability, double lightingRange, double lightingDamage, int ticks, int amplifier) {
        super(ability);
        this.lightingRange = lightingRange;
        this.lightingDamage = lightingDamage;
        this.ticks = ticks;
        this.amplifier = amplifier;
    }

    @Override
    public void run() {
        if(getAbility() == null) return;
        if(getAbility().getConfig() == null) return;
        if(getAbility().getSymbol() == null) return;
        for(World world : Bukkit.getWorlds()){
            for(LivingEntity entity : world.getLivingEntities()){
                if(!(entity instanceof Monster monster)) continue;
                if(StormyAbility.getStormingMonsters().contains(monster.getUniqueId()) && AbilityUtils.hasAbility(monster, getAbility())){
                    if(monster.isDead()) StormyAbility.getStormingMonsters().remove(monster.getUniqueId());
                    if(monster.getTarget() != null){
                        if(monster.getNearbyEntities(lightingRange, lightingRange, lightingRange).contains(monster.getTarget()) && monster.hasLineOfSight(monster.getTarget())) {
                            monster.getTarget().getWorld().strikeLightning(monster.getTarget().getLocation()).addScoreboardTag("adm_storm_summoned");
                            monster.getTarget().damage(lightingDamage, monster);
                            monster.getTarget().addPotionEffect(new PotionEffect(PotionEffectType.SLOW, ticks, amplifier));
                            if(getAbility().getConfig().getBoolean("stormy_show_lighting_damage_message")) monster.getTarget().sendMessage(
                                    getAbility().getSymbol().append(getAbility().getName()).append(Component.text(" 능력에 의해 번개에 맞았습니다!")
                                            .color(TextColor.color(0xFF5555))));
                        }
                    }
                }else if(!AbilityUtils.hasAbility(monster, getAbility())){
                    StormyAbility.getStormingMonsters().remove(monster.getUniqueId());
                }
                if(AbilityUtils.hasAbility(monster, getAbility())){
                    StormyAbility.getStormingMonsters().add(monster.getUniqueId());
                }
            }
        }
    }
}
