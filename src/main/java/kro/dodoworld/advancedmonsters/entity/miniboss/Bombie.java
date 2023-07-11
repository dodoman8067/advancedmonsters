package kro.dodoworld.advancedmonsters.entity.miniboss;

import kro.dodoworld.advancedmonsters.AdvancedMonsters;
import kro.dodoworld.advancedmonsters.event.MonsterAbilityUnlockEvent;
import kro.dodoworld.advancedmonsters.util.AdvancedUtils;
import kro.dodoworld.advancedmonsters.modifier.ability.MonsterAbility;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.SpawnCategory;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.scheduler.BukkitRunnable;

public class Bombie implements Listener {
    public static void createBombie(Location loc){
        Zombie bombie = loc.getWorld().spawn(loc, Zombie.class, CreatureSpawnEvent.SpawnReason.CUSTOM);
        bombie.customName(Component.text("⚛MINIBOSS ").color(TextColor.color(219, 42, 216)).decorate(TextDecoration.BOLD).append(Component.text("Bombie").color(TextColor.color(0xFF5555))));
        bombie.setCustomNameVisible(true);
        bombie.setCanBreakDoors(true);
        bombie.addScoreboardTag("adm_miniboss_bombie");
        bombie.addScoreboardTag("adm_remove_when_reload");
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
        bombie.setPersistent(true);
        bombie.getEquipment().setItemInMainHand(new ItemStack(Material.BLAZE_ROD));
        bombie.getEquipment().setItemInMainHandDropChance(0f);
        bombie.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(bombie.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getValue() * 1.5);
        new BukkitRunnable(){
            int i = 0;
            @Override
            public void run() {
                if(bombie.isDead()){
                    if(bombie.getKiller() == null){
                        cancel();
                        return;
                    }
                    if(!AdvancedUtils.isUnlocked(MonsterAbility.BOMBER)){
                        if(bombie.isValid()){ cancel(); return; }
                        MonsterAbilityUnlockEvent event = new MonsterAbilityUnlockEvent(MonsterAbility.BOMBER);
                        if(bombie.getKiller().getHealth() <= 0) return;
                        Bukkit.getServer().getPluginManager().callEvent(event);
                        if(!event.isCancelled()) {
                            AdvancedUtils.setUnlocked(event.getAbility(), true);
                        }
                    }
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
                }
                if(i >= (Integer.MAX_VALUE - 100)){
                    i = 0;
                }
                i++;
            }
        }.runTaskTimer(AdvancedMonsters.getPlugin(AdvancedMonsters.class), 0L, 1L);
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
        if(!event.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_EXPLOSION)) return;
        if(!(event.getDamager() instanceof TNTPrimed)) return;
        if(!event.getDamager().getScoreboardTags().contains("adm_bombie_tnt")) return;
        if(event.getEntity().getSpawnCategory().equals(SpawnCategory.MONSTER)) {
            event.setCancelled(true);
        }else{
            event.setDamage(event.getFinalDamage() * 3);
        }
    }
}
