package kro.dodoworld.advancedmonsters.modifiers.equipment.armor;

import kro.dodoworld.advancedmonsters.AdvancedMonsters;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Random;

public class EntityArmor {

    public static ItemStack getRandomArmor(World world, EquipmentSlot slot){
        double level = AdvancedMonsters.getMonsterLevel().getMonsterEquipmentLevel(world);
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
        if(level >= 28.8 && level <= 39.7){
            int type = (int) (Math.random() * 2);

            if(type == 0){
                item = getChainMailArmor(slot);
            }
            if(type == 1){
                item = getIronArmor(slot);
            }
        }
        if(level >= 39.8 && level <= 49.7){
            int chance = (int) (Math.random() * 100);

            if(chance <= 1){
                item = getDiamondArmor(slot);
            }else{
                item = getIronArmor(slot);
            }
        }
        if(level >= 49.8 && level <= 65.3){
            int chance = (int) (Math.random() * 100);

            if(chance <= 40){
                item = getIronArmor(slot);
            }else{
                item = getDiamondArmor(slot);
            }

        }
        if(level >= 65.4 && level <= 80.0){
            int chance = (int) (Math.random() * 100);

            if(chance <= 2){
                item = getNetheriteArmor(slot);
            }else{
                item = getDiamondArmor(slot);
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
        addRandomLevelEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, item, 4);
        addRandomLevelEnchant(Enchantment.THORNS, item, 3);

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
        addRandomLevelEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, item, 2);

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
        addRandomLevelEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, item, 3);

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
        addRandomLevelEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, item, 3);

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
        if(item.getType().equals(Material.DIAMOND_BOOTS)){
            addRandomLevelEnchant(Enchantment.DEPTH_STRIDER, item, 3);
        }
        addRandomLevelEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, item, 4);
        addRandomLevelEnchant(Enchantment.THORNS, item, 3);

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
        if(item.getType().equals(Material.NETHERITE_BOOTS)){
            addRandomLevelEnchant(Enchantment.DEPTH_STRIDER, item, 4);
        }
        addRandomLevelEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, item, 5);
        addRandomLevelEnchant(Enchantment.THORNS, item, 4);

        return item;
    }

    private static void addRandomLevelEnchant(Enchantment enchantment, ItemStack stack, int max){
        ItemMeta meta = stack.getItemMeta();
        Random random = new Random();
        int level = random.nextInt(0, max);
        if(level != 0){
            meta.addEnchant(enchantment, level, true);
            stack.setItemMeta(meta);
        }
    }
}
