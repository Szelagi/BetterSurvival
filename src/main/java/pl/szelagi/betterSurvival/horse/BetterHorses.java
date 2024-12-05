package pl.szelagi.betterSurvival.horse;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitTask;
import pl.szelagi.betterSurvival.BetterSurvival;

import java.util.ArrayList;

public class BetterHorses {
	private static BukkitTask observer = null;
	private static ArrayList<MountPlayer> observed = new ArrayList<>();

	public static void register(MountPlayer mount) {
		if (observed.isEmpty()) startTimer();

		if (!observed.contains(mount)) observed.add(mount);
	}

	public static void unregister(MountPlayer horse) {
		observed.remove(horse);
		if (observed.isEmpty()) stopTimer();
	}
	public static void initialize() {
		BetterSurvival.runTimer(() -> {

			if (observed.isEmpty()) return;

		}, 0, 1);
	}

	public static void stopTimer() {
		if (observed == null) return;
		observer.cancel();
		observer = null;
	}

	public static void startTimer() {
		observer = BetterSurvival.runTimer(() -> {
			if (observed.isEmpty()) return;

			var invalid = new ArrayList<>();

			for (var mp : observed) {
				if (!validate(mp)) {
					invalid.add(mp);
					continue;
				}

				var entity = (LivingEntity) mp.entity();
				if (isInLiquid(entity)) {
					entity.setGravity(false);
					if (hasLand(entity)) {
						jump(entity);
					} else {
						swim(entity);
					}
				} else {
					entity.setGravity(true);
				}
			}

		}, 0, 1);
	}

	public static boolean validate(MountPlayer mountPlayer) {
		var mount = mountPlayer.entity();
		var player = mountPlayer.player();
		if (player.getVehicle() == null || !player.getVehicle().equals(mount)) return false;
		if (!(mount instanceof LivingEntity)) return false;
		if (mount.isDead()) return false;
		return !player.isDead();
		// offline player
		// isValid
	}

	public static boolean isInLiquid(LivingEntity livingEntity) {
		Block block = livingEntity.getLocation().clone().add(0, 1, 0).getBlock();
		return block.getType() == Material.WATER || block.getType() == Material.LAVA;
	}

	public static boolean hasLand(LivingEntity livingEntity) {
		return livingEntity.getEyeLocation().add(livingEntity.getLocation().getDirection())
				.getBlock().getType() != Material.WATER;
	}

	public static void jump(LivingEntity livingEntity) {
		livingEntity.setVelocity(livingEntity.getVelocity().setY(0.20));
	}

	public static void swim(LivingEntity livingEntity) {
		livingEntity.setVelocity(livingEntity.getVelocity().setY(0.20));
	}

}
