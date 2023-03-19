package kro.dodoworld.advancedmonsters.entity.miniboss;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import kro.dodoworld.advancedmonsters.AdvancedMonsters;
import kro.dodoworld.advancedmonsters.util.Skulls;
import kro.dodoworld.advancedmonsters.util.UtilMethods;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

public class EarthQuaker implements Listener {

    public static void createEarthQuaker(Location loc){
        Skeleton earthQuaker = loc.getWorld().spawn(loc, Skeleton.class);
        earthQuaker.getEquipment().setHelmet(new ItemStack(Skulls.getSkull("https://textures.minecraft.net/texture/2c2d745dc69979e75d9c9fc045ad0f7f306455f5c385838c2cbf742c80cb021c")));
        earthQuaker.getEquipment().setHelmetDropChance(0f);
        earthQuaker.getEquipment().setChestplate(getGrayArmor(Material.LEATHER_CHESTPLATE));
        earthQuaker.getEquipment().setChestplateDropChance(0f);
        earthQuaker.getEquipment().setLeggings(getGrayArmor(Material.LEATHER_LEGGINGS));
        earthQuaker.getEquipment().setLeggingsDropChance(0f);
        earthQuaker.getEquipment().setBoots(getGrayArmor(Material.LEATHER_BOOTS));
        earthQuaker.getEquipment().setItemInMainHand(new ItemStack(Material.STONE_SWORD));
        earthQuaker.getEquipment().setItemInMainHandDropChance(0f);
        earthQuaker.getEquipment().setBootsDropChance(0f);
        earthQuaker.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(30);
        earthQuaker.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).setBaseValue(20);
        earthQuaker.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(320);
        earthQuaker.addScoreboardTag("adm_remove_when_reload");
        earthQuaker.addScoreboardTag("adm_miniboss_earthquaker");
        earthQuaker.setCustomNameVisible(true);
        earthQuaker.customName(getName());
        earthQuaker.setHealth(320);
        new BukkitRunnable(){
            int i = 0;

            @Override
            public void run() {
                if(earthQuaker.isDead()){
                    cancel();
                }
                if(earthQuaker.getTarget() != null && !earthQuaker.getTarget().isDead()){
                    if(i % 180 == 0){
                        new BukkitRunnable() {
                            final Location location = earthQuaker.getLocation().subtract(0, 1, 0);
                            int rad = 1;
                            @Override
                            public void run() {
                                for (Location loc : UtilMethods.getCircle(location, rad, (rad * ((int) (Math.PI * 2))))) {
                                    if(loc.getBlock().getType().getBlastResistance() >= 3000000) continue;
                                    FallingBlock fb = loc.getWorld().spawnFallingBlock(loc, loc.getBlock().getBlockData());
                                    fb.setHurtEntities(true);
                                    fb.setDropItem(false);
                                    fb.setVelocity(new Vector(0, .5, 0));
                                    loc.getBlock().setType(Material.AIR);
                                    for(Entity entity : fb.getNearbyEntities(0.5, 0.5, 0.5)){
                                        if(entity instanceof LivingEntity && !entity.getScoreboardTags().contains("adm_miniboss_earthquaker")){
                                            ((LivingEntity) entity).damage(15);
                                        }
                                    }
                                }
                                rad++;
                                if(rad >= 20){
                                    cancel();
                                }
                            }
                        }.runTaskTimer(AdvancedMonsters.getPlugin(AdvancedMonsters.class), 2, 2);
                }
                }
                if(i >= (Integer.MAX_VALUE - 100000)){
                    i = 0;
                }
                i++;
            }
        }.runTaskTimer(AdvancedMonsters.getPlugin(AdvancedMonsters.class), 0L,1L);
    }

    private static Component getName(){
        return Component.text("⚛MINIBOSS ").color(TextColor.color(219, 42, 216)).decorate(TextDecoration.BOLD).append(Component.text("Earthquaker").color(TextColor.color(65, 135, 38)).decorate(TextDecoration.BOLD));
    }


    private static ItemStack getGrayArmor(Material material){
        ItemStack stack = new ItemStack(material);
        LeatherArmorMeta meta = (LeatherArmorMeta) stack.getItemMeta();
        if(material.equals(Material.LEATHER_BOOTS)){
            meta.setColor(Color.fromRGB(161, 161, 161));
        }else{
            meta.setColor(org.bukkit.Color.fromRGB(133, 130, 130));
        }
        meta.setUnbreakable(true);
        meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4, false);
        if(material.equals(Material.LEATHER_BOOTS)){
            meta.addEnchant(Enchantment.DEPTH_STRIDER, 3, false);
        }
        stack.setItemMeta(meta);
        return stack;
    }

    private BukkitTask wave(Location location){
        return new BukkitRunnable() {
            int rad = 1;
            @Override
            public void run() {
                for (Location loc : UtilMethods.getCircle(location, rad, (rad * ((int) (Math.PI * 2))))) {
                    if(loc.getBlock().getType().getBlastResistance() >= 3000000) continue;
                    FallingBlock fb = loc.getWorld().spawnFallingBlock(loc, loc.getBlock().getBlockData());
                    fb.setHurtEntities(true);
                    fb.setDropItem(false);
                    fb.setVelocity(new Vector(0, .5, 0));
                    loc.getBlock().setType(Material.AIR);
                    for(Entity entity : fb.getNearbyEntities(0.5, 0.5, 0.5)){
                        if(entity instanceof LivingEntity && !entity.getScoreboardTags().contains("adm_miniboss_earthquaker")){
                            ((LivingEntity) entity).damage(15);
                        }
                    }
                }
                rad++;
            }
        }.runTaskTimer(AdvancedMonsters.getPlugin(AdvancedMonsters.class), 180, 2);
    }
}
