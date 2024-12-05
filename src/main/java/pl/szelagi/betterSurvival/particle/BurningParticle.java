package pl.szelagi.betterSurvival.particle;

import org.bukkit.Particle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class BurningParticle implements Listener {
    @EventHandler(ignoreCancelled = true)
    public void onEntityDamage(EntityDamageEvent event) {
        var cause = event.getCause();
        if (cause != EntityDamageEvent.DamageCause.FIRE && cause != EntityDamageEvent.DamageCause.LAVA && cause != EntityDamageEvent.DamageCause.FIRE_TICK) return;
        var l = event.getEntity().getLocation();
        l.getWorld().spawnParticle(Particle.LAVA, l.getX(), l.getY() + 1, l.getZ(), 4, 0.1d, 0.2d, 0.1d);
    }
}
