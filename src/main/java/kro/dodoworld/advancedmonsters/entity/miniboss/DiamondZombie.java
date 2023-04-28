package kro.dodoworld.advancedmonsters.entity.miniboss;

import kro.dodoworld.advancedmonsters.event.MonsterAbilityUnlockEvent;
import kro.dodoworld.advancedmonsters.util.AdvancedMonstersUtilMethods;
import kro.dodoworld.advancedmonsters.util.MonsterAbility;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.minecraft.world.damagesource.DamageSource;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftLivingEntity;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class DiamondZombie implements Listener {
    public static void createZombie(Location loc){
        Zombie zombie = loc.getWorld().spawn(loc, Zombie.class, CreatureSpawnEvent.SpawnReason.CUSTOM);
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
        zombie.addScoreboardTag("adm_remove_when_reload");
        zombie.customName(Component.text("⚛MINIBOSS ").color(TextColor.color(219, 42, 216)).decorate(TextDecoration.BOLD).append(Component.text("Diamond Zombie").color(TextColor.color(0xAAAAAA))));
        zombie.setCustomNameVisible(true);
    }

    @EventHandler
    public void onDeath(EntityDeathEvent event){
        if(event.getEntity().getKiller() == null) return;
        if(!(event.getEntity().getKiller() instanceof Player)) return;
        if(!(event.getEntity() instanceof Zombie)) return;
        if(!event.getEntity().getScoreboardTags().contains("adm_miniboss_diamond_zombie")) return;
        if(!AdvancedMonstersUtilMethods.isUnlocked(MonsterAbility.SPEEDY)){
            MonsterAbilityUnlockEvent monsterAbilityUnlockEvent = new MonsterAbilityUnlockEvent(MonsterAbility.SPEEDY);
            Bukkit.getServer().getPluginManager().callEvent(monsterAbilityUnlockEvent);
            if(monsterAbilityUnlockEvent.isCancelled()) return;
            AdvancedMonstersUtilMethods.setUnlocked(monsterAbilityUnlockEvent.getAbility(), true);
        }
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event){
        if(!event.getDamager().getScoreboardTags().contains("adm_miniboss_diamond_zombie")) return;
        if(!(event.getEntity() instanceof LivingEntity living)) return;
        event.setCancelled(true);
        ((CraftLivingEntity) living).getHandle().hurt(DamageSource.MAGIC, (float) event.getFinalDamage() * 1.2f);
    }
}
