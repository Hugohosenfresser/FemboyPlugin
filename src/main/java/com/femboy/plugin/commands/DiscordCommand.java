package com.femboy.plugin.commands;

import com.femboy.plugin.FemboyPlugin;
import com.femboy.plugin.utils.DiscordBot;
import com.femboy.plugin.utils.EventLogger;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.stream.Collectors;

public class DiscordCommand implements CommandExecutor, TabCompleter {
    
    private final FemboyPlugin plugin;
    
    public DiscordCommand(FemboyPlugin plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Check permission
        if (!plugin.getPermissionManager().checkPermissionWithMessage(sender, "admin")) {
            return true;
        }
        
        if (args.length == 0) {
            showHelp(sender);
            return true;
        }
        
        String subCommand = args[0].toLowerCase();
        
        switch (subCommand) {
            case "status" -> handleStatus(sender);
            case "reload" -> handleReload(sender);
            case "test" -> handleTest(sender, Arrays.copyOfRange(args, 1, args.length));
            case "toggle" -> handleToggle(sender, Arrays.copyOfRange(args, 1, args.length));
            case "channels" -> handleChannels(sender);
            case "events" -> handleEvents(sender);
            default -> showHelp(sender);
        }
        
        return true;
    }
    
    private void showHelp(CommandSender sender) {
        sender.sendMessage(ChatColor.GOLD + "=== Discord Bot Commands ===");
        sender.sendMessage(ChatColor.YELLOW + "/discord status" + ChatColor.WHITE + " - Check Discord bot status");
        sender.sendMessage(ChatColor.YELLOW + "/discord reload" + ChatColor.WHITE + " - Reload Discord bot configuration");
        sender.sendMessage(ChatColor.YELLOW + "/discord test <channel>" + ChatColor.WHITE + " - Test Discord bot connection");
        sender.sendMessage(ChatColor.YELLOW + "/discord toggle <event>" + ChatColor.WHITE + " - Toggle event logging");
        sender.sendMessage(ChatColor.YELLOW + "/discord channels" + ChatColor.WHITE + " - List configured channels");
        sender.sendMessage(ChatColor.YELLOW + "/discord events" + ChatColor.WHITE + " - List event logging status");
    }
    
    private void handleStatus(CommandSender sender) {
        DiscordBot discordBot = plugin.getDiscordBot();
        EventLogger eventLogger = plugin.getEventLogger();
        
        sender.sendMessage(ChatColor.GOLD + "=== Discord Bot Status ===");
        
        if (discordBot != null) {
            boolean enabled = discordBot.isEnabled();
            boolean connected = discordBot.isConnected();
            
            sender.sendMessage(ChatColor.YELLOW + "Bot Enabled: " + (enabled ? ChatColor.GREEN + "YES" : ChatColor.RED + "NO"));
            sender.sendMessage(ChatColor.YELLOW + "Bot Connected: " + (connected ? ChatColor.GREEN + "YES" : ChatColor.RED + "NO"));
            
            if (connected && discordBot.getJDA() != null) {
                sender.sendMessage(ChatColor.YELLOW + "Bot User: " + ChatColor.WHITE + discordBot.getJDA().getSelfUser().getAsTag());
                sender.sendMessage(ChatColor.YELLOW + "Guilds: " + ChatColor.WHITE + discordBot.getJDA().getGuilds().size());
            }
        } else {
            sender.sendMessage(ChatColor.RED + "Discord bot is not initialized!");
        }
        
        if (eventLogger != null) {
            sender.sendMessage(ChatColor.YELLOW + "Event Logger: " + (eventLogger.isEnabled() ? ChatColor.GREEN + "ACTIVE" : ChatColor.RED + "INACTIVE"));
        } else {
            sender.sendMessage(ChatColor.RED + "Event logger is not initialized!");
        }
    }
    
    private void handleReload(CommandSender sender) {
        sender.sendMessage(ChatColor.YELLOW + "Reloading Discord bot configuration...");
        
        // Reload plugin config first
        plugin.reloadConfig();
        
        // Reload Discord bot
        if (plugin.getDiscordBot() != null) {
            plugin.getDiscordBot().reload();
            sender.sendMessage(ChatColor.GREEN + "Discord bot configuration reloaded!");
        } else {
            sender.sendMessage(ChatColor.RED + "Discord bot is not initialized!");
        }
    }
    
