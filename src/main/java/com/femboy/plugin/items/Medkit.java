package com.femboy.plugin.items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.List;

public class Medkit extends CustomItem {

    public Medkit() {
        super("medkit");
    }

    @Override
    public ItemStack createItem() {
        ItemStack medkit = new ItemStack(Material.PAPER);
        ItemMeta meta = medkit.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(ChatColor.WHITE + "Medkit");
            meta.setLore(List.of(
                ChatColor.GRAY + "Heals 4 hearts when used.",
                ChatColor.YELLOW + "Right-click to apply!"
            ));
            medkit.setItemMeta(meta);
        }
        return medkit;
    }
}
