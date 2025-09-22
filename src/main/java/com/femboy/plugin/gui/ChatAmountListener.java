package com.femboy.plugin.gui;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class ChatAmountListener implements Listener {

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        if (!GUIListener.pendingAmount.containsKey(player)) return;

        event.setCancelled(true);

        ItemStack item = GUIListener.pendingAmount.get(player);

        int amount;
        try {
            amount = Integer.parseInt(event.getMessage());
            if (amount < 1) amount = 1;
            if (amount > 500) amount = 500;
        } catch (NumberFormatException e) {
            player.sendMessage(ChatColor.RED + "Invalid number! Using 1.");
            amount = 1;
        }

        item.setAmount(amount);
        player.getInventory().addItem(item);

        String displayName;
        if (item.hasItemMeta() && item.getItemMeta().hasDisplayName()) {
            displayName = item.getItemMeta().getDisplayName();
        } else {
            displayName = ChatColor.WHITE + item.getType().name().toLowerCase().replace('_', ' ');
        }
        player.sendMessage(ChatColor.GREEN + "You received " + amount + "x " + displayName + ChatColor.RESET + ChatColor.GREEN);
        
        GUIListener.pendingAmount.remove(player);
    }
}
