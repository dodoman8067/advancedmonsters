package kro.dodoworld.advancedmonsters.util;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;

public class LocationUtils {
    public static List<Location> getCircle(Location center, double radius, int amount){
        World world = center.getWorld();
        double increment = ((2 * Math.PI) / amount);
        List<Location> locations = new ArrayList<>();
        for(int i = 0;i < amount; i++){
            double angle = i * increment;
            double x = center.getX() + (radius * Math.cos(angle));
            double z = center.getZ() + (radius * Math.sin(angle));
            locations.add(new Location(world, x, center.getY(), z));
        }
        return locations;
    }

    public static void createCircle(Location loc, float radius, int r, int g, int b, float size){
        for(double t = 0; t<50; t+=0.5){
            float x = radius*(float) Math.sin(t);
            float z = radius*(float) Math.cos(t);

            loc.getWorld().spawnParticle(Particle.REDSTONE, x + loc.getX(), loc.getY(), z + loc.getZ(), 1, 0 ,0, 0 ,0, new Particle.DustOptions(org.bukkit.Color.fromRGB(r, g, b), size));
        }
    }
}
