package pl.szelagi.betterSurvival.particle;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class DamageParticle implements Listener {
    public final static double DESTINATION = 4;
    public final static int UPPER_LIMIT = 250;
    public final static int UNDER_LIMIT = 3;

    public static double visualDamage(double damage) {
        return damage * 1.3 + (damage * damage) / 2;
    }

    public @Nullable BlockData particle(Entity entity) {
        return particleMap.get(entity.getType());
    }

    private final Map<EntityType, BlockData> particleMap;
    private final BlockData blood = Material.RED_CONCRETE.createBlockData();
    private final BlockData liteBlood = Material.RED_STAINED_GLASS.createBlockData();


    public DamageParticle() {
        particleMap = new HashMap<>();
        particleMap.put(EntityType.SKELETON, Material.BONE_BLOCK.createBlockData());
        particleMap.put(EntityType.WITHER_SKELETON, Material.COAL_BLOCK.createBlockData());
        particleMap.put(EntityType.WITHER, Material.SOUL_SAND.createBlockData());
        particleMap.put(EntityType.WARDEN, Material.WARPED_WART_BLOCK.createBlockData());
        particleMap.put(EntityType.BLAZE, Material.LAVA.createBlockData());
        particleMap.put(EntityType.GHAST, Material.NETHER_WART_BLOCK.createBlockData());
        particleMap.put(EntityType.CREEPER, Material.TNT.createBlockData());
        particleMap.put(EntityType.SLIME, Material.SLIME_BLOCK.createBlockData());
        particleMap.put(EntityType.MAGMA_CUBE, Material.LAVA.createBlockData());
        particleMap.put(EntityType.IRON_GOLEM, Material.IRON_BLOCK.createBlockData());
        particleMap.put(EntityType.SNOW_GOLEM, Material.SNOW_BLOCK.createBlockData());
    }

    private void displayBlood(Entity entity, double damage) {
        var l = entity.getLocation();
        var b = entity.getBoundingBox();
        var count = (int) (DESTINATION * b.getVolume() * visualDamage(damage));
        count = Math.min(count, UPPER_LIMIT);
        count = Math.max(count, UNDER_LIMIT);
        var blockData = particle(entity);
        if (blockData != null) {
            l.getWorld().spawnParticle(Particle.BLOCK_CRUMBLE, l, count, b.getWidthX() / 4, b.getHeight()/ 2, b.getWidthZ() / 4, blockData);
        } else {
            l.getWorld().spawnParticle(Particle.BLOCK_CRUMBLE, l, count / 3 * 2, b.getWidthX() / 4, b.getHeight()/ 2, b.getWidthZ() / 4, liteBlood);
            l.getWorld().spawnParticle(Particle.BLOCK_CRUMBLE, l, count / 3, b.getWidthX() / 4, b.getHeight()/ 2, b.getWidthZ() / 4, blood);
        }
    }
    @EventHandler(ignoreCancelled = true)
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        displayBlood(event.getEntity(), event.getDamage());
    }
}
