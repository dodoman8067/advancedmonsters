package kro.dodoworld.advancedmonsters.entity.boss;

import kro.dodoworld.advancedmonsters.util.Skulls;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.WitherSkeleton;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Necromancer {
    public static void spawnNecromancer(Location loc){
        WitherSkeleton necromancer = loc.getWorld().spawn(loc, WitherSkeleton.class, CreatureSpawnEvent.SpawnReason.CUSTOM);
        necromancer.getEquipment().setHelmet(Skulls.getSkull("https://textures.minecraft.net/texture/e0686a0d939bc9acf1ce1f668d4084e7e64d615553fb1c36f5127b41c8cb7911"));
        necromancer.getEquipment().setHelmetDropChance(0f);
        necromancer.getEquipment().setChestplate(getNetheriteArmor(Material.NETHERITE_CHESTPLATE));
        necromancer.getEquipment().setChestplateDropChance(0f);
        necromancer.getEquipment().setLeggings(getNetheriteArmor(Material.NETHERITE_LEGGINGS));
        necromancer.getEquipment().setLeggingsDropChance(0f);
        necromancer.getEquipment().setBoots(getNetheriteArmor(Material.NETHERITE_BOOTS));
        necromancer.getEquipment().setBootsDropChance(0f);
        necromancer.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(25);
        necromancer.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).setBaseValue(20);
        necromancer.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(1000);
        necromancer.setHealth(1000);
        necromancer.setShouldBurnInDay(false);
        necromancer.setCanPickupItems(false);


    }


    private static ItemStack getNetheriteArmor(Material material){
        ItemStack stack = new ItemStack(material);
        ItemMeta meta = stack.getItemMeta();
        meta.setUnbreakable(true);
        meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4, false);
        stack.setItemMeta(meta);
        return stack;
    }
}
