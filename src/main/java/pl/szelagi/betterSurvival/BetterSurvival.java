package pl.szelagi.betterSurvival;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import pl.szelagi.betterSurvival.creeper.BurningCreeperExplode;
import pl.szelagi.betterSurvival.enderpearl.MarkedEnderPearl;
import pl.szelagi.betterSurvival.fishing.FishingListener;
import pl.szelagi.betterSurvival.horse.BetterHorses;
import pl.szelagi.betterSurvival.horse.HorseListener;
import pl.szelagi.betterSurvival.particle.BurningParticle;
import pl.szelagi.betterSurvival.particle.DamageParticle;

public final class BetterSurvival extends JavaPlugin {
    private static BetterSurvival instance;

    @Override
    public void onEnable() {
        instance = this;
        // Plugin startup logic
        String pluginVersion = getPluginMeta().getVersion();
        getLogger().info("Plugin version " + pluginVersion + " has started successfully!");

        // Better Fishing
        getServer().getPluginManager().registerEvents(new FishingListener(), this);

        // Burning Particle
        getServer().getPluginManager().registerEvents(new BurningParticle(), this);

        // Burning Creeper Explode
        getServer().getPluginManager().registerEvents(new BurningCreeperExplode(), this);

        // Damage Particle
        getServer().getPluginManager().registerEvents(new DamageParticle(), this);

        // Marked Ender Pearl
        getServer().getPluginManager().registerEvents(new MarkedEnderPearl(), this);

        // Swimming Horse
        BetterHorses.initialize();
        getServer().getPluginManager().registerEvents(new HorseListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        String pluginVersion = getPluginMeta().getVersion();
        getLogger().info("Plugin version " + pluginVersion + " is shutting down!");
    }

    public static BetterSurvival getInstance() {
        return instance;
    }

    public static BukkitTask runTimer(Runnable runnable, int l0, int l1) {
        return getInstance().getServer().getScheduler().runTaskTimer(getInstance(), runnable, l0, l1);
    }
}
