package kro.dodoworld.advancedmonsters.modifier;

import com.destroystokyo.paper.entity.ai.GoalKey;
import kro.dodoworld.advancedmonsters.AdvancedMonsters;
import kro.dodoworld.advancedmonsters.config.data.UnlockedEntityAbilities;
import kro.dodoworld.advancedmonsters.config.modifier.HealthyModifierConfig;
import kro.dodoworld.advancedmonsters.config.modifier.SpeedyModifierConfig;
import kro.dodoworld.advancedmonsters.config.modifier.StormyModifierConfig;
import kro.dodoworld.advancedmonsters.config.modifier.TankModifierConfig;
import kro.dodoworld.advancedmonsters.modifier.ability.type.RevitalizeModifier;
import kro.dodoworld.advancedmonsters.modifier.ability.type.TeleporterModifier;
import kro.dodoworld.advancedmonsters.modifier.entity.ai.AnimalAttackTargetGoal;
import kro.dodoworld.advancedmonsters.util.AdvancedUtils;
import kro.dodoworld.advancedmonsters.modifier.ability.MonsterAbility;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Random;

public class EntityModifier implements Listener {

    private final EnumSet<MonsterAbility> possibleAbilities = EnumSet.of(
            MonsterAbility.HEALTHY, MonsterAbility.INVISIBLE, MonsterAbility.BOOMER, MonsterAbility.FLAMING, MonsterAbility.LASER,
            MonsterAbility.PUNCHY, MonsterAbility.SPEEDY, MonsterAbility.STORMY, MonsterAbility.STRONG, MonsterAbility.TELEPORTER, MonsterAbility.TANK, MonsterAbility.VENOMOUS,
            MonsterAbility.FROZEN, MonsterAbility.LIGHTING, MonsterAbility.REVITALIZE
    );
    private final Random random = new Random();
    private final Plugin plugin = AdvancedMonsters.getPlugin(AdvancedMonsters.class);

    @EventHandler
    public void onSpawn(CreatureSpawnEvent event) {
        if (event.getEntity().getWorld().getDifficulty().equals(Difficulty.PEACEFUL)) return;
        if (event.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.CUSTOM)) return;
        if (!(event.getEntity() instanceof Monster monster)) return;
        if (monster.getScoreboardTags().contains("adm_miniboss")) return;
        if (random.nextDouble(0, 101) <= 20) return;
        FileConfiguration config = UnlockedEntityAbilities.getUnlockedEntityAbilityConfig();
        MonsterAbility ability = getRandomAbility(config);
        if(ability == null) return;
        applyAbility(ability, monster);
    }

    @EventHandler
    public void onAnimalSpawn(ChunkLoadEvent event){
        if(!event.isNewChunk()) return;
        if(!plugin.isEnabled()) return;
        if (event.getWorld().getDifficulty().equals(Difficulty.PEACEFUL)) return;
        for(Entity e : event.getChunk().getEntities()){
            if (!(e instanceof Animals animal)) continue;
            if(Bukkit.getMobGoals().hasGoal(animal, GoalKey.of(Animals.class, new NamespacedKey(plugin, "animal_attack")))) continue;
            if(random.nextDouble(0, 101) <= 2){
                animal.addScoreboardTag("adm_animal_deadly");
                animal.setCustomNameVisible(true);
                animal.customName(Component.text("☠Deadly", NamedTextColor.DARK_RED, TextDecoration.BOLD).append(Component.text(" " + toMobName(animal.getType().name()), NamedTextColor.WHITE)));
                animal.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(animal.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getBaseValue() * 3);
                animal.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(200);
                animal.setHealth(200);
                if(animal.getAttribute(Attribute.GENERIC_FLYING_SPEED) != null){
                    animal.getAttribute(Attribute.GENERIC_FLYING_SPEED).setBaseValue(animal.getAttribute(Attribute.GENERIC_FLYING_SPEED).getBaseValue() * 2);
                }
                Bukkit.getMobGoals().addGoal(animal, 2, new AnimalAttackTargetGoal(animal, random.nextDouble(10, 30), true));
            }else{
                Bukkit.getMobGoals().addGoal(animal, 2, new AnimalAttackTargetGoal(animal, 2.0, false));
            }
        }
    }

    private MonsterAbility getRandomAbility(FileConfiguration config) {
        List<MonsterAbility> abilities = new ArrayList<>(possibleAbilities);
        Collections.shuffle(abilities);
        for (MonsterAbility ability : abilities) {
            if (config.getBoolean(ability.toString().toLowerCase())) {
                return ability;
            }
        }
        return null;
    }

    private void applyAbility(MonsterAbility ability, Monster monster) {
        switch (ability) {
            case HEALTHY -> {
                FileConfiguration healthyConfig = HealthyModifierConfig.getHealthyModifierConfig();
                addIconAndName(monster, ability);
                AttributeInstance maxHealthAttribute = monster.getAttribute(Attribute.GENERIC_MAX_HEALTH);
                double healthMultiplier = healthyConfig.getDouble("healthy_health_multiply_amount");
                maxHealthAttribute.setBaseValue(maxHealthAttribute.getBaseValue() * healthMultiplier);
                monster.setHealth(maxHealthAttribute.getBaseValue());
            }
            case STRONG, FLAMING, BOOMER, PUNCHY, LASER, VENOMOUS, FROZEN, LIGHTING -> {
                addIconAndName(monster, ability);
            }
            case TANK -> {
                FileConfiguration tankConfig = TankModifierConfig.getTankModifierConfig();
                addIconAndName(monster, ability);
                monster.getAttribute(Attribute.GENERIC_ARMOR).addModifier(new AttributeModifier("generic.Armor", tankConfig.getDouble("tank_bonus_defence_amount"), AttributeModifier.Operation.ADD_NUMBER));
                monster.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(monster.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getBaseValue() * tankConfig.getDouble("tank_speed_multiply_amount"));
            }
            case SPEEDY -> {
                FileConfiguration speedyConfig = SpeedyModifierConfig.getSpeedyModifierConfig();
                addIconAndName(monster, ability);
                monster.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(monster.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() * speedyConfig.getDouble("speedy_health_multiply_amount"));
                monster.setHealth(monster.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());
                monster.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(monster.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getBaseValue() * speedyConfig.getDouble("speedy_speed_multiply_amount"));
            }
            case TELEPORTER -> {
                addIconAndName(monster, ability);
                TeleporterModifier.getTeleportingMonsters().add(monster.getUniqueId());
            }
            case INVISIBLE -> {
                monster.setInvisible(true);
                addIconAndName(monster, MonsterAbility.INVISIBLE);
            }
            case STORMY -> {
                FileConfiguration stormyConfig = StormyModifierConfig.getStormyModifierConfig();
                if(stormyConfig.getBoolean("stormy_only_spawn_when_storming")){
                    if(!monster.getWorld().hasStorm()) return;
                }
                addIconAndName(monster, ability);
            }
            case REVITALIZE -> {
                addIconAndName(monster, ability);
                RevitalizeModifier.getRevitalizeMonsters().add(monster.getUniqueId());
            }
        }
    }

    private void addIconAndName(Monster monster, MonsterAbility ability){
        monster.addScoreboardTag("adm_modifier_" + ability.toString().toLowerCase());
        monster.setCustomNameVisible(true);
        monster.customName(AdvancedUtils.getAbilitySymbolWithColor(ability).append(Component.text(ability + " ").append(Component.text(toMobName(monster.getType().name()), NamedTextColor.GRAY))));
    }

    private String toMobName(String typeName){
        return WordUtils.capitalize(typeName.toLowerCase().replace("_", " "));
    }
}