package com.femboy.plugin.events;

import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.ChatColor;

public class CustomItemListener implements Listener {

    @EventHandler
    public void onUse(PlayerInteractEvent event) {
        ItemStack item = event.getItem();
        if (item == null || !item.hasItemMeta()) return;

        String name = ChatColor.stripColor(item.getItemMeta().getDisplayName());

        switch (name.toLowerCase()) {
            case "Cock Slicer" -> event.getPlayer().sendMessage(ChatColor.GREEN + "Cuck Slicer has been used!");
            case "bandage" -> {
                event.getPlayer().sendMessage(ChatColor.AQUA + "You healed yourself.");

                // Heal 2 hearts
                double newHealth = Math.min(event.getPlayer().getHealth() + 4.0, event.getPlayer().getMaxHealth());
                event.getPlayer().setHealth(newHealth);

                // Consume 1 bandage
                ItemStack itemStack = event.getItem();
                if (itemStack.getAmount() > 1) itemStack.setAmount(itemStack.getAmount() - 1);
                else event.getPlayer().getInventory().remove(itemStack);
            }
            case "medkit" -> {
                event.getPlayer().sendMessage(ChatColor.AQUA + "You healed yourself.");

                // Heal 4 hearts
                double newHealth = Math.min(event.getPlayer().getHealth() + 8.0, event.getPlayer().getMaxHealth());
                event.getPlayer().setHealth(newHealth);

                // Consume 1 medkit
                ItemStack itemStack = event.getItem();
                if (itemStack.getAmount() > 1) itemStack.setAmount(itemStack.getAmount() - 1);
                else event.getPlayer().getInventory().remove(itemStack);
            }
        }
    }
}
