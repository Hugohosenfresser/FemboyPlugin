package com.femboy.plugin.items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class RickrollStick extends CustomItem {

    public RickrollStick() {
        super("rickroll_stick");
    }

    @Override
    public ItemStack createItem() {
        ItemStack stick = new ItemStack(Material.STICK);
        ItemMeta meta = stick.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(ChatColor.LIGHT_PURPLE + "Rickroll Stick");
            meta.setLore(List.of(
                    ChatColor.GRAY + "Hit a player to play a surprise tune",
                    ChatColor.DARK_RED + "Admin Only"
            ));
            // Add a harmless enchant for the glowing effect and hide it
            meta.addEnchant(Enchantment.getByKey(org.bukkit.NamespacedKey.minecraft("unbreaking")), 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            stick.setItemMeta(meta);
        }
        return stick;
    }
}