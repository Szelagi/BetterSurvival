package pl.szelagi.betterSurvival.creeper;

import org.bukkit.Particle;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class BurningCreeperExplode implements Listener {
    @EventHandler(ignoreCancelled = true)
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getEntityType() != EntityType.CREEPER) return;
        var cause = event.getCause();
        if (cause != EntityDamageEvent.DamageCause.FIRE && cause != EntityDamageEvent.DamageCause.LAVA && cause != EntityDamageEvent.DamageCause.FIRE_TICK) return;
        var creeper = (Creeper) event.getEntity();
        if (cause == EntityDamageEvent.DamageCause.FIRE_TICK) {
            explode(creeper);
        } else if (creeper.getHealth() - event.getDamage() <= 0) {
            explode(creeper);
        }
    }

    private void explode(Creeper creeper) {
        var l = creeper.getLocation();
        l.getWorld().spawnParticle(Particle.LAVA, l.getX(), l.getY() + 1, l.getZ(), 14, 0.4d, 0.2d, 0.4d);
        creeper.explode();
    }
}
