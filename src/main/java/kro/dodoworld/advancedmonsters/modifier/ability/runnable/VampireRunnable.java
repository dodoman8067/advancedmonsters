package kro.dodoworld.advancedmonsters.modifier.ability.runnable;

import kro.dodoworld.advancedmonsters.modifier.ability.Ability;
import kro.dodoworld.advancedmonsters.modifier.ability.AbilityRunnable;
import kro.dodoworld.advancedmonsters.modifier.ability.custom.VampireAbility;
import kro.dodoworld.advancedmonsters.util.AbilityUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;

public class VampireRunnable extends AbilityRunnable {
    private final double amount;

    public VampireRunnable(Ability ability, double amount) {
        super(ability);
        this.amount = amount;
    }

    @Override
    public void run() {
        if(getAbility() == null) return;
        if(getAbility().getConfig() == null) return;
        for(World world : Bukkit.getWorlds()){
            for(LivingEntity entity : world.getLivingEntities()){
                if(!(entity instanceof Monster monster)) continue;
                if(VampireAbility.getVampireMonsters().contains(monster.getUniqueId()) && AbilityUtils.hasAbility(monster, getAbility())){
                    if(monster.isDead() || !monster.isValid()) VampireAbility.getVampireMonsters().remove(monster.getUniqueId());
                    if(monster.getWorld().isDayTime()){
                        monster.damage(monster.getLocation().getBlock().getLightLevel() * (amount * 2));
                        monster.setNoDamageTicks(1);
                    }
                }else if(!AbilityUtils.hasAbility(monster, getAbility())){
                    VampireAbility.getVampireMonsters().remove(monster.getUniqueId());
                }
                if(AbilityUtils.hasAbility(monster, getAbility())){
                    VampireAbility.getVampireMonsters().add(monster.getUniqueId());
                }
            }
        }
    }
}
