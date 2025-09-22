package com.femboy.plugin.items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.List;

public class RainbowHelmet extends CustomItem {

    public RainbowHelmet() {
        super("rainbow_helmet");
    }

    @Override
    public ItemStack createItem() {
        ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
        ItemMeta meta = helmet.getItemMeta();
        if (meta instanceof LeatherArmorMeta lam) {
            lam.setDisplayName(ChatColor.LIGHT_PURPLE + "Rainbow Helmet");
            lam.setLore(List.of(
                    ChatColor.GRAY + "Colors cycle while worn",
                    ChatColor.DARK_RED + "Admin Only"
            ));
            // Initial color (will be animated by manager)
            lam.setColor(org.bukkit.Color.fromRGB(0xFF0000));
            helmet.setItemMeta(lam);
        } else if (meta != null) {
            meta.setDisplayName(ChatColor.LIGHT_PURPLE + "Rainbow Helmet");
            helmet.setItemMeta(meta);
        }
        return helmet;
    }
}
