package com.femboy.plugin.utils;

import com.femboy.plugin.FemboyPlugin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ReportStorage {
    private final FemboyPlugin plugin;
    private final File reportsFile;
    private FileConfiguration reportsConfig;
    
    public ReportStorage(FemboyPlugin plugin) {
        this.plugin = plugin;
        this.reportsFile = new File(plugin.getDataFolder(), "reports.yml");
        loadReportsFile();
    }
    
    private void loadReportsFile() {
        if (!reportsFile.exists()) {
            try {
                // Create parent directories if they don't exist
                if (!plugin.getDataFolder().exists()) {
                    plugin.getDataFolder().mkdirs();
                }
                reportsFile.createNewFile();
                plugin.getLogger().info("Created new reports.yml file");
            } catch (IOException e) {
                plugin.getLogger().severe("Could not create reports.yml: " + e.getMessage());
            }
        }
        
        reportsConfig = YamlConfiguration.loadConfiguration(reportsFile);
        
        // Initialize structure if file is empty
        if (!reportsConfig.contains("reports")) {
            reportsConfig.createSection("reports");
            saveReportsFile();
        }
    }
    
    public void storeReport(String reportType, String reporter, String content, String reportId) {
        if (!plugin.getConfig().getBoolean("reports.store_locally", true)) {
            return; // Storage disabled
        }
        
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        String reportPath = "reports." + reportId;
        
        reportsConfig.set(reportPath + ".type", reportType);
        reportsConfig.set(reportPath + ".reporter", reporter);
        reportsConfig.set(reportPath + ".content", content);
        reportsConfig.set(reportPath + ".timestamp", timestamp);
        reportsConfig.set(reportPath + ".status", "OPEN");
        
        saveReportsFile();
        
        plugin.getLogger().info(String.format("Stored report %s locally: %s by %s", 
                reportId, reportType, reporter));
    }
    
    public void storePlayerReport(String reporter, String reportedPlayer, String reason) {
        String reportId = "RPT-" + System.currentTimeMillis();
        String content = String.format("Reported Player: %s | Reason: %s", reportedPlayer, reason);
        storeReport("PLAYER", reporter, content, reportId);
    }
    
    public void storeIssueReport(String reporter, String issue) {
        String reportId = "RPT-" + System.currentTimeMillis();
        storeReport("ISSUE", reporter, issue, reportId);
    }
    
    public List<String> getRecentReports(int limit) {
        List<String> reports = new ArrayList<>();
        
        if (!reportsConfig.contains("reports")) {
            return reports;
        }
        
        var reportSection = reportsConfig.getConfigurationSection("reports");
        if (reportSection == null) {
            return reports;
        }
        
        var reportKeys = new ArrayList<>(reportSection.getKeys(false));
        
        // Sort by timestamp (newest first)
        reportKeys.sort((a, b) -> {
            String timestampA = reportsConfig.getString("reports." + a + ".timestamp", "");
            String timestampB = reportsConfig.getString("reports." + b + ".timestamp", "");
            return timestampB.compareTo(timestampA);
        });
        
        // Limit results
        if (limit > 0 && reportKeys.size() > limit) {
            reportKeys = reportKeys.subList(0, limit);
        }
        
        for (String reportId : reportKeys) {
            String type = reportsConfig.getString("reports." + reportId + ".type", "UNKNOWN");
            String reporter = reportsConfig.getString("reports." + reportId + ".reporter", "Unknown");
            String content = reportsConfig.getString("reports." + reportId + ".content", "No content");
            String timestamp = reportsConfig.getString("reports." + reportId + ".timestamp", "Unknown time");
            String status = reportsConfig.getString("reports." + reportId + ".status", "OPEN");
            
            reports.add(String.format("[%s] %s - %s by %s (%s) - Status: %s", 
                    timestamp, reportId, type, reporter, content, status));
        }
        
        return reports;
    }
    
    public void markReportClosed(String reportId) {
        if (reportsConfig.contains("reports." + reportId)) {
            reportsConfig.set("reports." + reportId + ".status", "CLOSED");
            reportsConfig.set("reports." + reportId + ".closed_timestamp", 
                    LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            saveReportsFile();
            plugin.getLogger().info("Marked report " + reportId + " as closed");
        }
    }
    
    private void saveReportsFile() {
        try {
            reportsConfig.save(reportsFile);
        } catch (IOException e) {
            plugin.getLogger().severe("Could not save reports.yml: " + e.getMessage());
        }
    }
    
    public void reloadReports() {
        loadReportsFile();
    }
}