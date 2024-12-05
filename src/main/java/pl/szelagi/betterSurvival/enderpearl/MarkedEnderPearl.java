package pl.szelagi.betterSurvival.enderpearl;

import com.destroystokyo.paper.event.player.PlayerLaunchProjectileEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import pl.szelagi.betterSurvival.BetterSurvival;

public class MarkedEnderPearl implements Listener {
    @EventHandler(ignoreCancelled = true)
    public void onPlayerLaunchProjectile(PlayerLaunchProjectileEvent event) {
        var pearl = event.getProjectile();
        if (pearl.getType() != EntityType.ENDER_PEARL) return;

        var player = event.getPlayer();
        pearl.setCustomNameVisible(true);
        pearl.setCustomName("§2" + player.getName());
        pearl.setGlowing(true);
        Bukkit.getScheduler().runTaskLater(BetterSurvival.getInstance(), () -> {
            player.setCooldown(Material.ENDER_PEARL, 20*3);
        },1);
    }
}
