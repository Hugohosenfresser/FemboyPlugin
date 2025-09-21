package com.femboy.plugin.commands;

import com.femboy.plugin.FemboyPlugin;
import com.femboy.plugin.utils.DiscordWebhook;
import com.femboy.plugin.utils.ReportStorage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class ReportCommand implements CommandExecutor, TabCompleter {
    
    private final FemboyPlugin plugin;
    private final Map<UUID, Long> cooldowns = new ConcurrentHashMap<>();
    private DiscordWebhook discordWebhook;
    private ReportStorage reportStorage;
    
    public ReportCommand(FemboyPlugin plugin) {
        this.plugin = plugin;
        this.reportStorage = new ReportStorage(plugin);
        loadDiscordWebhook();
    }
    
    private void loadDiscordWebhook() {
        FileConfiguration config = plugin.getConfig();
        String webhookUrl = config.getString("discord.webhook_url");
        String botName = config.getString("discord.bot_name", "Server Reports");
        String botAvatar = config.getString("discord.bot_avatar", "");
        
        if (DiscordWebhook.isValidWebhookUrl(webhookUrl)) {
            this.discordWebhook = new DiscordWebhook(webhookUrl, botName, botAvatar);
            plugin.getLogger().info("Discord webhook configured successfully");
        } else {
            plugin.getLogger().warning("Discord webhook not configured properly. Reports will not be sent to Discord.");
        }
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(ChatColor.RED + "Only players can use this command.");
            return true;
        }
        
        if (args.length < 2) {
            player.sendMessage(getColoredMessage("messages.no_message"));
            return true;
        }
        
        // Check cooldown
        if (isOnCooldown(player)) {
            long remainingTime = getRemainingCooldown(player);
            String message = getColoredMessage("messages.cooldown").replace("%time%", String.valueOf(remainingTime));
            player.sendMessage(message);
            return true;
        }
        
        String reportType = args[0].toLowerCase();
        String message = String.join(" ", Arrays.copyOfRange(args, 1, args.length));
        
        // Check message length
        int maxLength = plugin.getConfig().getInt("reports.max_message_length", 500);
        if (message.length() > maxLength) {
            String errorMessage = getColoredMessage("messages.message_too_long").replace("%max%", String.valueOf(maxLength));
            player.sendMessage(errorMessage);
            return true;
        }
        
        // Check if Discord webhook is configured
        if (discordWebhook == null) {
            player.sendMessage(getColoredMessage("messages.webhook_not_configured"));
            return true;
        }
        
        // Set cooldown
        setCooldown(player);
        
        switch (reportType) {
            case "player" -> handlePlayerReport(player, message);
            case "issue" -> handleIssueReport(player, message);
            default -> {
                player.sendMessage(ChatColor.RED + "Invalid report type. Use 'player' or 'issue'.");
                player.sendMessage(ChatColor.YELLOW + "Usage: /report player <playername> <reason>");
                player.sendMessage(ChatColor.YELLOW + "Usage: /report issue <description>");
                return true;
            }
        }
        
        return true;
    }
    
    private void handlePlayerReport(Player reporter, String message) {
        // Parse player report: /report player <playername> <reason>
        String[] parts = message.split(" ", 2);
        if (parts.length < 2) {
            reporter.sendMessage(ChatColor.RED + "Usage: /report player <playername> <reason>");
            return;
        }
        
        String reportedPlayer = parts[0];
        String reason = parts[1];
        
        // Send to Discord
        discordWebhook.sendPlayerReport(reporter, reportedPlayer, reason).thenAccept(success -> {
            Bukkit.getScheduler().runTask(plugin, () -> {
                if (success) {
                    reporter.sendMessage(getColoredMessage("messages.report_sent"));
                    
                    // Log to console if enabled
                    if (plugin.getConfig().getBoolean("reports.log_to_console", true)) {
                        plugin.getLogger().info(String.format("Player Report - Reporter: %s, Reported: %s, Reason: %s", 
                                reporter.getName(), reportedPlayer, reason));
                    }
                    
                    // Store locally if enabled
                    if (plugin.getConfig().getBoolean("reports.store_locally", true)) {
                        reportStorage.storePlayerReport(reporter.getName(), reportedPlayer, reason);
                    }
                } else {
                    reporter.sendMessage(getColoredMessage("messages.webhook_error"));
                }
            });
        });
    }
    
    private void handleIssueReport(Player reporter, String issue) {
        // Send to Discord
        discordWebhook.sendIssueReport(reporter, issue).thenAccept(success -> {
            Bukkit.getScheduler().runTask(plugin, () -> {
                if (success) {
                    reporter.sendMessage(getColoredMessage("messages.report_sent"));
                    
                    // Log to console if enabled
                    if (plugin.getConfig().getBoolean("reports.log_to_console", true)) {
                        plugin.getLogger().info(String.format("Issue Report - Reporter: %s, Issue: %s", 
                                reporter.getName(), issue));
                    }
                    
                    // Store locally if enabled
                    if (plugin.getConfig().getBoolean("reports.store_locally", true)) {
                        reportStorage.storeIssueReport(reporter.getName(), issue);
                    }
                } else {
                    reporter.sendMessage(getColoredMessage("messages.webhook_error"));
                }
            });
        });
    }
    
    private boolean isOnCooldown(Player player) {
        long cooldownTime = plugin.getConfig().getLong("reports.cooldown_seconds", 300) * 1000; // Convert to milliseconds
        return cooldowns.containsKey(player.getUniqueId()) && 
               (System.currentTimeMillis() - cooldowns.get(player.getUniqueId())) < cooldownTime;
    }
    
    private long getRemainingCooldown(Player player) {
        long cooldownTime = plugin.getConfig().getLong("reports.cooldown_seconds", 300) * 1000;
        long elapsed = System.currentTimeMillis() - cooldowns.get(player.getUniqueId());
        return (cooldownTime - elapsed) / 1000; // Return seconds
    }
    
    private void setCooldown(Player player) {
        cooldowns.put(player.getUniqueId(), System.currentTimeMillis());
    }
    
    private String getColoredMessage(String path) {
        String message = plugin.getConfig().getString(path, "Message not found: " + path);
        return ChatColor.translateAlternateColorCodes('&', message);
    }
    
    
    public void reloadDiscordWebhook() {
        loadDiscordWebhook();
    }
    
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (!(sender instanceof Player)) {
            return Collections.emptyList();
        }
        
        List<String> suggestions = new ArrayList<>();
        
        if (args.length == 1) {
            // First argument: report type
            String partial = args[0].toLowerCase();
            if ("player".startsWith(partial)) {
                suggestions.add("player");
            }
            if ("issue".startsWith(partial)) {
                suggestions.add("issue");
            }
        } else if (args.length == 2 && args[0].equalsIgnoreCase("player")) {
            // Second argument for player reports: player name
            String partial = args[1].toLowerCase();
            suggestions.addAll(Bukkit.getOnlinePlayers().stream()
                    .map(Player::getName)
                    .filter(name -> name.toLowerCase().startsWith(partial))
                    .collect(Collectors.toList()));
        }
        
        Collections.sort(suggestions);
        return suggestions;
    }
}