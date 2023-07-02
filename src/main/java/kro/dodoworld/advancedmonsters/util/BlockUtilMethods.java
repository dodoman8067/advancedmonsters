package kro.dodoworld.advancedmonsters.util;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for block-related methods.
 */
public class BlockUtilMethods {

    /**
     * Creates particles with circle shape.
     * @param loc location to create circle
     * @param radius circle's radius
     * @param r red from particle's color
     * @param g green from particle's color
     * @param b blue from particle's color
     * @param size particle's size (not the circle's size)
     */
    public static void createCircle(Location loc, float radius, int r, int g, int b, float size){
        for(double t = 0; t<50; t+=0.5){
            //Math stuff
            float x = radius*(float) Math.sin(t);
            float z = radius*(float) Math.cos(t);
            //Spawns particle
            loc.getWorld().spawnParticle(Particle.REDSTONE, x + loc.getX(), loc.getY(), z + loc.getZ(), 1, 0 ,0, 0 ,0, new Particle.DustOptions(org.bukkit.Color.fromRGB(r, g, b), size));
        }
    }

    /**
     * Returns nearby blocks.
     * @param start start location of getting blocks
     * @param radius radius of getting blocks
     * @return list of blocks
     */
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

    /**
     * Returns locations of circle.
     * @param center circle's center
     * @param radius circle's radius
     * @param amount circle's amount
     * @return list of location
     */
    public static List<Location> getCircle(Location center, double radius, int amount){
        World world = center.getWorld();
        double increment = ((2 * Math.PI) / amount);
        ArrayList<Location> locations = new ArrayList<>();
        for(int i = 0;i < amount; i++){
            double angle = i * increment;
            double x = center.getX() + (radius * Math.cos(angle));
            double z = center.getZ() + (radius * Math.sin(angle));
            locations.add(new Location(world, x, center.getY(), z));
        }
        return locations;
    }

    /**
     * Checks biome is snowy.
     * @param biome biome to check
     * @return true if biome is snowy, otherwise false
     */
    public static boolean isSnowy(Biome biome){
        return biome.equals(Biome.FROZEN_OCEAN) || biome.equals(Biome.FROZEN_PEAKS) || biome.equals(Biome.FROZEN_RIVER) || biome.equals(Biome.SNOWY_BEACH) || biome.equals(Biome.SNOWY_PLAINS)
                || biome.equals(Biome.SNOWY_SLOPES) || biome.equals(Biome.SNOWY_TAIGA);
    }
}
