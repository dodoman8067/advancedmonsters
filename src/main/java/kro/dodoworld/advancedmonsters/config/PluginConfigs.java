package kro.dodoworld.advancedmonsters.config;

import kro.dodoworld.advancedmonsters.config.modifier.HealthyModifierConfig;
import kro.dodoworld.advancedmonsters.config.modifier.StrongModifierConfig;

public class PluginConfigs {
    public static void init(){
        HealthyModifierConfig healthyConfig = new HealthyModifierConfig();
        StrongModifierConfig strongConfig = new StrongModifierConfig();
        healthyConfig.init();
        strongConfig.init();
    }
}
