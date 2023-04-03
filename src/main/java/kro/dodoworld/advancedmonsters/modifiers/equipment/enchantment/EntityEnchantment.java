package kro.dodoworld.advancedmonsters.modifiers.equipment.enchantment;

import kro.dodoworld.advancedmonsters.modifiers.equipment.EntityEquipment;
import kro.dodoworld.advancedmonsters.util.AdvancedMonstersUtilMethods;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class EntityEnchantment {
    public static void addRandomLevelEnchant(Enchantment enchantment, ItemStack stack, int max){
        ItemMeta meta = stack.getItemMeta();
        int level = (int) (Math.random() * max);
        if(level != 0 || !(level > max)){
            meta.addEnchant(enchantment, max, true);
            stack.setItemMeta(meta);
        }
    }

    public static double getRandom(World world, double max){
        double level = AdvancedMonstersUtilMethods.getEquipmentLevel().getMonsterEquipmentLevel(world);
        double returnValue = level * (Math.random() * 2);
        if(returnValue <= 0){
            returnValue = 1;
        }
        return Math.min(returnValue, max);
    }
}
