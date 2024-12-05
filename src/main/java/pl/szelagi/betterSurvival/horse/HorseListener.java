package pl.szelagi.betterSurvival.horse;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDismountEvent;
import org.bukkit.event.entity.EntityMountEvent;

import java.util.Arrays;
import java.util.List;

public class HorseListener implements Listener {
	private final List<EntityType> allowTypes = Arrays.asList(EntityType.HORSE, EntityType.MULE, EntityType.PIG, EntityType.DONKEY, EntityType.CAMEL);
	@EventHandler(ignoreCancelled = true)
	public void onEntityMount(EntityMountEvent event) {
		if (!(event.getEntity() instanceof Player player)) return;
		var mount = event.getMount();
		if (!allowTypes.contains(mount.getType())) return;
		BetterHorses.register(new MountPlayer(mount, player));
		Bukkit.getServer().broadcast(Component.text("m"));
	}

	@EventHandler(ignoreCancelled = true)
	public void onEntityDismount(EntityDismountEvent event) {
		if (!(event.getEntity() instanceof Player player)) return;
		var mount = event.getDismounted();
		if (!allowTypes.contains(mount.getType())) return;
		BetterHorses.unregister(new MountPlayer(mount, player));
	}

}
