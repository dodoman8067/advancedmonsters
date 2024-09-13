package kro.dodoworld.advancedabilities.ability;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import kro.dodoworld.advancedmonsters.AdvancedMonsters;
import kro.dodoworld.advancedmonsters.core.registry.Registrable;
import kro.dodoworld.advancedmonsters.core.registry.Registry;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashSet;
import java.util.Set;

public class AbilityManager {
    private static final AbilityManager MANAGER = new AbilityManager();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private AbilityManager(){}

    public void applyAbility(ItemStack item, ItemAbility ability, int tier, boolean override) {
        if (item == null || item.getItemMeta() == null) return;

        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        NamespacedKey key = new NamespacedKey(AdvancedMonsters.getPlugin(AdvancedMonsters.class), "item_abilities");

        JsonArray abilitiesArray = new JsonArray();
        if(pdc.has(key, PersistentDataType.STRING)){
            String jsonString = pdc.get(key, PersistentDataType.STRING);
            abilitiesArray = GSON.fromJson(jsonString, JsonArray.class);
        }

        JsonObject newAbility = new JsonObject();
        newAbility.addProperty("id", ability.getId().value());
        newAbility.addProperty("tier", tier);

        boolean abilityExists = false;
        for(JsonElement element : abilitiesArray){
            if(element.isJsonObject()){
                JsonObject existingAbility = element.getAsJsonObject();
                if(existingAbility.has("id") && existingAbility.get("id").getAsString().equals(ability.getId().value())){
                    abilityExists = true;
                    /*
                    if(existingAbility.get("tier").getAsInt() == tier){
                        existingAbility.addProperty("tier", tier + 1);
                    }
                    */
                    if(override){
                        existingAbility.addProperty("tier", tier);
                    }
                    break;
                }
            }
        }

        if(!abilityExists){
            abilitiesArray.add(newAbility);
        }

        pdc.set(key, PersistentDataType.STRING, GSON.toJson(abilitiesArray));

        item.setItemMeta(meta);
    }

    public boolean hasAbility(ItemStack item, ItemAbility ability){
        if(item == null || item.getItemMeta() == null) return false;

        for(ItemAbility i : getAppliedItemAbilities(item)){
            if(i.getId().equals(ability.getId())) return true;
        }
        return false;
    }

    public void removeAbility(ItemStack item, ItemAbility ability) {
        if(item == null || item.getItemMeta() == null) return;
        if(!hasAbility(item, ability)) return;

        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        NamespacedKey key = new NamespacedKey(AdvancedMonsters.getPlugin(AdvancedMonsters.class), "item_abilities");

        JsonArray abilitiesArray = new JsonArray();
        if(pdc.has(key, PersistentDataType.STRING)){
            String jsonString = pdc.get(key, PersistentDataType.STRING);
            abilitiesArray = GSON.fromJson(jsonString, JsonArray.class);
        }

        for(int i = 0; i < abilitiesArray.size(); i++){
            JsonElement element = abilitiesArray.get(i);
            if(!element.isJsonObject()) continue;

            JsonObject existingAbility = element.getAsJsonObject();
            if(existingAbility.has("id") && existingAbility.get("id").getAsString().equals(ability.getId().value())){
                abilitiesArray.remove(i);
                break;
            }
        }
        
        pdc.set(key, PersistentDataType.STRING, GSON.toJson(abilitiesArray));

        item.setItemMeta(meta);
    }

    public Set<ItemAbility> getAppliedItemAbilities(ItemStack item) {
        Set<ItemAbility> abilities = new HashSet<>();

        if(item == null || item.getItemMeta() == null) return abilities;

        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        NamespacedKey key = new NamespacedKey(AdvancedMonsters.getPlugin(AdvancedMonsters.class), "item_abilities");

        if(pdc.has(key, PersistentDataType.STRING)){
            String jsonString = pdc.get(key, PersistentDataType.STRING);
            JsonArray abilitiesArray = GSON.fromJson(jsonString, JsonArray.class);
            
            for(JsonElement element : abilitiesArray){
                if(element.isJsonObject()){
                    JsonObject abilityObject = element.getAsJsonObject();

                    String id = abilityObject.has("id") ? abilityObject.get("id").getAsString() : null;

                    if(id != null){
                        NamespacedKey abilityKey = NamespacedKey.fromString(id);

                        ItemAbility ability = getAbilityById(abilityKey);
                        if(ability != null){
                            abilities.add(ability);
                        }
                    }
                }
            }
        }

        return abilities;
    }

    public void removeAllAbility(ItemStack item){

    }

    public int getAppliedAbilityTier(ItemStack item, ItemAbility ability){
        return -1;
    }

    public static AbilityManager getManager() {
        return MANAGER;
    }

    public static Set<ItemAbility> getRegisteredItemAbilities(){
        Set<ItemAbility> abilitySet = new HashSet<>();
        for(Registrable r : Registry.getRegisteredObjects()){
            if(!(r instanceof ItemAbility a)) continue;
            abilitySet.add(a);
        }
        return abilitySet;
    }

    private ItemAbility getAbilityById(NamespacedKey key){
        for(ItemAbility i : getRegisteredItemAbilities()){
            if(i.getId().equals(key)) return i;
        }
        return null;
    }
}
