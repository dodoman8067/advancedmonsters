package kro.dodoworld.advancedmonsters.modifier.ability;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.apache.commons.lang3.text.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Monster;

import java.util.List;

public class HybridAbility {
    private final Monster entity;
    private final List<Ability> appliedAbilities;

    public HybridAbility(Monster entity, List<Ability> appliedAbilities) {
        this.entity = entity;
        this.appliedAbilities = appliedAbilities;
    }

    public void apply(){
        if(appliedAbilities.isEmpty()) return;
        String gradient = "";
        for(Ability a : appliedAbilities){
            a.onSpawn(entity);
            if(a.getSymbol() == null) {
                gradient = gradient + ":#AAAAAA";
            }else{
                gradient = gradient + ":" + a.getSymbol().color().asHexString();
            }
        }
        MiniMessage mm = MiniMessage.miniMessage();
        Component c = mm.deserialize("<gradient" + gradient + ">⚛Hybrid " + WordUtils.capitalize(this.entity.getType().name().toLowerCase().replace('_', ' ')) + "</gradient>");
        this.entity.customName(c);
    }
}
