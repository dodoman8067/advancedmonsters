package kro.dodoworld.advancedmonsters.modifier.ability.runnable;

import kro.dodoworld.advancedmonsters.AdvancedMonsters;
import kro.dodoworld.advancedmonsters.modifier.ability.Ability;
import kro.dodoworld.advancedmonsters.modifier.ability.AbilityRunnable;
import kro.dodoworld.advancedmonsters.modifier.ability.custom.HealerAbility;
import kro.dodoworld.advancedmonsters.system.entity.ability.HealingCircle;
import kro.dodoworld.advancedmonsters.util.AbilityUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;

import java.util.Collection;
import java.util.concurrent.ThreadLocalRandom;

public class HealerRunnable extends AbilityRunnable {
    private final int radius;
    private final double amount;

    public HealerRunnable(Ability ability, int radius, double amount) {
        super(ability);
        this.radius = radius;
        this.amount = amount;
    }

    @Override
    public void run(){
        if(getAbility() == null) return;
        for(World world : Bukkit.getWorlds()){
            for(LivingEntity entity : world.getLivingEntities()){
                if(!(entity instanceof Monster monster)) continue;
                if(HealerAbility.getHealerMonsters().contains(monster.getUniqueId()) && AbilityUtils.hasAbility(monster, getAbility())){
                    if(monster.isDead()){
                        HealerAbility.getHealerMonsters().remove(monster.getUniqueId());
                        continue;
                    }
                    Collection<Monster> monsters = monster.getLocation().getNearbyEntitiesByType(Monster.class, 10, 3, 10);
                    int monstersLowHealth = 0;
                    if(monsters.size() > 4){
                        for(Monster m : monsters){
                            if(m.getHealth() < (m.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() / 2)){
                                monstersLowHealth++;
                            }
                        }

                        if(monstersLowHealth > 1){
                            if(ThreadLocalRandom.current().nextInt(2) == 1){
                                HealingCircle circle = new HealingCircle(monster, this.radius, this.amount);
                                circle.spawn();
                                Bukkit.getScheduler().runTaskLater(AdvancedMonsters.getPlugin(AdvancedMonsters.class), circle::remove, 200L);
                            }
                        }
                    }
                }else if(!AbilityUtils.hasAbility(monster, getAbility())){
                    HealerAbility.getHealerMonsters().remove(monster.getUniqueId());
                }
                if(AbilityUtils.hasAbility(monster, getAbility())){
                    HealerAbility.getHealerMonsters().add(monster.getUniqueId());
                }

            }
        }
    }
}
