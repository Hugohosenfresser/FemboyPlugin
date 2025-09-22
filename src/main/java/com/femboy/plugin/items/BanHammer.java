package com.femboy.plugin.items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class BanHammer extends CustomItem {

    public BanHammer() {
        super("ban_hammer");
    }

    @Override
    public ItemStack createItem() {
        ItemStack axe = new ItemStack(Material.NETHERITE_AXE);
        ItemMeta meta = axe.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(ChatColor.RED + "Ban Hammer");
            meta.setLore(List.of(
                    ChatColor.GRAY + "Smite rulebreakers with a swing",
                    ChatColor.DARK_RED + "Admin Only"
            ));
            meta.addEnchant(Enchantment.getByKey(org.bukkit.NamespacedKey.minecraft("sharpness")), 5, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            axe.setItemMeta(meta);
        }
        return axe;
    }
}
