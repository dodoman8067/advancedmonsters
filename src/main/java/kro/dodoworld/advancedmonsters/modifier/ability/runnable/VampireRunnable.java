package kro.dodoworld.advancedmonsters.modifier.ability.runnable;

import kro.dodoworld.advancedmonsters.modifier.ability.Ability;
import kro.dodoworld.advancedmonsters.modifier.ability.AbilityRunnable;
import kro.dodoworld.advancedmonsters.modifier.ability.custom.VampireAbility;
import kro.dodoworld.advancedmonsters.util.AbilityUtils;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Bat;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.entity.SpawnCategory;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.util.Vector;

public class VampireRunnable extends AbilityRunnable {
    private final double range;
    private final double amount;

    public VampireRunnable(Ability ability, double range, double amount) {
        super(ability);
        this.range = range;
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
                    }else{
                        if(monster.getTarget() instanceof Player player && ((player.getGameMode().equals(GameMode.SPECTATOR) || player.getGameMode().equals(GameMode.CREATIVE)))) continue;
                        for(Mob mob : monster.getWorld().getNearbyEntitiesByType(Mob.class, monster.getLocation(), range)){
                            if(mob.getSpawnCategory().equals(SpawnCategory.MONSTER)) continue;
                            if(mob instanceof Bat) continue;
                            if(!monster.hasLineOfSight(mob)) continue;
                            mob.damage(amount, monster);
                            spawnLaser(monster.getLocation().add(0, monster.getHeight() / 2, 0), mob.getLocation(), Color.fromRGB(108, 0, 0));
                            monster.heal(amount, EntityRegainHealthEvent.RegainReason.MAGIC);
                        }
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

    private void spawnLaser(Location basis, Location target, Color color) {
        World world = basis.getWorld();
        Validate.isTrue(target.getWorld().equals(world));
        double dis = basis.distance(target);
        Vector pos1 = basis.toVector();
        Vector pos2 = target.toVector();
        Vector vector = pos2.clone().subtract(pos1).normalize().multiply(0.1);
        double cover = 0.0;

        while(cover < dis) {
            Particle.DustOptions dustOptions = new Particle.DustOptions(color, 1.0F);
            world.spawnParticle(Particle.DUST, pos1.getX(), pos1.getY(), pos1.getZ(), 1, dustOptions);
            cover += 0.1;
            pos1.add(vector);
        }
    }
}
