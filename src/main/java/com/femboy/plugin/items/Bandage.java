package com.femboy.plugin.items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.List;

public class Bandage extends CustomItem {

    public Bandage() {
        super("bandage");
    }

    @Override
    public ItemStack createItem() {
        ItemStack bandage = new ItemStack(Material.PAPER);
        ItemMeta meta = bandage.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(ChatColor.WHITE + "Bandage");
            meta.setLore(List.of(
                ChatColor.GRAY + "Heals 2 hearts when used.",
                ChatColor.YELLOW + "Right-click to apply!"
            ));
            meta.setCustomModelData(5001);
            bandage.setItemMeta(meta);
        }
        return bandage;
    }
}
