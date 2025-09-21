package com.femboy.plugin.items;

import org.bukkit.inventory.ItemStack;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ItemManager {

    private static final Map<String, ItemStack> items = new HashMap<>();

    public static void registerItem(CustomItem item) {
        items.put(item.getKey().toLowerCase(), item.createItem());
    }

    public static ItemStack getItem(String key) {
        return items.get(key.toLowerCase());
    }

    public static boolean hasItem(String key) {
        return items.containsKey(key.toLowerCase());
    }

    public static Set<String> getKeys() {
        return items.keySet();
    }
}
