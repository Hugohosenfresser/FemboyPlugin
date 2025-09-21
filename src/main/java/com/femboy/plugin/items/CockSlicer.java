package com.femboy.plugin.items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.List;

public class CockSlicer extends CustomItem {

    public CockSlicer() {
        super("cockslicer"); // use lowercase key
    }

    @Override
    public ItemStack createItem() {
        ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
        ItemMeta meta = sword.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(ChatColor.RED + "Cock Slicer");
            meta.setLore(List.of(
                ChatColor.GRAY + "A legendary sword of heroes.",
                ChatColor.YELLOW + "Deals extra damage :333"
            ));
            meta.addEnchant(Enchantment.getByKey(org.bukkit.NamespacedKey.minecraft("sharpness")), 10, true);
            meta.addEnchant(Enchantment.getByKey(org.bukkit.NamespacedKey.minecraft("fire_aspect")), 10, true);
            sword.setItemMeta(meta);
        }
        return sword;
    }
}
