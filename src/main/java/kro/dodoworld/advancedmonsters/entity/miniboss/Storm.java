package kro.dodoworld.advancedmonsters.entity.miniboss;

import kro.dodoworld.advancedmonsters.AdvancedMonsters;
import kro.dodoworld.advancedmonsters.event.MonsterAbilityUnlockEvent;
import kro.dodoworld.advancedmonsters.util.AdvancedUtils;
import kro.dodoworld.advancedmonsters.modifier.ability.MonsterAbility;
import kro.dodoworld.advancedmonsters.util.Skulls;
import kro.dodoworld.advancedmonsters.util.BlockUtilMethods;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LightningStrike;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class Storm implements Listener {
    public static void createStorm(Location loc){
        Random rnd = new Random();
        Skeleton storm = loc.getWorld().spawn(loc, Skeleton.class, CreatureSpawnEvent.SpawnReason.CUSTOM);
        storm.setCustomNameVisible(true);
        storm.getWorld().setStorm(true);
        storm.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(380);
        storm.setHealth(380);
        storm.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(15);
        storm.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).setBaseValue(12);
        storm.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(15);
        storm.customName(getName());
        storm.getEquipment().setHelmet(new ItemStack(Skulls.getSkull("https://textures.minecraft.net/texture/b914cf5106aaa82409fdd9213fbdb1479b4d65aecc5d5e22b1f25e5744c4c4f7")));
        storm.getEquipment().setHelmetDropChance(0f);
        storm.getEquipment().setChestplate(getBlueArmor(Material.LEATHER_CHESTPLATE));
        storm.getEquipment().setChestplateDropChance(0f);
        storm.getEquipment().setLeggings(getBlueArmor(Material.LEATHER_LEGGINGS));
        storm.getEquipment().setLeggingsDropChance(0f);
        storm.getEquipment().setBoots(getBlueArmor(Material.LEATHER_BOOTS));
        storm.getEquipment().setBootsDropChance(0f);
        storm.getEquipment().setItemInMainHand(getSword());
        storm.getEquipment().setItemInMainHandDropChance(0f);
        storm.setCanPickupItems(false);
        storm.addScoreboardTag("adm_miniboss_storm");
        storm.addScoreboardTag("adm_remove_when_reload");
        new BukkitRunnable(){
            int i = 0;
            @Override
            public void run() {
                if(storm.isDead()){
                    if(storm.getKiller() == null){
                        cancel();
                        return;
                    }
                    if(!AdvancedUtils.isUnlocked(MonsterAbility.STORMY)){
                        if(storm.getKiller().isDead()){ cancel(); return; }
                        MonsterAbilityUnlockEvent event = new MonsterAbilityUnlockEvent(MonsterAbility.STORMY);
                        Bukkit.getServer().getPluginManager().callEvent(event);
                        if(!event.isCancelled()) {
                            AdvancedUtils.setUnlocked(event.getAbility(), true);
                        }
                    }
                    if(!AdvancedUtils.isUnlocked(MonsterAbility.LIGHTING)){
                        MonsterAbilityUnlockEvent event = new MonsterAbilityUnlockEvent(MonsterAbility.LIGHTING);
                        Bukkit.getServer().getPluginManager().callEvent(event);
                        if(!event.isCancelled()) {
                            AdvancedUtils.setUnlocked(event.getAbility(), true);
                        }
                    }
                    cancel();

                    return;
                }
                if(storm.getTarget() != null && !storm.getTarget().isDead()){
                    if(i % 180 == 0){
                        for(int j = 0; j<(int) (Math.random() * 5); j++){
                            Location loc = storm.getLocation().add(rnd.nextDouble(1, 8), 0, rnd.nextDouble(1, 8));
                            Block block = storm.getWorld().getHighestBlockAt(loc);
                            createLightingAura(block.getLocation().add(0, 1.1, 0), getRadius(4), 100, AdvancedMonsters.getPlugin(AdvancedMonsters.class), storm);
                        }
                    }
                }
                if(i >= Integer.MAX_VALUE - 1000000){
                    i = 0;
                }
                i++;
            }
        }.runTaskTimer(AdvancedMonsters.getPlugin(AdvancedMonsters.class), 0L, 1L);
    }

    private static int getRadius(int max){
        Random rnd = new Random();
        return rnd.nextInt(1, max + 1);
    }


    private static ItemStack getSword(){
        ItemStack stack = new ItemStack(Material.DIAMOND_SHOVEL);
        ItemMeta meta = stack.getItemMeta();
        meta.addEnchant(Enchantment.MENDING, 1, false);
        meta.setUnbreakable(true);
        stack.setItemMeta(meta);

        return stack;
    }

    private static Component getName(){
        return Component.text("⚛MINIBOSS ").color(TextColor.color(219, 42, 216)).decorate(TextDecoration.BOLD).append(Component.text("Storm").color(TextColor.color(39, 198, 242)).decorate(TextDecoration.BOLD));
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event){
        if(!(event.getDamager() instanceof Skeleton)) return;
        if(!(event.getEntity() instanceof LivingEntity)) return;
        if(!event.getDamager().getScoreboardTags().contains("adm_miniboss_storm")) return;
        int random = (int) (Math.random() * 100);
        if(random <= 50){
            event.getEntity().getWorld().strikeLightningEffect(event.getEntity().getLocation());
            ((LivingEntity) event.getEntity()).damage(6, event.getDamager());
        }
    }

    private static ItemStack getBlueArmor(Material material){
        ItemStack stack = new ItemStack(material);
        LeatherArmorMeta meta = (LeatherArmorMeta) stack.getItemMeta();
        meta.setColor(org.bukkit.Color.fromRGB(33, 133, 176));
        meta.setUnbreakable(true);
        meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, false);
        if(material.equals(Material.LEATHER_BOOTS)){
            meta.addEnchant(Enchantment.DEPTH_STRIDER, 3, false);
        }
        stack.setItemMeta(meta);
        return stack;
    }



    private static void createLightingAura(Location loc, int radius, final int ticks, Plugin plugin, Mob attacker){
        new BukkitRunnable(){
            int i = 0;
            @Override
            public void run() {
                attacker.getPathfinder().moveTo(loc);
                BlockUtilMethods.createCircle(loc, radius, 13, 163, 158, 1f);
                if(i >= ticks){
                    for(Block block : BlockUtilMethods.getNearbyBlocks(loc.getBlock(), radius - 1)){
                        LightningStrike strike = block.getWorld().strikeLightningEffect(block.getLocation());
                        strike.addScoreboardTag("adm_entity_lighting_aura");
                        for(Entity entity : strike.getNearbyEntities(radius, radius, radius)){
                            if(!(entity instanceof Monster) && entity instanceof LivingEntity){
                                ((LivingEntity) entity).damage(100, strike);
                            }
                        }
                    }
                    cancel();
                    return;
                }
                i++;
            }
        }.runTaskTimer(plugin, 0L, 1L);
    }
}
