package com.femboy.plugin.gui;

import com.femboy.plugin.FemboyPlugin;
import com.femboy.plugin.items.ItemManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class GUIListener implements Listener {

    // Store items players clicked to set amount
    public static Map<Player, ItemStack> pendingAmount = new HashMap<>();

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!event.getView().getTitle().equals("Select Custom Item")) return;

        event.setCancelled(true);

        ItemStack current = event.getCurrentItem();
        if (current == null || current.getType().isAir()) return;

        // Disallow taking black stained glass panes (decorative border)
        if (current.getType() == Material.BLACK_STAINED_GLASS_PANE) {
            return; // do nothing
        }

        // Only proceed if the item is one of our custom items
        boolean isCustom = ItemManager.getKeys().stream()
                .anyMatch(k -> {
                    ItemStack is = ItemManager.getItem(k);
                    return is != null && is.getType() == current.getType();
                });
        if (!isCustom) return;

        Player player = (Player) event.getWhoClicked();
        ItemStack clicked = current.clone();

        // Get the display name or fall back to material type
        String itemName = clicked.getType().toString();
        if (clicked.hasItemMeta() && clicked.getItemMeta().hasDisplayName()) {
            itemName = clicked.getItemMeta().getDisplayName();
        }

        player.sendMessage(ChatColor.GREEN + "Type the amount for " + itemName + ChatColor.RESET + ChatColor.GREEN + " in chat:");
        pendingAmount.put(player, clicked);

        player.closeInventory();
    }
}
