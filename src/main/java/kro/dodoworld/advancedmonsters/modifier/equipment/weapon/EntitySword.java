package kro.dodoworld.advancedmonsters.modifier.equipment.weapon;

import kro.dodoworld.advancedmonsters.AdvancedMonsters;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Random;

public class EntitySword {

    private static final Random RANDOM = new Random();

    public static ItemStack getRandomSword(World world){
        double level = AdvancedMonsters.getMonsterLevel().getMonsterEquipmentLevel(world);
        ItemStack item = null;
        if(level >= 1.0 && level <= 3.0){
            item = getWoodenSword();
        }
        if(level >= 3.1 && level <= 7.0){
            int type = (int) (Math.random() * 2);
            if(type == 0){
                item = getWoodenSword();
            }
            if(type == 1){
                item = getGoldSword();
            }
        }
        if(level >= 7.1 && level <= 15.4){
            int type = (int) (Math.random() * 3);
            if(type == 0){
                item = getUpgradedWoodenSword();
            }
            if(type == 1){
                item = getGoldSword();
            }
            if(type == 2){
                item = getWoodenSword();
            }
        }
        if(level >= 15.5 && level <= 28.7){
            int type = (int) (Math.random() * 3);
            if(type == 0){
                item = getUpgradedWoodenSword();
            }
            if(type == 1){
                item = getGoldSword();
            }
            if(type == 2){
                item = getIronSword();
            }
        }
        if(level >= 28.8 && level <= 39.7){
            int type = (int) (Math.random() * 2);

            if(type == 0){
                item = getGoldSword();
            }
            if(type == 1){
                item = getIronSword();
            }
        }
        if(level >= 39.8 && level <= 49.7){
            int chance = (int) (Math.random() * 100);

            if(chance <= 10){
                item = getDiamondSword();
            }else{
                item = getIronSword();
            }
        }
        if(level >= 49.8 && level <= 65.3){
            int chance = (int) (Math.random() * 100);

            if(chance <= 40){
                item = getIronSword();
            }else{
                item = getDiamondSword();
            }

        }
        if(level >= 65.4 && level <= 80.0){
            int chance = (int) (Math.random() * 100);

            if(chance <= 20){
                item = getNetheriteSword();
            }else{
                item = getDiamondSword();
            }

        }
        if(level >= 80.1){
            item = getNetheriteSword();
        }
        return item;
    }

    public static ItemStack getIronSword(){
        ItemStack item;
        if(RANDOM.nextInt(0, 2) == 1){
            item = new ItemStack(Material.IRON_AXE);
        }else{
            item = new ItemStack(Material.IRON_SWORD);
        }
        addRandomLevelEnchant(Enchantment.DAMAGE_ALL, item, 4);
        addRandomLevelEnchant(Enchantment.SWEEPING_EDGE, item, 3);
        addRandomLevelEnchant(Enchantment.FIRE_ASPECT, item, 1);

        return item;
    }

    public static ItemStack getUpgradedWoodenSword(){
        ItemStack item;
        if(RANDOM.nextInt(0, 2) == 1){
            item = new ItemStack(Material.WOODEN_AXE);
        }else{
            item = new ItemStack(Material.WOODEN_SWORD);
        }
        addRandomLevelEnchant(Enchantment.DAMAGE_ALL, item, 2);
        addRandomLevelEnchant(Enchantment.SWEEPING_EDGE, item, 1);

        return item;
    }

    public static ItemStack getWoodenSword(){
        ItemStack item;
        if(RANDOM.nextInt(0, 2) == 1){
            item = new ItemStack(Material.WOODEN_AXE);
        }else{
            item = new ItemStack(Material.WOODEN_SWORD);
        }
        addRandomLevelEnchant(Enchantment.DAMAGE_ALL, item, 1);

        return item;
    }

    public static ItemStack getGoldSword(){
        ItemStack item;
        if(RANDOM.nextInt(0, 2) == 1){
            item = new ItemStack(Material.GOLDEN_AXE);
        }else{
            item = new ItemStack(Material.GOLDEN_SWORD);
        }
        addRandomLevelEnchant(Enchantment.DAMAGE_ALL, item, 3);
        addRandomLevelEnchant(Enchantment.SWEEPING_EDGE, item, 2);
        addRandomLevelEnchant(Enchantment.FIRE_ASPECT, item, 2);

        return item;
    }

    public static ItemStack getDiamondSword(){
        ItemStack item;
        if(RANDOM.nextInt(0, 2) == 1){
            item = new ItemStack(Material.DIAMOND_AXE);
        }else{
            item = new ItemStack(Material.DIAMOND_SWORD);
        }
        addRandomLevelEnchant(Enchantment.DAMAGE_ALL, item, 5);
        addRandomLevelEnchant(Enchantment.SWEEPING_EDGE, item, 3);
        addRandomLevelEnchant(Enchantment.FIRE_ASPECT, item, 2);

        return item;
    }

    public static ItemStack getNetheriteSword(){
        ItemStack item;
        if(RANDOM.nextInt(0, 2) == 1){
            item = new ItemStack(Material.NETHERITE_AXE);
        }else{
            item = new ItemStack(Material.NETHERITE_SWORD);
        }
        addRandomLevelEnchant(Enchantment.DAMAGE_ALL, item, 6);
        addRandomLevelEnchant(Enchantment.SWEEPING_EDGE, item, 4);
        addRandomLevelEnchant(Enchantment.FIRE_ASPECT, item, 3);

        return item;
    }

    private static void addRandomLevelEnchant(Enchantment enchantment, ItemStack stack, int max){
        ItemMeta meta = stack.getItemMeta();
        int level = RANDOM.nextInt(0, max + 1);
        if(level != 0){
            meta.addEnchant(enchantment, level, true);
            stack.setItemMeta(meta);
        }
    }
}
