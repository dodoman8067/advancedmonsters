package kro.dodoworld.advancedmonsters.entity.miniboss;

import kro.dodoworld.advancedmonsters.AdvancedMonsters;
import kro.dodoworld.advancedmonsters.event.MonsterAbilityUnlockEvent;
import kro.dodoworld.advancedmonsters.util.AdvancedUtils;
import kro.dodoworld.advancedmonsters.modifier.ability.MonsterAbility;
import kro.dodoworld.advancedmonsters.util.Skulls;
import kro.dodoworld.advancedmonsters.util.BlockUtilMethods;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

public class EarthQuaker implements Listener {

    public static void createEarthQuaker(Location loc){
        Skeleton earthQuaker = loc.getWorld().spawn(loc, Skeleton.class, CreatureSpawnEvent.SpawnReason.CUSTOM);
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
        earthQuaker.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(17);
        earthQuaker.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).setBaseValue(16);
        earthQuaker.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(320);
        earthQuaker.addScoreboardTag("adm_remove_when_reload");
        earthQuaker.addScoreboardTag("adm_miniboss_earthquaker");
        earthQuaker.setCustomNameVisible(true);
        earthQuaker.customName(getName());
        earthQuaker.setHealth(320);
        earthQuaker.setPersistent(true);
        new BukkitRunnable(){
            int i = 0;

            @Override
            public void run() {
                if(earthQuaker.isDead()){
                    if(earthQuaker.getKiller() == null){
                        cancel();
                        return;
                    }
                    if(!AdvancedUtils.isUnlocked(MonsterAbility.TANK)){
                        if(earthQuaker.isValid()){ cancel(); return; }
                        MonsterAbilityUnlockEvent event = new MonsterAbilityUnlockEvent(MonsterAbility.TANK);
                        if(earthQuaker.getKiller().getHealth() <= 0){ cancel(); return; }
                        Bukkit.getServer().getPluginManager().callEvent(event);
                        if(!event.isCancelled()) {
                            AdvancedUtils.setUnlocked(event.getAbility(), true);
                        }
                    }
                    cancel();
                    return;
                }
                if(earthQuaker.getTarget() != null && !earthQuaker.getTarget().isDead()){
                    if(i % 180 == 0){
                        new BukkitRunnable() {
                            final Location location = earthQuaker.getLocation().subtract(0, 1, 0);
                            int rad = 1;
                            @Override
                            public void run() {
                                for (Location loc : BlockUtilMethods.getCircle(location, rad, (rad * ((int) (Math.PI * 2))))) {
                                    if(loc.getBlock().getType().getBlastResistance() >= 3000000) continue;
                                    FallingBlock fb = loc.getWorld().spawnFallingBlock(loc, loc.getBlock().getBlockData());
                                    fb.setHurtEntities(true);
                                    fb.addScoreboardTag("adm_remove_when_reload");
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
                    if(i % 360 == 0){
                        for(int i1 = 0; i1<(int) (Math.random() * 5); i1++){
                            createGravityOrb(earthQuaker.getLocation().add(Math.random() * 30, 0, Math.random() * 30));
                        }
                    }
                }
                if(i >= (Integer.MAX_VALUE - 10000)){
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

    private static void createGravityOrb(Location loc){
        ArmorStand orb = loc.getWorld().spawn(loc, ArmorStand.class);
        orb.setInvulnerable(true);
        orb.setGravity(false);
        orb.setInvisible(true);
        orb.setVisible(false);
        orb.setBasePlate(false);
        orb.setCustomNameVisible(true);
        orb.addScoreboardTag("adm_remove_when_reload");
        orb.customName(Component.text("중력 오브").color(TextColor.color(0xAA00AA)).decorate(TextDecoration.BOLD));
        orb.setHeadPose(new EulerAngle(56, 36, 34));
        orb.getEquipment().setHelmet(Skulls.getSkull("https://textures.minecraft.net/texture/78cf2596fb4cca004ee79561a062819c247cb4ba711d9540970e4398d9dbac43"));
        orb.addDisabledSlots(EquipmentSlot.values());
        new BukkitRunnable(){
            int i = 0;
            @Override
            public void run() {
                if(i>=280){
                    orb.remove();
                    cancel();
                }else{
                    EulerAngle rot = orb.getHeadPose();
                    EulerAngle rowNew = rot.add(0, 0, 0.6);
                    orb.setHeadPose(rowNew);
                    orb.getWorld().spawnParticle(Particle.SPELL_WITCH, orb.getEyeLocation(), 7, 0, 0, 0, 0, null);
                    for(Entity entity : orb.getNearbyEntities(20, 20, 20)){
                        if(entity.getScoreboardTags().contains("adm_miniboss_earthquaker")) continue;
                        entity.setVelocity(entity.getVelocity().clone().add(orb.getLocation().clone().toVector().subtract(entity.getLocation().clone().toVector()).multiply(0.005)));
                        entity.sendMessage(Component.text("중력 오브").color(TextColor.color(0xAA00AA)).decorate(TextDecoration.BOLD).append(Component.text(" 에 이끌리고 있습니다!").color(NamedTextColor.LIGHT_PURPLE)));
                    }
                }
                i++;
            }
        }.runTaskTimer(AdvancedMonsters.getPlugin(AdvancedMonsters.class), 0, 1);
    }
}
