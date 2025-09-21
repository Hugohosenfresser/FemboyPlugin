package com.femboy.plugin;

import org.bukkit.plugin.java.JavaPlugin;

// Commands
import com.femboy.plugin.commands.GiveItemCommand;
import com.femboy.plugin.commands.GiveGUICommand;
import com.femboy.plugin.commands.ReportCommand;
import com.femboy.plugin.commands.CommandsCommand;
import com.femboy.plugin.commands.DiscordCommand;

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
import com.femboy.plugin.events.PlayerEventListener;
import com.femboy.plugin.events.DiscordEventListener;

// Utils
import com.femboy.plugin.utils.PermissionManager;
import com.femboy.plugin.utils.DiscordBot;
import com.femboy.plugin.utils.EventLogger;

public class FemboyPlugin extends JavaPlugin {

    private static FemboyPlugin instance;
    private ReportCommand reportCommand;
    private PermissionManager permissionManager;
    private DiscordBot discordBot;
    private EventLogger eventLogger;

    @Override
    public void onEnable() {
        instance = this;
        
        // Load configuration
        saveDefaultConfig();
        reloadConfig();
        
        // Initialize permission manager
        this.permissionManager = new PermissionManager(this);
        
        // Initialize Discord bot
        this.discordBot = new DiscordBot(this);
        this.eventLogger = new EventLogger(this, discordBot);
        
        // Start Discord bot asynchronously
        getServer().getScheduler().runTaskAsynchronously(this, () -> {
            discordBot.initialize().thenAccept(success -> {
                if (success) {
                    getLogger().info("Discord bot initialized successfully!");
                    // Log plugin startup
                    eventLogger.logPluginEvent("PLUGIN_ENABLED", "FemboyPlugin has been enabled with Discord integration");
                } else {
                    getLogger().warning("Discord bot failed to initialize - check your configuration");
                }
            });
        });

        // Register commands
        this.getCommand("givecustomitem").setExecutor(new GiveItemCommand());
        this.getCommand("givegui").setExecutor(new GiveGUICommand());
        
        // Initialize and register report command
        this.reportCommand = new ReportCommand(this);
        this.getCommand("report").setExecutor(reportCommand);
        this.getCommand("report").setTabCompleter(reportCommand);
        
        // Register commands help command
        this.getCommand("commands").setExecutor(new CommandsCommand(this));
        
        // Register Discord admin command
        DiscordCommand discordCommand = new DiscordCommand(this);
        this.getCommand("discord").setExecutor(discordCommand);
        this.getCommand("discord").setTabCompleter(discordCommand);

        // Register events
        getServer().getPluginManager().registerEvents(new CustomItemListener(), this);
        getServer().getPluginManager().registerEvents(new GUIListener(), this);
        getServer().getPluginManager().registerEvents(new ChatAmountListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerEventListener(this), this);
        getServer().getPluginManager().registerEvents(new DiscordEventListener(this), this);

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
        // Log plugin shutdown
        if (eventLogger != null && eventLogger.isEnabled()) {
            eventLogger.logPluginEvent("PLUGIN_DISABLED", "FemboyPlugin is shutting down");
        }
        
        // Shutdown Discord bot
        if (discordBot != null) {
            discordBot.shutdown();
        }
        
        getLogger().info("FemboyPlugin disabled.");
    }

    public static FemboyPlugin getInstance() {
        return instance;
    }
    
    public PermissionManager getPermissionManager() {
        return permissionManager;
    }
    
    public DiscordBot getDiscordBot() {
        return discordBot;
    }
    
    public EventLogger getEventLogger() {
        return eventLogger;
    }
}
