package kro.dodoworld.advancedmonsters.util;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.List;

public class UtilMethods {
    public static void createCircle(Location loc, float radius, int r, int g, int b, float size){
        for(double t = 0; t<50; t+=0.5){
            float x = radius*(float) Math.sin(t);
            float z = radius*(float) Math.cos(t);
            loc.getWorld().spawnParticle(Particle.REDSTONE, x + loc.getX(), loc.getY(), z + loc.getZ(), 1, 0 ,0, 0 ,0, new Particle.DustOptions(org.bukkit.Color.fromRGB(r, g, b), size));
        }
    }

    public static List<Block> getNearbyBlocks(Block start, int radius){
        List<Block> blocks = new ArrayList<>();
        for(double x = start.getLocation().getX() - radius; x <= start.getLocation().getX() + radius; x++){
            for(double y = start.getLocation().getY() - radius; y <= start.getLocation().getY() + radius; y++){
                for(double z = start.getLocation().getZ() - radius; z <= start.getLocation().getZ() + radius; z++){
                    Location loc = new Location(start.getWorld(), x, y, z);
                    blocks.add(loc.getBlock());
                }
            }
        }
        return blocks;
    }
}
