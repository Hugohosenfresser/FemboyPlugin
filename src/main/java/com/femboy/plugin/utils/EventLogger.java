package com.femboy.plugin.utils;

import com.femboy.plugin.FemboyPlugin;
import org.bukkit.entity.Player;
import org.bukkit.configuration.file.FileConfiguration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EventLogger {
    
    private final FemboyPlugin plugin;
    private final DiscordBot discordBot;
    
    public EventLogger(FemboyPlugin plugin, DiscordBot discordBot) {
        this.plugin = plugin;
        this.discordBot = discordBot;
    }
    
    /**
     * Log command usage to Discord
     */
    public void logCommand(Player player, String command, String[] args) {
        FileConfiguration config = plugin.getConfig();
        if (!config.getBoolean("discord_bot.events.log_commands", true)) {
            return;
        }
        
        String timestamp = getCurrentTimestamp();
        String argsString = args.length > 0 ? String.join(" ", args) : "no arguments";
        
        String message = String.format("**Command Used**\\n" +
                "**Player:** %s\n" +
                "**Command:** /%s\n" +
                "**Arguments:** %s\n" +
                "**Server:** %s",
                player.getName(),
                command,
                argsString,
                plugin.getServer().getName()
        );
        
        discordBot.sendFormattedMessage("commands", "Command Usage", message, timestamp);
    }
    
    /**
     * Log admin actions to Discord
     */
    public void logAdminAction(Player player, String action, String details) {
        FileConfiguration config = plugin.getConfig();
        if (!config.getBoolean("discord_bot.events.log_admin_actions", true)) {
            return;
        }
        
        String timestamp = getCurrentTimestamp();
        
        String message = String.format("**Admin Action**\\n" +
                "**Admin:** %s\n" +
                "**Action:** %s\n" +
                "**Details:** %s\n" +
                "**Server:** %s",
                player.getName(),
                action,
                details,
                plugin.getServer().getName()
        );
        
        discordBot.sendFormattedMessage("admin_actions", "Admin Action", message, timestamp);
    }
    
    /**
     * Log player join events to Discord
     */
    public void logPlayerJoin(Player player) {
        FileConfiguration config = plugin.getConfig();
        if (!config.getBoolean("discord_bot.events.log_player_events", true)) {
            return;
        }
        
        String timestamp = getCurrentTimestamp();
        
        String message = String.format("**Player Joined**\\n" +
                "**Player:** %s\n" +
                "**IP:** %s\n" +
                "**Online Players:** %d/%d\n" +
                "**Server:** %s",
                player.getName(),
                player.getAddress() != null ? player.getAddress().getAddress().getHostAddress() : "Unknown",
                plugin.getServer().getOnlinePlayers().size(),
                plugin.getServer().getMaxPlayers(),
                plugin.getServer().getName()
        );
        
        discordBot.sendFormattedMessage("player_events", "Player Join", message, timestamp);
    }
    
    /**
     * Log player leave events to Discord
     */
    public void logPlayerLeave(Player player, String reason) {
        FileConfiguration config = plugin.getConfig();
        if (!config.getBoolean("discord_bot.events.log_player_events", true)) {
            return;
        }
        
        String timestamp = getCurrentTimestamp();
        
        String message = String.format("**Player Left**\\n" +
                "**Player:** %s\n" +
                "**Reason:** %s\n" +
                "**Online Players:** %d/%d\n" +
                "**Server:** %s",
                player.getName(),
                reason != null ? reason : "Unknown",
                plugin.getServer().getOnlinePlayers().size() - 1, // -1 because player hasn't left yet
                plugin.getServer().getMaxPlayers(),
                plugin.getServer().getName()
        );
        
        discordBot.sendFormattedMessage("player_events", "Player Leave", message, timestamp);
    }
    
    /**
     * Log report events to Discord
     */
    public void logReport(Player reporter, String reportType, String details) {
        FileConfiguration config = plugin.getConfig();
        if (!config.getBoolean("discord_bot.events.log_reports", true)) {
            return;
        }
        
        String timestamp = getCurrentTimestamp();
        
        String message = String.format("**Report Submitted**\\n" +
                "**Reporter:** %s\n" +
                "**Type:** %s\n" +
                "**Details:** %s\n" +
                "**Server:** %s",
                reporter.getName(),
                reportType.toUpperCase(),
                details,
                plugin.getServer().getName()
        );
        
        discordBot.sendFormattedMessage("reports", "New Report", message, timestamp);
    }
    
    /**
     * Log permission denials to Discord
     */
    public void logPermissionDenial(Player player, String command, String permission) {
        FileConfiguration config = plugin.getConfig();
        if (!config.getBoolean("discord_bot.events.log_permission_denials", false)) {
            return;
        }
        
        String timestamp = getCurrentTimestamp();
        
        String message = String.format("**Permission Denied**\\n" +
                "**Player:** %s\n" +
                "**Command:** /%s\n" +
                "**Required Permission:** %s\n" +
                "**Server:** %s",
                player.getName(),
                command,
                permission,
                plugin.getServer().getName()
        );
        
        discordBot.sendFormattedMessage("general", "Permission Denied", message, timestamp);
    }
    
    /**
     * Log plugin events to Discord
     */
    public void logPluginEvent(String eventType, String details) {
        FileConfiguration config = plugin.getConfig();
        if (!config.getBoolean("discord_bot.events.log_plugin_events", true)) {
            return;
        }
        
        String timestamp = getCurrentTimestamp();
        
        String message = String.format("**Plugin Event**\\n" +
                "**Event:** %s\n" +
                "**Details:** %s\n" +
                "**Server:** %s\n" +
                "**Plugin Version:** %s",
                eventType,
                details,
                plugin.getServer().getName(),
                plugin.getDescription().getVersion()
        );
        
        discordBot.sendFormattedMessage("general", "Plugin Event", message, timestamp);
    }
    
    /**
     * Log custom item usage to Discord
     */
    public void logItemUsage(Player player, String itemName, String action) {
        FileConfiguration config = plugin.getConfig();
        if (!config.getBoolean("discord_bot.events.log_admin_actions", true)) {
            return;
        }
        
        String timestamp = getCurrentTimestamp();
        
        String message = String.format("**Item Usage**\\n" +
                "**Player:** %s\n" +
                "**Item:** %s\n" +
                "**Action:** %s\n" +
                "**Server:** %s",
                player.getName(),
                itemName,
                action,
                plugin.getServer().getName()
        );
        
        discordBot.sendFormattedMessage("general", "Item Usage", message, timestamp);
    }
    
    /**
     * Get current timestamp formatted for Discord
     */
    private String getCurrentTimestamp() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
    
    /**
     * Check if Discord bot logging is enabled and connected
     */
    public boolean isEnabled() {
        return discordBot.isEnabled() && discordBot.isConnected();
    }
}