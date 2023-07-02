package kro.dodoworld.advancedmonsters.util;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.Base64;
import java.util.UUID;

/**
 * Utility class for custom player heads.
 */
public class Skulls {
    /**
     * Returns skull ItemStack instance.
     * @param url link to minecraft player skin
     * @return skull instance
     */
    public static ItemStack getSkull(String url) {
        //Creates player head ItemStack instance with 1 amount
        ItemStack skull = new ItemStack(Material.PLAYER_HEAD, 1);
        if (url == null || url.isEmpty())
            //Returns unmodified skull when url is null or empty
            return skull;
        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        //Modify skull with skin from url
        byte[] encodedData = Base64.getEncoder().encode(String.format("{textures:{SKIN:{url:\"%s\"}}}", url).getBytes());
        profile.getProperties().put("textures", new Property("textures", new String(encodedData)));
        Field profileField = null;
        try {
            profileField = skullMeta.getClass().getDeclaredField("profile");
        } catch (NoSuchFieldException | SecurityException e) {
            e.printStackTrace();
        }
        profileField.setAccessible(true);
        try {
            profileField.set(skullMeta, profile);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
        skull.setItemMeta(skullMeta);
        return skull;
    }
}
