package com.femboy.plugin.gui;

import org.bukkit.entity.Player;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.Material;
import org.bukkit.ChatColor;
import com.femboy.plugin.items.ItemManager;

import java.util.List;
import java.util.ArrayList;

public class ItemGUI {

    public static void open(Player player) {
        Inventory gui = Bukkit.createInventory(null, 27, "Select Custom Item");

        // Example: black glass border
        ItemStack pane = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta meta = pane.getItemMeta();
        meta.setDisplayName(" ");
        pane.setItemMeta(meta);

        for (int i = 0; i < gui.getSize(); i++) {
            if (i < 9 || i >= gui.getSize() - 9 || i % 9 == 0 || i % 9 == 8) {
                gui.setItem(i, pane);
            }
        }

        // Add custom items to the center slots
        List<String> itemKeys = new ArrayList<>(ItemManager.getKeys());
        int[] slots = {10, 11, 12, 13, 14, 15, 16}; // center row slots
        
        for (int i = 0; i < Math.min(itemKeys.size(), slots.length); i++) {
            String key = itemKeys.get(i);
            ItemStack item = ItemManager.getItem(key).clone();
            
            // Add amount selector info to lore
            ItemMeta itemMeta = item.getItemMeta();
            if (itemMeta != null) {
                List<String> lore = itemMeta.getLore();
                if (lore == null) lore = new ArrayList<>();
                lore.add("");
                lore.add(ChatColor.YELLOW + "Click to select amount!");
                itemMeta.setLore(lore);
                item.setItemMeta(itemMeta);
            }
            
            gui.setItem(slots[i], item);
        }

        // Open GUI
        player.openInventory(gui);
    }
}
