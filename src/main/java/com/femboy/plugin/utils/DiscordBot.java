package com.femboy.plugin.utils;

import com.femboy.plugin.FemboyPlugin;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.concurrent.CompletableFuture;

public class DiscordBot {
    
    private final FemboyPlugin plugin;
    private JDA jda;
    private boolean enabled = false;
    private boolean connected = false;
    
    public DiscordBot(FemboyPlugin plugin) {
        this.plugin = plugin;
    }
    
    /**
     * Initialize and connect the Discord bot
     */
    public CompletableFuture<Boolean> initialize() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                FileConfiguration config = plugin.getConfig();
                
                if (!config.getBoolean("discord_bot.enabled", false)) {
                    plugin.getLogger().info("Discord bot is disabled in config");
                    return false;
                }
                
                String token = config.getString("discord_bot.token");
                if (token == null || token.equals("CHANGE_ME") || token.trim().isEmpty()) {
                    plugin.getLogger().warning("Discord bot token not configured. Bot will not start.");
                    return false;
                }
                
                plugin.getLogger().info("Starting Discord bot...");
                
                jda = JDABuilder.createDefault(token)
                        .setActivity(Activity.playing("Minecraft Server"))
                        .setMemberCachePolicy(MemberCachePolicy.NONE)
                        .disableIntents(GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_PRESENCES)
                        .build();
                
                // Wait for bot to be ready
                jda.awaitReady();
                
                enabled = true;
                connected = true;
                
                plugin.getLogger().info("Discord bot connected successfully as: " + jda.getSelfUser().getAsTag());
                return true;
                
            } catch (InterruptedException e) {
                plugin.getLogger().severe("Discord bot initialization was interrupted");
                Thread.currentThread().interrupt();
                return false;
            } catch (Exception e) {
                plugin.getLogger().severe("Failed to initialize Discord bot: " + e.getMessage());
                return false;
            }
        });
    }
    
    /**
     * Shutdown the Discord bot
     */
    public void shutdown() {
        if (jda != null) {
            plugin.getLogger().info("Shutting down Discord bot...");
            jda.shutdown();
            connected = false;
        }
    }
    
    /**
     * Send a message to a specific channel
     */
    public CompletableFuture<Boolean> sendMessage(String channelType, String message) {
        return CompletableFuture.supplyAsync(() -> {
            if (!isConnected()) {
                return false;
            }
            
            try {
                FileConfiguration config = plugin.getConfig();
                String channelId = config.getString("discord_bot.channels." + channelType);
                
                if (channelId == null || channelId.equals("CHANGE_ME") || channelId.trim().isEmpty()) {
                    return false; // Channel not configured
                }
                
                TextChannel channel = jda.getTextChannelById(channelId);
                if (channel == null) {
                    plugin.getLogger().warning("Discord channel not found: " + channelId + " for type: " + channelType);
                    return false;
                }
                
                channel.sendMessage(message).queue(
                    success -> {}, // Success callback
                    error -> plugin.getLogger().warning("Failed to send Discord message: " + error.getMessage())
                );
                
                return true;
                
            } catch (Exception e) {
                plugin.getLogger().warning("Error sending Discord message: " + e.getMessage());
                return false;
            }
        });
    }
    
    /**
     * Send an embed-like formatted message
     */
    public CompletableFuture<Boolean> sendFormattedMessage(String channelType, String title, String content, String footer) {
        StringBuilder message = new StringBuilder();
        message.append("**").append(title).append("**\n");
        message.append(content);
        if (footer != null && !footer.isEmpty()) {
            message.append("\n*").append(footer).append("*");
        }
        
        return sendMessage(channelType, message.toString());
    }
    
    /**
     * Check if bot is enabled in config
     */
    public boolean isEnabled() {
        return enabled && plugin.getConfig().getBoolean("discord_bot.enabled", false);
    }
    
    /**
     * Check if bot is connected to Discord
     */
    public boolean isConnected() {
        return connected && jda != null && jda.getStatus() == JDA.Status.CONNECTED;
    }
    
    /**
     * Get the JDA instance
     */
    public JDA getJDA() {
        return jda;
    }
    
    /**
     * Reload bot configuration
     */
    public void reload() {
        if (isConnected()) {
            shutdown();
        }
        
        // Restart bot with new config
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            initialize().thenAccept(success -> {
                if (success) {
                    plugin.getLogger().info("Discord bot reloaded successfully");
                } else {
                    plugin.getLogger().warning("Failed to reload Discord bot");
                }
            });
        });
    }
}