    private void handleTest(CommandSender sender, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "Usage: /discord test <channel>");
            sender.sendMessage(ChatColor.YELLOW + "Available channels: commands, player_events, admin_actions, reports, general, chat, deaths, items, alerts");
            return;
        }
        
        String channelType = args[0].toLowerCase();
        DiscordBot discordBot = plugin.getDiscordBot();
        
        if (discordBot == null || !discordBot.isConnected()) {
            sender.sendMessage(ChatColor.RED + "Discord bot is not connected!");
            return;
        }
        
        String testMessage = String.format("**Test Message**\\n" +
                "**Sender:** %s\n" +
                "**Channel Type:** %s\n" +
                "**Server:** %s\n" +
                "**Time:** %s",
                sender.getName(),
                channelType,
                plugin.getServer().getName(),
                new java.util.Date().toString()
        );
        
        sender.sendMessage(ChatColor.YELLOW + "Sending test message to Discord channel: " + channelType);
        
        discordBot.sendFormattedMessage(channelType, "Test Message", testMessage, "Test completed").thenAccept(success -> {
            plugin.getServer().getScheduler().runTask(plugin, () -> {
                if (success) {
                    sender.sendMessage(ChatColor.GREEN + "Test message sent successfully!");
                } else {
                    sender.sendMessage(ChatColor.RED + "Failed to send test message. Check channel configuration.");
                }
            });
        });
    }
    
    private void handleToggle(CommandSender sender, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "Usage: /discord toggle <event>");
            sender.sendMessage(ChatColor.YELLOW + "Available events: commands, player_events, admin_actions, reports, plugin_events, chat, deaths, item_usage, world_changes, advancements, server_alerts, all");
            return;
        }
        
        String eventType = args[0].toLowerCase();
        FileConfiguration config = plugin.getConfig();
        String configPath = "discord_bot.events.log_" + eventType;
        
        if (!config.contains(configPath)) {
            sender.sendMessage(ChatColor.RED + "Unknown event type: " + eventType);
            return;
        }
        
        boolean currentValue = config.getBoolean(configPath);
        boolean newValue = !currentValue;
        
        config.set(configPath, newValue);
        plugin.saveConfig();
        
        String status = newValue ? ChatColor.GREEN + "ENABLED" : ChatColor.RED + "DISABLED";
        sender.sendMessage(ChatColor.YELLOW + "Event logging for '" + eventType + "' is now " + status);
    }
    
    private void handleChannels(CommandSender sender) {
        FileConfiguration config = plugin.getConfig();
        sender.sendMessage(ChatColor.GOLD + "=== Discord Channel Configuration ===");
        
        String[] channels = {"commands", "player_events", "admin_actions", "reports", "general", "chat", "deaths", "items", "alerts"};
        
        for (String channel : channels) {
            String channelId = config.getString("discord_bot.channels." + channel, "CHANGE_ME");
            String status = channelId.equals("CHANGE_ME") ? ChatColor.RED + "NOT CONFIGURED" : ChatColor.GREEN + channelId;
            sender.sendMessage(ChatColor.YELLOW + channel + ": " + status);
        }
    }
    
    private void handleEvents(CommandSender sender) {
        FileConfiguration config = plugin.getConfig();
        sender.sendMessage(ChatColor.GOLD + "=== Discord Event Logging Status ===");
        
        String[] events = {"commands", "player_events", "admin_actions", "reports", "plugin_events", 
                          "permission_denials", "chat", "deaths", "item_usage", "world_changes", 
                          "advancements", "server_alerts", "all"};
        
        for (String event : events) {
            boolean enabled = config.getBoolean("discord_bot.events.log_" + event, false);
            String status = enabled ? ChatColor.GREEN + "ENABLED" : ChatColor.RED + "DISABLED";
            sender.sendMessage(ChatColor.YELLOW + event + ": " + status);
        }
    }
    
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (!plugin.getPermissionManager().hasPermission(sender, "admin")) {
            return Collections.emptyList();
        }
        
        List<String> suggestions = new ArrayList<>();
        
        if (args.length == 1) {
            // Main subcommands
            String[] subCommands = {"status", "reload", "test", "toggle", "channels", "events"};
            String partial = args[0].toLowerCase();
            
            for (String subCmd : subCommands) {
                if (subCmd.startsWith(partial)) {
                    suggestions.add(subCmd);
                }
            }
        } else if (args.length == 2) {
            String subCommand = args[0].toLowerCase();
            String partial = args[1].toLowerCase();
            
            if ("test".equals(subCommand)) {
                // Channel types for test command
                String[] channels = {"commands", "player_events", "admin_actions", "reports", "general", "chat", "deaths", "items", "alerts"};
                for (String channel : channels) {
                    if (channel.startsWith(partial)) {
                        suggestions.add(channel);
                    }
                }
            } else if ("toggle".equals(subCommand)) {
                // Event types for toggle command
                String[] events = {"commands", "player_events", "admin_actions", "reports", "plugin_events", 
                                  "permission_denials", "chat", "deaths", "item_usage", "world_changes", 
                                  "advancements", "server_alerts", "all"};
                for (String event : events) {
                    if (event.startsWith(partial)) {
                        suggestions.add(event);
                    }
                }
            }
        }
        
        Collections.sort(suggestions);
        return suggestions;
    }
}