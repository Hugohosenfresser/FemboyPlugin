package com.femboy.plugin.events;

import com.femboy.plugin.FemboyPlugin;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatBypassListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChat(AsyncPlayerChatEvent event) {
        // Respect other features that may already consume chat (e.g., GUI amount input)
        if (event.isCancelled()) return;

        FemboyPlugin plugin = FemboyPlugin.getInstance();
        boolean disable = plugin.getConfig().getBoolean("chat.disable_reporting", true);
        if (!disable) return;

        Player player = event.getPlayer();
        String raw = event.getMessage();

        // Cancel vanilla/signed chat and broadcast as system messages (not reportable)
        event.setCancelled(true);

        String format = plugin.getConfig().getString("chat.format", "&7<%player%> %message%");

        // Insert a hard reset after the player's display name to prevent color bleed
        String safePlayer = player.getDisplayName() + ChatColor.RESET + ChatColor.GRAY;

        String rendered = format
                .replace("%player%", safePlayer)
                .replace("%message%", raw);
        String colored = ChatColor.translateAlternateColorCodes('&', rendered);

        for (Player recipient : event.getRecipients()) {
            recipient.sendMessage(colored);
        }
    }
}