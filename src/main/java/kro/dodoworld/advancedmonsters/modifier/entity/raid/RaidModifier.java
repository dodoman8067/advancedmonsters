package kro.dodoworld.advancedmonsters.modifier.entity.raid;

import org.bukkit.Raid;
import org.bukkit.entity.Raider;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.raid.RaidSpawnWaveEvent;

import java.util.concurrent.ThreadLocalRandom;

public class RaidModifier implements Listener {
    private final ThreadLocalRandom random = ThreadLocalRandom.current();
    @EventHandler
    public void onRaidWave(RaidSpawnWaveEvent event){

    }

    private void addRaider(Raid raid, Class<? extends Raider> raiderClazz){
        int size = raid.getRaiders().size();
        Raider raider = raid.getLocation().getWorld().spawn(raid.getRaiders().get(random.nextInt(0, size)).getLocation(), raiderClazz);
        raider.setCanJoinRaid(true);
    }
}
