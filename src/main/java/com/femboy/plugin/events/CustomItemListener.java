package com.femboy.plugin.events;

import com.femboy.plugin.FemboyPlugin;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import org.bukkit.BanList;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Date;
import java.time.Instant;

public class CustomItemListener implements Listener {

    private final Map<UUID, Long> grappleCooldown = new ConcurrentHashMap<>();

    @EventHandler
    public void onUse(PlayerInteractEvent event) {
        ItemStack item = event.getItem();
        if (item == null || !item.hasItemMeta()) return;

        String name = ChatColor.stripColor(item.getItemMeta().getDisplayName());

        switch (name.toLowerCase()) {
            case "Cock Slicer" -> event.getPlayer().sendMessage(ChatColor.GREEN + "Cuck Slicer has been used!");
            case "bandage" -> {
                event.getPlayer().sendMessage(ChatColor.AQUA + "You healed yourself.");

                // Heal 2 hearts
                double newHealth = Math.min(event.getPlayer().getHealth() + 4.0, event.getPlayer().getMaxHealth());
                event.getPlayer().setHealth(newHealth);

                // Consume 1 bandage
                ItemStack itemStack = event.getItem();
                if (itemStack.getAmount() > 1) itemStack.setAmount(itemStack.getAmount() - 1);
                else event.getPlayer().getInventory().remove(itemStack);
            }
            case "medkit" -> {
                event.getPlayer().sendMessage(ChatColor.AQUA + "You healed yourself.");

                // Heal 4 hearts
                double newHealth = Math.min(event.getPlayer().getHealth() + 8.0, event.getPlayer().getMaxHealth());
                event.getPlayer().setHealth(newHealth);

                // Consume 1 medkit
                ItemStack itemStack = event.getItem();
                if (itemStack.getAmount() > 1) itemStack.setAmount(itemStack.getAmount() - 1);
                else event.getPlayer().getInventory().remove(itemStack);
            }
        }
    }

    @EventHandler
    public void onEntityHit(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player damager)) return;
        ItemStack held = damager.getInventory().getItemInMainHand();
        if (held == null || !held.hasItemMeta() || !held.getItemMeta().hasDisplayName()) return;

        String name = ChatColor.stripColor(held.getItemMeta().getDisplayName());
        FemboyPlugin plugin = FemboyPlugin.getInstance();

        if (name.equalsIgnoreCase("Rickroll Stick")) {
            // Admin-only usage
            if (!plugin.getPermissionManager().hasAdminPermission(damager)) {
                plugin.getPermissionManager().sendNoPermissionMessage(damager);
                return;
            }
            if (!(event.getEntity() instanceof Player target)) return;

            // Configurable custom sound key + fallback
            String soundKey = plugin.getConfig().getString("rickroll.sound_key", "custom.rickroll");
            float volume = (float) plugin.getConfig().getDouble("rickroll.volume", 1.0);
            float pitch = (float) plugin.getConfig().getDouble("rickroll.pitch", 1.0);
            String fallbackEnum = plugin.getConfig().getString("rickroll.fallback_sound", "MUSIC_DISC_11");

            // Try to play custom sound by key first (requires resource pack). If it fails, play fallback enum.
            try {
                target.playSound(target.getLocation(), soundKey, volume, pitch);
            } catch (Throwable ignored) {
                try {
                    Sound fallback = Sound.valueOf(fallbackEnum.toUpperCase());
                    target.playSound(target.getLocation(), fallback, volume, pitch);
                } catch (IllegalArgumentException ex) {
                    target.playSound(target.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, volume, pitch);
                }
            }

            target.sendMessage(ChatColor.LIGHT_PURPLE + "You've been rickrolled by " + damager.getName() + "!");
            damager.sendMessage(ChatColor.LIGHT_PURPLE + "You rickrolled " + target.getName() + "!");
            return;
        }

        if (name.equalsIgnoreCase("Ban Hammer")) {
            // Admin-only usage
            if (!plugin.getPermissionManager().hasAdminPermission(damager)) {
                plugin.getPermissionManager().sendNoPermissionMessage(damager);
                return;
            }
            if (!(event.getEntity() instanceof Player target)) return;

            String reason = plugin.getConfig().getString("banhammer.reason", "Banned by an administrator");
            long minutes = plugin.getConfig().getLong("banhammer.duration_minutes", -1);
            Date expires = minutes > 0 ? Date.from(Instant.now().plusSeconds(minutes * 60)) : null;

            try {
                plugin.getServer().getBanList(BanList.Type.NAME).addBan(target.getName(), reason, expires, damager.getName());
                target.kickPlayer(ChatColor.RED + reason);
                damager.sendMessage(ChatColor.RED + "Banned " + target.getName() + (expires == null ? " permanently." : (" for " + minutes + " minutes.")));
                event.setCancelled(true);
            } catch (Exception e) {
                damager.sendMessage(ChatColor.RED + "Failed to ban player: " + e.getMessage());
            }
        }
    }

    @EventHandler
    public void onGrapple(PlayerFishEvent event) {
        Player player = event.getPlayer();
        ItemStack held = player.getInventory().getItemInMainHand();
        if (held == null || !held.hasItemMeta() || !held.getItemMeta().hasDisplayName()) return;
        String name = ChatColor.stripColor(held.getItemMeta().getDisplayName());
        if (!name.equalsIgnoreCase("Grappling Hook")) return;

        FemboyPlugin plugin = FemboyPlugin.getInstance();
        if (!plugin.getPermissionManager().hasAdminPermission(player)) {
            plugin.getPermissionManager().sendNoPermissionMessage(player);
            return;
        }

        PlayerFishEvent.State state = event.getState();
        switch (state) {
            case IN_GROUND, CAUGHT_ENTITY, REEL_IN -> {
                long now = System.currentTimeMillis();
                long cooldownMs = plugin.getConfig().getLong("grappling.cooldown_ms", 1500);
                Long last = grappleCooldown.get(player.getUniqueId());
                if (last != null && (now - last) < cooldownMs) {
                    return; // still on cooldown
                }
                grappleCooldown.put(player.getUniqueId(), now);

                double strength = plugin.getConfig().getDouble("grappling.strength", plugin.getConfig().getDouble("grappling.speed", 1.5));
                double verticalBoost = plugin.getConfig().getDouble("grappling.vertical_boost", 0.4);
                org.bukkit.entity.FishHook hook = event.getHook();
                if (hook == null) return;
                Vector from = player.getLocation().toVector();
                Vector to = hook.getLocation().toVector();
                Vector dir = to.subtract(from);
                if (dir.length() < 0.1) return;
                dir.normalize().multiply(strength);
                // configurable upward boost
                dir.setY(Math.max(verticalBoost, dir.getY()));
                player.setVelocity(dir);
            }
            default -> {
            }
        }
    }
}
