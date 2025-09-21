package com.femboy.plugin.utils;

import com.femboy.plugin.FemboyPlugin;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class PermissionManager {
    
    private final FemboyPlugin plugin;
    
    public PermissionManager(FemboyPlugin plugin) {
        this.plugin = plugin;
    }
    
    /**
     * Checks if a command sender has permission for a specific command
     * @param sender The command sender
     * @param command The command name (without slash)
     * @return true if sender has permission or permission is disabled
     */
    public boolean hasPermission(CommandSender sender, String command) {
        FileConfiguration config = plugin.getConfig();
        String permission = config.getString("permissions." + command, "");
        
        // If permission is empty or null, allow everyone
        if (permission == null || permission.trim().isEmpty()) {
            return true;
        }
        
        // Console always has permission
        if (!(sender instanceof Player)) {
            return true;
        }
        
        // Check if player has the permission
        return sender.hasPermission(permission);
    }
    
    /**
     * Checks if a command sender has admin permission
     * @param sender The command sender
     * @return true if sender has admin permission
     */
    public boolean hasAdminPermission(CommandSender sender) {
        FileConfiguration config = plugin.getConfig();
        String permission = config.getString("permissions.admin", "femboyplugin.admin");
        
        // Console always has admin permission
        if (!(sender instanceof Player)) {
            return true;
        }
        
        return sender.hasPermission(permission);
    }
    
    /**
     * Sends no permission message to the sender
     * @param sender The command sender
     */
    public void sendNoPermissionMessage(CommandSender sender) {
        FileConfiguration config = plugin.getConfig();
        String message = config.getString("permissions.no_permission", "&cYou don't have permission to use this command!");
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }
    
    /**
     * Checks permission and sends message if denied
     * @param sender The command sender
     * @param command The command name
     * @return true if has permission, false if denied (and message sent)
     */
    public boolean checkPermissionWithMessage(CommandSender sender, String command) {
        if (hasPermission(sender, command)) {
            return true;
        }
        
        sendNoPermissionMessage(sender);
        return false;
    }
    
    /**
     * Gets the permission string for a command
     * @param command The command name
     * @return The permission string or empty if disabled
     */
    public String getPermission(String command) {
        FileConfiguration config = plugin.getConfig();
        return config.getString("permissions." + command, "");
    }
}