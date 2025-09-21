package com.femboy.plugin.commands;

import com.femboy.plugin.FemboyPlugin;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandsCommand implements CommandExecutor {
    
    private final FemboyPlugin plugin;
    
    public CommandsCommand(FemboyPlugin plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Check permission
        if (!plugin.getPermissionManager().checkPermissionWithMessage(sender, "commands")) {
            return true;
        }
        
        sendCommandsList(sender);
        return true;
    }
    
    private void sendCommandsList(CommandSender sender) {
        // Header
        sender.sendMessage(ChatColor.GOLD + "==========================================");
        sender.sendMessage(ChatColor.YELLOW + "         " + ChatColor.BOLD + "FemboyPlugin Commands");
        sender.sendMessage(ChatColor.GOLD + "==========================================");
        sender.sendMessage("");
        
        // Custom Items Section
        sender.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "Custom Items:");
        sender.sendMessage(ChatColor.WHITE + "/givecustomitem <item> [amount]" + ChatColor.GRAY + " - Give custom items");
        sender.sendMessage(ChatColor.DARK_GRAY + "   Available items: cockslicer, bandage, medkit");
        sender.sendMessage(ChatColor.DARK_GRAY + "   Permission: " + plugin.getPermissionManager().getPermission("givecustomitem"));
        sender.sendMessage(ChatColor.WHITE + "/givegui" + ChatColor.GRAY + " - Open the custom items GUI");
        sender.sendMessage(ChatColor.DARK_GRAY + "   Permission: " + plugin.getPermissionManager().getPermission("givegui"));
        sender.sendMessage("");
        
        // Report System Section
        sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Report System:");
        sender.sendMessage(ChatColor.WHITE + "/report player <name> <reason>" + ChatColor.GRAY + " - Report a player");
        sender.sendMessage(ChatColor.DARK_GRAY + "   Example: /report player BadGuy griefing my house");
        sender.sendMessage(ChatColor.WHITE + "/report issue <description>" + ChatColor.GRAY + " - Report a server issue");
        sender.sendMessage(ChatColor.DARK_GRAY + "   Example: /report issue Shop plugin is broken");
        sender.sendMessage(ChatColor.WHITE + "/reports" + ChatColor.GRAY + " - Alias for /report");
        sender.sendMessage(ChatColor.WHITE + "/ticket" + ChatColor.GRAY + " - Alias for /report");
        sender.sendMessage(ChatColor.DARK_GRAY + "   Permission: " + plugin.getPermissionManager().getPermission("report"));
        sender.sendMessage("");
        
        // Help Section
        sender.sendMessage(ChatColor.BLUE + "" + ChatColor.BOLD + "Information:");
        sender.sendMessage(ChatColor.WHITE + "/commands" + ChatColor.GRAY + " - Show this help menu");
        sender.sendMessage(ChatColor.DARK_GRAY + "   Permission: " + plugin.getPermissionManager().getPermission("commands"));
        sender.sendMessage("");
        
        // Custom Items Details
        sender.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "Custom Items Info:");
        sender.sendMessage(ChatColor.WHITE + "• " + ChatColor.RED + "Cock Slicer" + ChatColor.GRAY + " - Powerful sword with special effects");
        sender.sendMessage(ChatColor.WHITE + "• " + ChatColor.GREEN + "Bandage" + ChatColor.GRAY + " - Basic healing item (+4 hearts)");
        sender.sendMessage(ChatColor.WHITE + "• " + ChatColor.LIGHT_PURPLE + "Medkit" + ChatColor.GRAY + " - Advanced healing item (+8 hearts)");
        sender.sendMessage("");
        
        // Report System Info
        sender.sendMessage(ChatColor.AQUA + "" + ChatColor.BOLD + "Report System Info:");
        sender.sendMessage(ChatColor.GRAY + "• Reports are sent to Discord and stored locally");
        sender.sendMessage(ChatColor.GRAY + "• 5-minute cooldown between reports");
        sender.sendMessage(ChatColor.GRAY + "• Maximum 500 characters per report");
        sender.sendMessage("");
        
        // Footer
        sender.sendMessage(ChatColor.GOLD + "==========================================");
        sender.sendMessage(ChatColor.YELLOW + "      Plugin Version: " + ChatColor.WHITE + "1.0.0");
        sender.sendMessage(ChatColor.GOLD + "==========================================");
    }
}