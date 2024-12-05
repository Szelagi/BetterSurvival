package pl.szelagi.betterSurvival;

import org.bukkit.plugin.java.JavaPlugin;
import pl.szelagi.betterSurvival.fishing.FishingListener;

public final class BetterSurvival extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        String pluginVersion = getPluginMeta().getVersion();
        getLogger().info("Plugin version " + pluginVersion + " has started successfully!");

        // BetterFishing
        getServer().getPluginManager().registerEvents(new FishingListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        String pluginVersion = getPluginMeta().getVersion();
        getLogger().info("Plugin version " + pluginVersion + " is shutting down!");
    }
}
