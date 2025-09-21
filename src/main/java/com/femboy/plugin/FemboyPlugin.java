package com.femboy.plugin;

import org.bukkit.plugin.java.JavaPlugin;

// Commands
import com.femboy.plugin.commands.GiveItemCommand;
import com.femboy.plugin.commands.GiveGUICommand;
import com.femboy.plugin.commands.ReportCommand;

// GUI
import com.femboy.plugin.gui.GUIListener;
import com.femboy.plugin.gui.ChatAmountListener;

// Items
import com.femboy.plugin.items.ItemManager;
import com.femboy.plugin.items.CockSlicer;
import com.femboy.plugin.items.Bandage;
import com.femboy.plugin.items.Medkit;

// Custom item events
import com.femboy.plugin.events.CustomItemListener;

public class FemboyPlugin extends JavaPlugin {

    private static FemboyPlugin instance;
    private ReportCommand reportCommand;

    @Override
    public void onEnable() {
        instance = this;
        
        // Load configuration
        saveDefaultConfig();
        reloadConfig();

        // Register commands
        this.getCommand("givecustomitem").setExecutor(new GiveItemCommand());
        this.getCommand("givegui").setExecutor(new GiveGUICommand());
        
        // Initialize and register report command
        this.reportCommand = new ReportCommand(this);
        this.getCommand("report").setExecutor(reportCommand);
        this.getCommand("report").setTabCompleter(reportCommand);

        // Register events
        getServer().getPluginManager().registerEvents(new CustomItemListener(), this);
        getServer().getPluginManager().registerEvents(new GUIListener(), this);
        getServer().getPluginManager().registerEvents(new ChatAmountListener(), this);

        // Set tab completer for givecustomitem command
        this.getCommand("givecustomitem").setTabCompleter(new GiveItemCommand());

        // Register custom items
        ItemManager.registerItem(new CockSlicer());
        ItemManager.registerItem(new Bandage());
        ItemManager.registerItem(new Medkit());

        getLogger().info("FemboyPlugin enabled with custom items and report system!");
    }

    @Override
    public void onDisable() {
        getLogger().info("FemboyPlugin disabled.");
    }

    public static FemboyPlugin getInstance() {
        return instance;
    }
}
