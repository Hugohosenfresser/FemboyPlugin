package com.femboy.plugin.commands;

import com.femboy.plugin.items.ItemManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.ChatColor;
import org.bukkit.command.TabCompleter;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;


public class GiveItemCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(ChatColor.RED + "Only players can run this command.");
            return true;
        }

        if (args.length == 0) {
            player.sendMessage(ChatColor.RED + "Usage: /givecustomitem <item> [amount]");
            return true;
        }

        String key = args[0].toLowerCase();
        if (!ItemManager.hasItem(key)) {
            player.sendMessage(ChatColor.RED + "Unknown item: " + key);
            return true;
        }

        int amount = 1; // default
        if (args.length >= 2) {
            try {
                amount = Integer.parseInt(args[1]);
                if (amount < 1) amount = 1;
                if (amount > 500) amount = 500; // max stack size
            } catch (NumberFormatException e) {
                player.sendMessage(ChatColor.RED + "Invalid amount! Using 1 instead.");
            }
        }

        ItemStack item = ItemManager.getItem(key).clone();
        item.setAmount(amount);
        player.getInventory().addItem(item);
        player.sendMessage(ChatColor.GREEN + "You Recieved: " + amount + "x " + key);

        return true;
    }
    
    // --- TAB COMPLETION ---
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) { // first argument: item key
            List<String> suggestions = new ArrayList<>();
            String partial = args[0].toLowerCase();

            for (String key : ItemManager.getKeys()) { // assuming ItemManager has getKeys() returning Set<String>
                if (key.toLowerCase().startsWith(partial)) {
                    suggestions.add(key);
                }
            }
            Collections.sort(suggestions); // optional, sorts alphabetically
            return suggestions;
        } else if (args.length == 2) { // second argument: amount
            return List.of("1", "16", "32", "64"); // common stack amounts
        }

        return Collections.emptyList();
    }
}