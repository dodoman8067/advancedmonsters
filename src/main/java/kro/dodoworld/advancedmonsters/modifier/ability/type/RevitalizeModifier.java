package kro.dodoworld.advancedmonsters.modifier.ability.type;

import kro.dodoworld.advancedmonsters.AdvancedMonsters;
import kro.dodoworld.advancedmonsters.config.modifier.RevitalizeModifierConfig;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

public class RevitalizeModifier {
    private static final Set<UUID> REVITALIZE_MONSTERS = new HashSet<>();
    private static final List<PotionEffectType> potionEffectTypeList = new ArrayList<>();
    private static final Random rnd = new Random();

    public static Set<UUID> getRevitalizeMonsters() {
        return REVITALIZE_MONSTERS;
    }

    public static void run(AdvancedMonsters plugin){
        FileConfiguration config = RevitalizeModifierConfig.getRevitalizeModifierConfig();
        double range = config.getDouble("revitalize_effect_apply_range");
        double chance = config.getDouble("revitalize_effect_apply_chance");
        int ticksPer = config.getInt("revitalize_apply_effects_per_tick_amount");
        int maxEffects = config.getInt("revitalize_max_effect_amount_per_entity");
        int maxTicks = config.getInt("revitalize_effect_max_ticks");
        for(String s : config.getStringList("revitalize_effects")){
            if(PotionEffectType.getByKey(NamespacedKey.minecraft(s)) == null) continue;
            potionEffectTypeList.add(PotionEffectType.getByKey(NamespacedKey.minecraft(s)));
        }
        new BukkitRunnable() {
            int i = 0;
            @Override
            public void run() {
                for(World world : Bukkit.getWorlds()){
                    for(LivingEntity entity : world.getLivingEntities()){
                        if(!(entity instanceof Monster monster)) continue;
                        if(!REVITALIZE_MONSTERS.contains(monster.getUniqueId())) continue;
                        if(monster.isDead()) {
                            REVITALIZE_MONSTERS.remove(monster.getUniqueId());
                            continue;
                        }

                        if(monster.getTarget() != null){
                            if(i % ticksPer == 0){
                                for(Entity e : monster.getNearbyEntities(range, range, range)){
                                    if(!(e instanceof Monster c)) continue;
                                    if((Math.random() * 100) <= chance){
                                        for(int j = 0; j<maxEffects; j++){
                                            Collections.shuffle(potionEffectTypeList, rnd);
                                            PotionEffectType effectType = potionEffectTypeList.get(0);
                                            applyEffects(c, effectType, rnd.nextInt(0, config.getInt("revitalize_max_effect_amplifier_" + effectType.getKey().value())+1), rnd.nextInt(20, maxTicks + 1));
                                        }
                                    }
                                }
                            }
                        }
                        i++;
                    }
                }
            }
        }.runTaskTimer(plugin, 0L, 2L);

        for(World world : Bukkit.getWorlds()){
            for(LivingEntity entity : world.getLivingEntities()){
                if(!(entity instanceof Monster monster)){
                    continue;
                }
                if(monster.getScoreboardTags().contains("adm_modifier_revitalize")){
                    REVITALIZE_MONSTERS.add(monster.getUniqueId());
                }
            }
        }
    }


    private static void applyEffects(Monster creature, PotionEffectType effect, int maxAmplifier, int maxTicks){
        if (maxAmplifier > 0) {
            creature.addPotionEffect(new PotionEffect(effect, rnd.nextInt(maxTicks - 20) + 20, rnd.nextInt(maxAmplifier) + 1, false, true, true));
        } else {
            creature.addPotionEffect(new PotionEffect(effect, rnd.nextInt(maxTicks - 20) + 20, 0, false, true, true));
        }
    }
}
