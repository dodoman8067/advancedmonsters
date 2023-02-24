package kro.dodoworld.advancedmonsters.entity.miniboss;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.awt.Color;

public class DiamondZombie {
    public static void createZombie(Location loc){
        Zombie zombie = loc.getWorld().spawn(loc, Zombie.class);
        zombie.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(zombie.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getValue() * 1.4);
        zombie.setCanBreakDoors(true);
        ItemStack stack = new ItemStack(Material.DIAMOND_HELMET);
        ItemMeta meta = stack.getItemMeta();
        meta.setUnbreakable(true);
        meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4, false);
        stack.setItemMeta(meta);
        ItemStack stack1 = new ItemStack(Material.DIAMOND_CHESTPLATE);
        ItemMeta meta1 = stack1.getItemMeta();
        meta1.setUnbreakable(true);
        meta1.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4, false);
        stack1.setItemMeta(meta1);
        ItemStack stack2 = new ItemStack(Material.DIAMOND_LEGGINGS);
        ItemMeta meta2 = stack2.getItemMeta();
        meta2.setUnbreakable(true);
        meta2.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4, false);
        stack2.setItemMeta(meta2);
        ItemStack stack3 = new ItemStack(Material.DIAMOND_BOOTS);
        ItemMeta meta3 = stack3.getItemMeta();
        meta3.setUnbreakable(true);
        meta3.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4, false);
        stack3.setItemMeta(meta3);
        zombie.getEquipment().setHelmet(new ItemStack(stack));
        zombie.getEquipment().setHelmetDropChance(0f);
        zombie.getEquipment().setChestplate(new ItemStack(stack1));
        zombie.getEquipment().setChestplateDropChance(0f);
        zombie.getEquipment().setLeggings(new ItemStack(stack2));
        zombie.getEquipment().setLeggingsDropChance(0f);
        zombie.getEquipment().setBoots(new ItemStack(stack3));
        zombie.getEquipment().setBootsDropChance(0f);
        zombie.getEquipment().setItemInMainHand(new ItemStack(Material.DIAMOND_AXE));
        zombie.addScoreboardTag("adm_miniboss_diamond_zombie");
        zombie.addScoreboardTag("sw_entity_remove_when_reload");
        zombie.setCustomName(net.md_5.bungee.api.ChatColor.of(new Color(219, 42, 216)) + "" + ChatColor.BOLD + "⚛MINIBOSS " + ChatColor.GRAY + "Diamond Zombie");
        zombie.setCustomNameVisible(true);
    }
}
