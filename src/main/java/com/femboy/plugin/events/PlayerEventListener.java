package com.femboy.plugin.events;

import com.femboy.plugin.FemboyPlugin;
import com.femboy.plugin.utils.EventLogger;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerEventListener implements Listener {
    
    private final FemboyPlugin plugin;
    
    public PlayerEventListener(FemboyPlugin plugin) {
        this.plugin = plugin;
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerJoin(PlayerJoinEvent event) {
        // Run async to avoid blocking the main thread
        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, () -> {
            EventLogger eventLogger = plugin.getEventLogger();
            if (eventLogger != null && eventLogger.isEnabled()) {
                eventLogger.logPlayerJoin(event.getPlayer());
            }
        });
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerQuit(PlayerQuitEvent event) {
        // Run async to avoid blocking the main thread
        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, () -> {
            EventLogger eventLogger = plugin.getEventLogger();
            if (eventLogger != null && eventLogger.isEnabled()) {
                String quitMessage = event.getQuitMessage();
                eventLogger.logPlayerLeave(event.getPlayer(), quitMessage != null ? quitMessage : "Left the server");
            }
        });
    }
}