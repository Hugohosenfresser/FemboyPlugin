package com.femboy.plugin.events;

import com.femboy.plugin.FemboyPlugin;
import com.femboy.plugin.utils.EventLogger;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.server.ServerCommandEvent;

public class DiscordEventListener implements Listener {
    
    private final FemboyPlugin plugin;
    private final EventLogger eventLogger;
    
    public DiscordEventListener(FemboyPlugin plugin) {
        this.plugin = plugin;
        this.eventLogger = plugin.getEventLogger();
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (eventLogger == null || !eventLogger.isEnabled()) {
            return;
        }
        
        FileConfiguration config = plugin.getConfig();
        if (config.getBoolean("discord_bot.events.log_all", false) || 
            config.getBoolean("discord_bot.events.log_player_events", true)) {
            
            eventLogger.logPlayerJoin(event.getPlayer());
        }
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerQuit(PlayerQuitEvent event) {
        if (eventLogger == null || !eventLogger.isEnabled()) {
            return;
        }
        
        FileConfiguration config = plugin.getConfig();
        if (config.getBoolean("discord_bot.events.log_all", false) || 
            config.getBoolean("discord_bot.events.log_player_events", true)) {
            
            String reason = event.getQuitMessage() != null ? event.getQuitMessage() : "Left the game";
            eventLogger.logPlayerLeave(event.getPlayer(), reason);
        }
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerDeath(PlayerDeathEvent event) {
        if (eventLogger == null || !eventLogger.isEnabled()) {
            return;
        }
        
        FileConfiguration config = plugin.getConfig();
        if (config.getBoolean("discord_bot.events.log_all", false) || 
            config.getBoolean("discord_bot.events.log_deaths", true)) {
            
            Player player = event.getEntity();
            String deathMessage = event.getDeathMessage() != null ? event.getDeathMessage() : player.getName() + " died";
            
            logPlayerDeath(player, deathMessage);
        }
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
        if (eventLogger == null || !eventLogger.isEnabled()) {
            return;
        }
        
        FileConfiguration config = plugin.getConfig();
        if (config.getBoolean("discord_bot.events.log_all", false) || 
            config.getBoolean("discord_bot.events.log_chat", false)) {
            
            Player player = event.getPlayer();
            String message = event.getMessage();
            
            logPlayerChat(player, message);
        }
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerChangedWorld(PlayerChangedWorldEvent event) {
        if (eventLogger == null || !eventLogger.isEnabled()) {
            return;
        }
        
        FileConfiguration config = plugin.getConfig();
        if (config.getBoolean("discord_bot.events.log_all", false) || 
            config.getBoolean("discord_bot.events.log_world_changes", true)) {
            
            Player player = event.getPlayer();
            String fromWorld = event.getFrom().getName();
            String toWorld = player.getWorld().getName();
            
            logWorldChange(player, fromWorld, toWorld);
        }
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerAdvancementDone(PlayerAdvancementDoneEvent event) {
        if (eventLogger == null || !eventLogger.isEnabled()) {
            return;
        }
        
        FileConfiguration config = plugin.getConfig();
        if (config.getBoolean("discord_bot.events.log_all", false) || 
            config.getBoolean("discord_bot.events.log_advancements", false)) {
            
            Player player = event.getPlayer();
            String advancement = event.getAdvancement().getKey().getKey();
            
            logPlayerAdvancement(player, advancement);
        }
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
        if (eventLogger == null || !eventLogger.isEnabled()) {
            return;
        }
        
        FileConfiguration config = plugin.getConfig();
        if (config.getBoolean("discord_bot.events.log_all", false) || 
            config.getBoolean("discord_bot.events.log_commands", true)) {
            
            Player player = event.getPlayer();
            String command = event.getMessage();
            
            // Remove the "/" from the command
            if (command.startsWith("/")) {
                command = command.substring(1);
            }
            
            String[] parts = command.split(" ");
            String commandName = parts.length > 0 ? parts[0] : command;
            String[] args = parts.length > 1 ? java.util.Arrays.copyOfRange(parts, 1, parts.length) : new String[0];
            
            // Only log if it's one of our plugin commands or if logging all commands
            if (isPluginCommand(commandName) || config.getBoolean("discord_bot.events.log_all_commands", false)) {
                eventLogger.logCommand(player, commandName, args);
            }
        }
    }
    
    private void logPlayerDeath(Player player, String deathMessage) {
        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, () -> {
            if (plugin.getDiscordBot().isConnected()) {
                String message = String.format("💀 **Player Death**\n" +
                        "**Player:** %s\n" +
                        "**Death Message:** %s\n" +
                        "**Location:** %s\n" +
                        "**World:** %s\n" +
                        "**Server:** %s",
                        player.getName(),
                        deathMessage,
                        String.format("X: %.0f, Y: %.0f, Z: %.0f", 
                            player.getLocation().getX(),
                            player.getLocation().getY(),
                            player.getLocation().getZ()),
                        player.getWorld().getName(),
                        plugin.getServer().getName()
                );
                
                plugin.getDiscordBot().sendFormattedMessage("deaths", "Player Death", message, getCurrentTimestamp());
            }
        });
    }
    
    private void logPlayerChat(Player player, String chatMessage) {
        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, () -> {
            if (plugin.getDiscordBot().isConnected()) {
                String message = String.format("💬 **Player Chat**\n" +
                        "**Player:** %s\n" +
                        "**Message:** %s\n" +
                        "**World:** %s\n" +
                        "**Server:** %s",
                        player.getName(),
                        chatMessage.length() > 100 ? chatMessage.substring(0, 100) + "..." : chatMessage,
                        player.getWorld().getName(),
                        plugin.getServer().getName()
                );
                
                plugin.getDiscordBot().sendFormattedMessage("chat", "Player Chat", message, getCurrentTimestamp());
            }
        });
    }
    
    private void logWorldChange(Player player, String fromWorld, String toWorld) {
        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, () -> {
            if (plugin.getDiscordBot().isConnected()) {
                String message = String.format("🌍 **World Change**\n" +
                        "**Player:** %s\n" +
                        "**From:** %s\n" +
                        "**To:** %s\n" +
                        "**Server:** %s",
                        player.getName(),
                        fromWorld,
                        toWorld,
                        plugin.getServer().getName()
                );
                
                plugin.getDiscordBot().sendFormattedMessage("general", "World Change", message, getCurrentTimestamp());
            }
        });
    }
    
    private void logPlayerAdvancement(Player player, String advancement) {
        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, () -> {
            if (plugin.getDiscordBot().isConnected()) {
                String message = String.format("🏆 **Achievement Unlocked**\n" +
                        "**Player:** %s\n" +
                        "**Achievement:** %s\n" +
                        "**World:** %s\n" +
                        "**Server:** %s",
                        player.getName(),
                        advancement.replace("_", " ").toLowerCase(),
                        player.getWorld().getName(),
                        plugin.getServer().getName()
                );
                
                plugin.getDiscordBot().sendFormattedMessage("general", "Achievement", message, getCurrentTimestamp());
            }
        });
    }
    
    private boolean isPluginCommand(String command) {
        return command.equals("givecustomitem") || 
               command.equals("givegui") || 
               command.equals("report") || 
               command.equals("reports") || 
               command.equals("ticket") || 
               command.equals("commands") || 
               command.equals("help") || 
               command.equals("pluginhelp") ||
               command.equals("discordbot") ||
               command.equals("discord");
    }
    
    private String getCurrentTimestamp() {
        return java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}