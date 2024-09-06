package kro.dodoworld.advancedmonsters;

import com.destroystokyo.paper.entity.ai.Goal;
import com.destroystokyo.paper.entity.ai.GoalKey;
import io.papermc.paper.event.player.AsyncChatEvent;
import kro.dodoworld.advancedmonsters.core.registry.Registry;
import kro.dodoworld.advancedmonsters.modifier.ability.Abilities;
import kro.dodoworld.advancedmonsters.system.entity.ModifierApplier;
import kro.dodoworld.advancedmonsters.system.entity.ability.GravityOrb;
import kro.dodoworld.advancedmonsters.system.entity.ability.HealingCircle;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Random;
import java.util.logging.Logger;

public final class AdvancedMonsters extends JavaPlugin implements Listener {

    /**
     * TODO: Add Necromancer boss
     * TODO: Add Crazy Snowman miniboss
     */

    private final Logger logger = getLogger();
    private final Random random = new Random();
    @Override
    public void onEnable() {
        if(!checkServerEnvironment()) return; //checks server environment. ends code when false returned.
        initFiles();
        Registry.init(this);
        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(new Abilities(), this);
        getServer().getPluginManager().registerEvents(new ModifierApplier(), this);
        logger.info("Plugin successfully started.");
    }

    @EventHandler
    public void onChat(PlayerInteractAtEntityEvent event){
        GravityOrb orb = new GravityOrb(event.getPlayer(), 2);
        orb.spawn(10);
    }

    /**
     * Checks this server is running Paper or fork of Paper.
     * @return true if Paper, otherwise false
     */
    private boolean isPaperServer(){
        try{
            Class<?> a = Class.forName("io.papermc.paper.entity.PaperBucketable");
        }catch (ClassNotFoundException e){
            return false;
        }
        return true;
    }

    /**
     * Checks server environment.
     * @return true, if server environment is good enough to enable this plugin, otherwise false
     */
    private boolean checkServerEnvironment(){
        if(!isPaperServer()){
            logger.severe("Plugin requires Paper or fork of Paper server.");
            logger.severe("Disabling plugin...");
            //Disables plugin
            getServer().getPluginManager().disablePlugin(this);
            return false;
        }
        return true;
    }

    /**
     * Removes entities with adm_remove_when_reload tag
     */
    private void removeEntities(){
        for(World world : getServer().getWorlds()){
            for(LivingEntity entity : world.getLivingEntities()){
                if(entity.getScoreboardTags().contains("adm_remove_when_reload")){
                    entity.remove();
                }
            }
        }
    }

    @Override
    public void onDisable() {
        logger.info("Plugin successfully disabled.");
        GravityOrb.removeAll();
    }

    private void initFiles(){
        File abilityConfigFolder = new File(getDataFolder() + "/ability_configs/");
        abilityConfigFolder.mkdir();
    }
}
