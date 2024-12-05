package pl.szelagi.betterSurvival.fishing;

import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.util.Vector;

public class FishingListener implements Listener {
    @EventHandler(ignoreCancelled = true)
    public void onPlayerFish(PlayerFishEvent event) {
        var state = event.getState();
        if (state != PlayerFishEvent.State.CAUGHT_FISH) return;
        var caught = event.getCaught();
        if (!(caught instanceof Item item)) return;
        var itemstack = item.getItemStack();

        Class<? extends Entity> entityClazz;
        if (Math.random() < 0.08d) {
            entityClazz = Drowned.class;
        } else {
            entityClazz = switch (itemstack.getType()) {
                case SALMON -> Salmon.class;
                case TROPICAL_FISH -> TropicalFish.class;
                case COD -> Cod.class;
                case PUFFERFISH -> PufferFish.class;
                default -> null;
            };
        }

        if (entityClazz == null) return;

        var location = event.getCaught().getLocation().clone();
        var velocity = event.getPlayer().getLocation().toVector().clone().subtract(caught.getLocation().toVector());
        velocity.multiply(1f / 10);
        velocity.add(new Vector(0.1, 0.4, 0.1));

        event.getCaught().remove();
        location.getWorld().spawn(location, entityClazz).setVelocity(velocity);
    }
}
