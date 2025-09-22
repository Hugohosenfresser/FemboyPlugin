package com.femboy.plugin.items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class GrapplingHook extends CustomItem {

    public GrapplingHook() {
        super("grappling_hook");
    }

    @Override
    public ItemStack createItem() {
        ItemStack rod = new ItemStack(Material.FISHING_ROD);
        ItemMeta meta = rod.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(ChatColor.AQUA + "Grappling Hook");
            meta.setLore(List.of(
                    ChatColor.GRAY + "Cast and reel to pull yourself",
                    ChatColor.DARK_RED + "Admin Only"
            ));
            meta.addEnchant(Enchantment.getByKey(org.bukkit.NamespacedKey.minecraft("unbreaking")), 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            rod.setItemMeta(meta);
        }
        return rod;
    }
}
