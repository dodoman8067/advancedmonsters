package kro.dodoworld.advancedmonsters.entity.miniboss;

import kro.dodoworld.advancedmonsters.AdvancedMonsters;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.SpawnCategory;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.scheduler.BukkitRunnable;

public class Bombie implements Listener {
    private static AdvancedMonsters plugin;

    public Bombie(AdvancedMonsters plugin){
        Bombie.plugin = plugin;
    }
    public static void createBombie(Location loc){
        Zombie bombie = loc.getWorld().spawn(loc, Zombie.class);
        bombie.setCustomName(net.md_5.bungee.api.ChatColor.of(new java.awt.Color(219, 42, 216)) + "" + ChatColor.BOLD + "⚛MINIBOSS " + ChatColor.RED + "Bombie");
        bombie.setCustomNameVisible(true);
        bombie.setCanBreakDoors(true);
        bombie.addScoreboardTag("adm_miniboss_bombie");
        bombie.addScoreboardTag("sw_entity_remove_when_reload");
        bombie.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(85);
        bombie.setHealth(85);
        bombie.setAdult();
        bombie.getEquipment().setHelmet(new ItemStack(Material.TNT));
        bombie.getEquipment().setHelmetDropChance(0f);
        bombie.getEquipment().setChestplate(getRedArmor(Material.LEATHER_CHESTPLATE));
        bombie.getEquipment().setChestplateDropChance(0f);
        bombie.getEquipment().setLeggings(getRedArmor(Material.LEATHER_LEGGINGS));
        bombie.getEquipment().setLeggingsDropChance(0f);
        bombie.getEquipment().setBoots(getRedArmor(Material.LEATHER_BOOTS));
        bombie.getEquipment().setBootsDropChance(0f);
        bombie.getEquipment().setItemInMainHand(new ItemStack(Material.BLAZE_ROD));
        bombie.getEquipment().setItemInMainHandDropChance(0f);
        bombie.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(bombie.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getValue() * 1.5);
        new BukkitRunnable(){
            int i = 0;
            @Override
            public void run() {
                if(bombie.isDead()){
                    cancel();
                    return;
                }
                if(bombie.getTarget() != null){
                    if(i % 60 == 0){
                        TNTPrimed tnt = bombie.getWorld().spawn(bombie.getEyeLocation(), TNTPrimed.class);
                        tnt.setSource(bombie);
                        tnt.setFuseTicks(100);
                        tnt.addScoreboardTag("adm_bombie_tnt");
                        tnt.setVelocity(bombie.getLocation().getDirection().multiply(2));
                    }
                    i++;
                    if(i <= (Integer.MAX_VALUE - 100000)){
                        i = 0;
                    }
                }
            }
        }.runTaskTimer(plugin, 0L, 1L);
    }

    private static ItemStack getRedArmor(Material material){
        ItemStack stack = new ItemStack(material);
        LeatherArmorMeta meta = (LeatherArmorMeta) stack.getItemMeta();
        meta.setColor(org.bukkit.Color.fromRGB(181, 29, 29));
        meta.setUnbreakable(true);
        stack.setItemMeta(meta);
        return stack;
    }

    @EventHandler
    public void onExplode(EntityDamageByEntityEvent event){
        if(!event.getCause().equals(EntityDamageEvent.DamageCause.BLOCK_EXPLOSION)) return;
        if(!(event.getDamager() instanceof TNTPrimed)) return;
        if(!event.getDamager().getScoreboardTags().contains("adm_bombie_tnt")) return;
        if(event.getEntity().getSpawnCategory().equals(SpawnCategory.MONSTER)) {
            event.setCancelled(true);
        }else{
            event.setDamage(event.getFinalDamage() * 3);
        }
    }
}
