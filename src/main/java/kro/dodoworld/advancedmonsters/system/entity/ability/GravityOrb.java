package kro.dodoworld.advancedmonsters.system.entity.ability;

import kro.dodoworld.advancedmonsters.AdvancedMonsters;
import kro.dodoworld.advancedmonsters.util.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Transformation;
import org.joml.AxisAngle4f;
import org.joml.Vector3f;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class GravityOrb {
    private final LivingEntity summonedBy;
    private BlockDisplay orb;
    private final int radius;
    // It does not use UUID as a type parameter on purpose because getting the entity by its unique id might take long
    private static final Set<BlockDisplay> BLOCK_DISPLAYS = new HashSet<>();

    public GravityOrb(LivingEntity summonedBy, int radius){
        this.summonedBy = summonedBy;
        this.radius = radius;
    }

    public LivingEntity getSummonedBy() {
        return summonedBy;
    }

    public int getRadius() {
        return radius;
    }

    public void spawn(){
        spawn(10);
    }

    public void spawn(int seconds){
        orb = summonedBy.getWorld().spawn(summonedBy.getLocation(), BlockDisplay.class, entity -> {
            entity.setTransformation(new Transformation(new Vector3f(), new AxisAngle4f(), new Vector3f(), new AxisAngle4f()));
            entity.setBlock(ItemUtils.getSkull("https://textures.minecraft.net/texture/78cf2596fb4cca004ee79561a062819c247cb4ba711d9540970e4398d9dbac43").getType().createBlockData());
        });
        if(orb.isValid()) BLOCK_DISPLAYS.add(orb);

        Bukkit.getScheduler().runTaskLater(AdvancedMonsters.getPlugin(AdvancedMonsters.class), this::remove, seconds * 20L);
    }

    public void remove(){
        orb.remove();
        BLOCK_DISPLAYS.remove(orb);
    }

    public BlockDisplay getOrb() {
        return orb;
    }

    public static Set<BlockDisplay> getBlockDisplays() {
        return Collections.unmodifiableSet(BLOCK_DISPLAYS);
    }

    public static void removeAll(){
        for(BlockDisplay blockDisplay : BLOCK_DISPLAYS){
            blockDisplay.remove();
        }
    }
}
