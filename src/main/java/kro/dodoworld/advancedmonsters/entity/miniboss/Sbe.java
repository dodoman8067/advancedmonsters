package kro.dodoworld.advancedmonsters.entity.miniboss;

import com.destroystokyo.paper.profile.CraftPlayerProfile;
import kro.dodoworld.advancedmonsters.AdvancedMonsters;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.PolarBear;
import org.bukkit.entity.Stray;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class Sbe implements Listener {
    public static void createSbe(Location loc){
        Stray sbe = loc.getWorld().spawn(loc, Stray.class, CreatureSpawnEvent.SpawnReason.CUSTOM);
        sbe.customName(Component.text("⚛MINIBOSS ").color(TextColor.color(219, 42, 216)).decorate(TextDecoration.BOLD)
                .append(Component.text("S_be").color(NamedTextColor.WHITE).decorate(TextDecoration.BOLD)));
        sbe.setCustomNameVisible(true);
        sbe.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(80);
        sbe.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.2f);
        sbe.getEquipment().setItemInMainHand(getBow());
        sbe.getEquipment().setItemInMainHandDropChance(0f);
        sbe.getEquipment().setHelmet(getSkull());
        sbe.getEquipment().setHelmetDropChance(0f);
        sbe.getEquipment().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
        sbe.getEquipment().setChestplateDropChance(0f);
        sbe.getEquipment().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
        sbe.getEquipment().setLeggingsDropChance(0f);
        sbe.getEquipment().setBoots(new ItemStack(Material.IRON_BOOTS));
        sbe.getEquipment().setBootsDropChance(0f);
        sbe.setHealth(80);
        sbe.addScoreboardTag("adm_remove_when_reload");
        sbe.addScoreboardTag("adm_miniboss_sbe");
        PolarBear bear = loc.getWorld().spawn(loc, PolarBear.class);
        bear.setStanding(false);
        bear.setAdult();
        bear.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(10);
        bear.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(150);
        bear.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.3f);
        bear.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).setBaseValue(5);
        bear.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(25);
        bear.setHealth(150);
        bear.addScoreboardTag("adm_remove_when_reload");
        bear.addScoreboardTag("adm_miniboss_sbe");
        bear.addPassenger(sbe);
        new BukkitRunnable(){

            @Override
            public void run() {
                if(sbe.isDead() || bear.isDead()){
                    cancel();
                }
                if(sbe.getTarget() != null){
                    bear.setTarget(sbe.getTarget());
                }
            }
        }.runTaskTimer(AdvancedMonsters.getPlugin(AdvancedMonsters.class), 0L, 1L);
    }

    private static ItemStack getBow(){
        ItemStack stack = new ItemStack(Material.BOW);
        ItemMeta meta = stack.getItemMeta();
        meta.setUnbreakable(true);
        meta.displayName(Component.text("S_be의 활").color(NamedTextColor.WHITE).decorate(TextDecoration.BOLD));
        meta.addEnchant(Enchantment.ARROW_DAMAGE, 5, false);
        stack.setItemMeta(meta);
        return stack;
    }

    private static ItemStack getSkull(){
        ItemStack stack = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) stack.getItemMeta();
        meta.setUnbreakable(true);
        meta.displayName(Component.text("S_be의 머리").color(NamedTextColor.WHITE).decorate(TextDecoration.BOLD));
        meta.setPlayerProfile(new CraftPlayerProfile(UUID.fromString("7aa895ed-8413-483e-ab41-d6032e4fdb9f"), "S_be"));
        stack.setItemMeta(meta);
        return stack;
    }
}
