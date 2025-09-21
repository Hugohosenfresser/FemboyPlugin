package com.femboy.plugin.utils;

import com.femboy.plugin.FemboyPlugin;
import okhttp3.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.CompletableFuture;

public class DiscordWebhook {
    private static final OkHttpClient client = new OkHttpClient();
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    
    private final String webhookUrl;
    private final String botName;
    private final String botAvatar;
    
    public DiscordWebhook(String webhookUrl, String botName, String botAvatar) {
        this.webhookUrl = webhookUrl;
        this.botName = botName;
        this.botAvatar = botAvatar;
    }
    
    /**
     * Sends a player report to Discord
     */
    public CompletableFuture<Boolean> sendPlayerReport(Player reporter, String reportedPlayer, String reason) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                String json = createPlayerReportEmbed(reporter, reportedPlayer, reason);
                return sendWebhook(json);
            } catch (Exception e) {
                FemboyPlugin.getInstance().getLogger().severe("Failed to send player report: " + e.getMessage());
                return false;
            }
        });
    }
    
    /**
     * Sends an issue report to Discord
     */
    public CompletableFuture<Boolean> sendIssueReport(Player reporter, String issue) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                String json = createIssueReportEmbed(reporter, issue);
                return sendWebhook(json);
            } catch (Exception e) {
                FemboyPlugin.getInstance().getLogger().severe("Failed to send issue report: " + e.getMessage());
                return false;
            }
        });
    }
    
    private String createPlayerReportEmbed(Player reporter, String reportedPlayer, String reason) {
        String timestamp = OffsetDateTime.now().format(DateTimeFormatter.ISO_INSTANT);
        String serverName = Bukkit.getServerName();
        
        return String.format("""
            {
                "username": "%s",
                "avatar_url": "%s",
                "embeds": [{
                    "title": "🚨 Player Report",
                    "color": 16711680,
                    "fields": [
                        {
                            "name": "Reporter",
                            "value": "%s",
                            "inline": true
                        },
                        {
                            "name": "Reported Player",
                            "value": "%s",
                            "inline": true
                        },
                        {
                            "name": "Server",
                            "value": "%s",
                            "inline": true
                        },
                        {
                            "name": "Reason",
                            "value": "%s",
                            "inline": false
                        }
                    ],
                    "footer": {
                        "text": "Report ID: %s"
                    },
                    "timestamp": "%s"
                }]
            }
            """, 
            escapeJson(botName),
            escapeJson(botAvatar),
            escapeJson(reporter.getName()),
            escapeJson(reportedPlayer),
            escapeJson(serverName),
            escapeJson(reason),
            generateReportId(),
            timestamp
        );
    }
    
    private String createIssueReportEmbed(Player reporter, String issue) {
        String timestamp = OffsetDateTime.now().format(DateTimeFormatter.ISO_INSTANT);
        String serverName = Bukkit.getServerName();
        
        return String.format("""
            {
                "username": "%s",
                "avatar_url": "%s",
                "embeds": [{
                    "title": "🐛 Issue Report",
                    "color": 16776960,
                    "fields": [
                        {
                            "name": "Reporter",
                            "value": "%s",
                            "inline": true
                        },
                        {
                            "name": "Server",
                            "value": "%s",
                            "inline": true
                        },
                        {
                            "name": "Issue Description",
                            "value": "%s",
                            "inline": false
                        }
                    ],
                    "footer": {
                        "text": "Report ID: %s"
                    },
                    "timestamp": "%s"
                }]
            }
            """, 
            escapeJson(botName),
            escapeJson(botAvatar),
            escapeJson(reporter.getName()),
            escapeJson(serverName),
            escapeJson(issue),
            generateReportId(),
            timestamp
        );
    }
    
    private boolean sendWebhook(String json) {
        RequestBody body = RequestBody.create(json, JSON);
        Request request = new Request.Builder()
                .url(webhookUrl)
                .post(body)
                .build();
        
        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                FemboyPlugin.getInstance().getLogger().info("Report sent successfully to Discord");
                return true;
            } else {
                FemboyPlugin.getInstance().getLogger().warning("Discord webhook returned error: " + response.code() + " " + response.message());
                return false;
            }
        } catch (IOException e) {
            FemboyPlugin.getInstance().getLogger().severe("Failed to send Discord webhook: " + e.getMessage());
            return false;
        }
    }
    
    private String escapeJson(String text) {
        if (text == null) return "";
        return text.replace("\\", "\\\\")
                  .replace("\"", "\\\"")
                  .replace("\n", "\\n")
                  .replace("\r", "\\r")
                  .replace("\t", "\\t");
    }
    
    private String generateReportId() {
        return "RPT-" + System.currentTimeMillis();
    }
    
    public static boolean isValidWebhookUrl(String url) {
        return url != null && 
               !url.equals("CHANGE_ME") && 
               url.startsWith("https://discord.com/api/webhooks/");
    }
}