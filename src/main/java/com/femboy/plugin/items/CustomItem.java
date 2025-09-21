package com.femboy.plugin.items;

import org.bukkit.inventory.ItemStack;

public abstract class CustomItem {

    private final String key;

    public CustomItem(String key) {
        this.key = key.toLowerCase();
    }

    public String getKey() {
        return key;
    }

    public abstract ItemStack createItem();
}
