package kro.dodoworld.advancedmonsters.modifiers.equipment.armor;

import kro.dodoworld.advancedmonsters.modifiers.equipment.enchantment.EntityEnchantment;
import kro.dodoworld.advancedmonsters.util.AdvancedMonstersUtilMethods;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class EntityArmor {

    public static ItemStack getRandomArmor(World world, EquipmentSlot slot){
        double level = AdvancedMonstersUtilMethods.getEquipmentLevel().getMonsterEquipmentLevel(world);
        ItemStack item = null;
        if(level >= 1.0 && level <= 3.0){
            item = getLeatherArmor(slot);
        }
        if(level >= 3.1 && level <= 7.0){
            int type = (int) (Math.random() * 2);
            if(type == 0){
                item = getLeatherArmor(slot);
            }
            if(type == 1){
                item = getGoldArmor(slot);
            }
        }
        if(level >= 7.1 && level <= 15.4){
            int type = (int) (Math.random() * 3);
            if(type == 0){
                item = getChainMailArmor(slot);
            }
            if(type == 1){
                item = getGoldArmor(slot);
            }
            if(type == 2){
                item = getLeatherArmor(slot);
            }
        }
        if(level >= 15.5 && level <= 28.7){
            int type = (int) (Math.random() * 3);
            if(type == 0){
                item = getChainMailArmor(slot);
            }
            if(type == 1){
                item = getGoldArmor(slot);
            }
            if(type == 2){
                item = getIronArmor(slot);
            }
        }
        if(level >= 28.8 && level <= 49.7){
            int type = (int) (Math.random() * 3);
            int chance = (int) (Math.random() * 100);
            if(type == 0){
                item = getChainMailArmor(slot);
            }
            if(type == 2){
                if(chance <= 10){
                    item = getDiamondArmor(slot);
                }else{
                    item = getIronArmor(slot);
                }
            }
        }
        if(level >= 49.8 && level <= 65.3){
            int chance = (int) (Math.random() * 100);

            if(chance <= 5){
                item = getIronArmor(slot);
            }else{
                item = getDiamondArmor(slot);
            }

        }
        if(level >= 65.4 && level <= 80.0){
            int chance = (int) (Math.random() * 100);

            if(chance <= 2){
                item = getDiamondArmor(slot);
            }else{
                item = getNetheriteArmor(slot);
            }

        }
        if(level >= 80.1){
            item = getNetheriteArmor(slot);
        }
        return item;
    }

    public static ItemStack getIronArmor(EquipmentSlot slot){
        ItemStack item = null;

        if(slot.equals(EquipmentSlot.CHEST)){
            item = new ItemStack(Material.IRON_CHESTPLATE);
        }
        if(slot.equals(EquipmentSlot.LEGS)){
            item = new ItemStack(Material.IRON_LEGGINGS);
        }
        if(slot.equals(EquipmentSlot.HEAD)){
            item = new ItemStack(Material.IRON_HELMET);
        }
        if(slot.equals(EquipmentSlot.FEET)){
            item = new ItemStack(Material.IRON_BOOTS);
        }
        if(item == null){
            item = new ItemStack(Material.IRON_BOOTS);
        }
        ItemMeta meta = item.getItemMeta();
        EntityEnchantment.addRandomLevelEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, item, 4);
        EntityEnchantment.addRandomLevelEnchant(Enchantment.THORNS, item, 3);
        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack getChainMailArmor(EquipmentSlot slot){
        ItemStack item = null;

        if(slot.equals(EquipmentSlot.CHEST)){
            item = new ItemStack(Material.CHAINMAIL_CHESTPLATE);
        }
        if(slot.equals(EquipmentSlot.LEGS)){
            item = new ItemStack(Material.CHAINMAIL_LEGGINGS);
        }
        if(slot.equals(EquipmentSlot.HEAD)){
            item = new ItemStack(Material.CHAINMAIL_HELMET);
        }
        if(slot.equals(EquipmentSlot.FEET)){
            item = new ItemStack(Material.CHAINMAIL_BOOTS);
        }
        if(item == null){
            item = new ItemStack(Material.CHAINMAIL_BOOTS);
        }
        ItemMeta meta = item.getItemMeta();
        EntityEnchantment.addRandomLevelEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, item, 2);
        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack getLeatherArmor(EquipmentSlot slot){
        ItemStack item = null;

        if(slot.equals(EquipmentSlot.CHEST)){
            item = new ItemStack(Material.LEATHER_CHESTPLATE);
        }
        if(slot.equals(EquipmentSlot.LEGS)){
            item = new ItemStack(Material.LEATHER_LEGGINGS);
        }
        if(slot.equals(EquipmentSlot.HEAD)){
            item = new ItemStack(Material.LEATHER_HELMET);
        }
        if(slot.equals(EquipmentSlot.FEET)){
            item = new ItemStack(Material.LEATHER_BOOTS);
        }
        if(item == null){
            item = new ItemStack(Material.LEATHER_BOOTS);
        }
        ItemMeta meta = item.getItemMeta();
        EntityEnchantment.addRandomLevelEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, item, 3);
        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack getGoldArmor(EquipmentSlot slot){
        ItemStack item = null;

        if(slot.equals(EquipmentSlot.CHEST)){
            item = new ItemStack(Material.GOLDEN_CHESTPLATE);
        }
        if(slot.equals(EquipmentSlot.LEGS)){
            item = new ItemStack(Material.GOLDEN_LEGGINGS);
        }
        if(slot.equals(EquipmentSlot.HEAD)){
            item = new ItemStack(Material.GOLDEN_HELMET);
        }
        if(slot.equals(EquipmentSlot.FEET)){
            item = new ItemStack(Material.GOLDEN_BOOTS);
        }
        if(item == null){
            item = new ItemStack(Material.GOLDEN_BOOTS);
        }
        ItemMeta meta = item.getItemMeta();
        EntityEnchantment.addRandomLevelEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, item, 3);
        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack getDiamondArmor(EquipmentSlot slot){
        ItemStack item = null;

        if(slot.equals(EquipmentSlot.CHEST)){
            item = new ItemStack(Material.DIAMOND_CHESTPLATE);
        }
        if(slot.equals(EquipmentSlot.LEGS)){
            item = new ItemStack(Material.DIAMOND_LEGGINGS);
        }
        if(slot.equals(EquipmentSlot.HEAD)){
            item = new ItemStack(Material.DIAMOND_HELMET);
        }
        if(slot.equals(EquipmentSlot.FEET)){
            item = new ItemStack(Material.DIAMOND_BOOTS);
        }
        if(item == null){
            item = new ItemStack(Material.DIAMOND_BOOTS);
        }
        ItemMeta meta = item.getItemMeta();
        if(item.getType().equals(Material.DIAMOND_BOOTS)){
            EntityEnchantment.addRandomLevelEnchant(Enchantment.DEPTH_STRIDER, item, 3);
        }
        EntityEnchantment.addRandomLevelEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, item, 4);
        EntityEnchantment.addRandomLevelEnchant(Enchantment.THORNS, item, 3);
        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack getNetheriteArmor(EquipmentSlot slot){
        ItemStack item = null;

        if(slot.equals(EquipmentSlot.CHEST)){
            item = new ItemStack(Material.NETHERITE_CHESTPLATE);
        }
        if(slot.equals(EquipmentSlot.LEGS)){
            item = new ItemStack(Material.NETHERITE_LEGGINGS);
        }
        if(slot.equals(EquipmentSlot.HEAD)){
            item = new ItemStack(Material.NETHERITE_HELMET);
        }
        if(slot.equals(EquipmentSlot.FEET)){
            item = new ItemStack(Material.NETHERITE_BOOTS);
        }
        if(item == null){
            item = new ItemStack(Material.NETHERITE_BOOTS);
        }
        ItemMeta meta = item.getItemMeta();
        if(item.getType().equals(Material.NETHERITE_BOOTS)){
            EntityEnchantment.addRandomLevelEnchant(Enchantment.DEPTH_STRIDER, item, 4);
        }
        EntityEnchantment.addRandomLevelEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, item, 5);
        EntityEnchantment.addRandomLevelEnchant(Enchantment.THORNS, item, 4);
        item.setItemMeta(meta);

        return item;
    }
}
