package com.femboy.plugin.items;

import com.femboy.plugin.FemboyPlugin;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;

public class RainbowHelmetManager implements Runnable {

    private final FemboyPlugin plugin;
    private BukkitTask task;
    private int tick = 0;

    private final Map<UUID, Map<Location, BlockData>> originals = new HashMap<>();

    private final Material[] glassPalette = new Material[]{
            Material.WHITE_STAINED_GLASS,
            Material.ORANGE_STAINED_GLASS,
            Material.MAGENTA_STAINED_GLASS,
            Material.LIGHT_BLUE_STAINED_GLASS,
            Material.YELLOW_STAINED_GLASS,
            Material.LIME_STAINED_GLASS,
            Material.PINK_STAINED_GLASS,
            Material.GRAY_STAINED_GLASS,
            Material.LIGHT_GRAY_STAINED_GLASS,
            Material.CYAN_STAINED_GLASS,
            Material.PURPLE_STAINED_GLASS,
            Material.BLUE_STAINED_GLASS,
            Material.BROWN_STAINED_GLASS,
            Material.GREEN_STAINED_GLASS,
            Material.RED_STAINED_GLASS,
            Material.BLACK_STAINED_GLASS
    };

    public RainbowHelmetManager(FemboyPlugin plugin) {
        this.plugin = plugin;
    }

    public void start() {
        if (!plugin.getConfig().getBoolean("rainbow_helmet.enabled", true)) return;
        int interval = plugin.getConfig().getInt("rainbow_helmet.interval_ticks", 5);
        this.task = plugin.getServer().getScheduler().runTaskTimer(plugin, this, interval, interval);
    }

    public void stop() {
        // Revert client-side changes
        for (UUID uuid : new ArrayList<>(originals.keySet())) {
            Player p = Bukkit.getPlayer(uuid);
            Map<Location, BlockData> map = originals.get(uuid);
            if (p != null && map != null) {
                for (Map.Entry<Location, BlockData> e : map.entrySet()) {
                    p.sendBlockChange(e.getKey(), e.getValue());
                }
            }
        }
        originals.clear();
        if (task != null) {
            task.cancel();
            task = null;
        }
    }

    @Override
    public void run() {
        int hueStep = Math.max(1, plugin.getConfig().getInt("rainbow_helmet.hue_step", 5));
        tick = (tick + hueStep) % 360;
        int radius = plugin.getConfig().getInt("rainbow_helmet.radius", 4);
        int maxBlocks = Math.max(16, plugin.getConfig().getInt("rainbow_helmet.max_blocks", 200));

        float hue = (tick % 360) / 360.0f;
        int rgb = java.awt.Color.HSBtoRGB(hue, 1.0f, 1.0f) & 0xFFFFFF;
        org.bukkit.Color bukkitColor = org.bukkit.Color.fromRGB(rgb);
        Material displayGlass = glassPalette[tick % glassPalette.length];

        for (Player p : Bukkit.getOnlinePlayers()) {
            // Only apply if wearing the Rainbow Helmet and has admin perms
            ItemStack helm = p.getInventory().getHelmet();
            boolean wearing = false;
            if (helm != null && helm.hasItemMeta() && helm.getItemMeta().hasDisplayName()) {
                String name = ChatColor.stripColor(helm.getItemMeta().getDisplayName());
                wearing = name.equalsIgnoreCase("Rainbow Helmet");
            }
            if (wearing && !plugin.getPermissionManager().hasAdminPermission(p)) {
                wearing = false;
            }

            Map<Location, BlockData> prev = originals.getOrDefault(p.getUniqueId(), new HashMap<>());

            if (!wearing) {
                // Revert any changes and continue
                if (!prev.isEmpty()) {
                    for (Map.Entry<Location, BlockData> e : prev.entrySet()) {
                        p.sendBlockChange(e.getKey(), e.getValue());
                    }
                    prev.clear();
                }
                originals.remove(p.getUniqueId());
                continue;
            }

            // Update helmet color smoothly
            if (helm.getItemMeta() instanceof LeatherArmorMeta lam) {
                lam.setColor(bukkitColor);
                helm.setItemMeta(lam);
            }

            // Revert previous changes before applying new ones
            if (!prev.isEmpty()) {
                for (Map.Entry<Location, BlockData> e : prev.entrySet()) {
                    p.sendBlockChange(e.getKey(), e.getValue());
                }
                prev.clear();
            }

            // Collect nearby glass blocks and recolor client-side
            Location center = p.getLocation();
            World world = center.getWorld();
            int changed = 0;
            int r = Math.max(1, radius);
            for (int dx = -r; dx <= r; dx++) {
                for (int dy = -r; dy <= r; dy++) {
                    for (int dz = -r; dz <= r; dz++) {
                        if (changed >= maxBlocks) break;
                        Location loc = center.clone().add(dx, dy, dz);
                        if (!world.isChunkLoaded(loc.getBlockX() >> 4, loc.getBlockZ() >> 4)) continue;
                        Block b = world.getBlockAt(loc);
                        Material m = b.getType();
                        if (m == Material.GLASS || m.name().endsWith("_STAINED_GLASS")) {
                            // Save original
                            BlockData original = b.getBlockData();
                            prev.put(loc, original);
                            // Send new color
                            BlockData displayData = Bukkit.createBlockData(displayGlass);
                            p.sendBlockChange(loc, displayData);
                            changed++;
                        }
                    }
                }
            }

            originals.put(p.getUniqueId(), prev);
        }
    }
}
