# Discord Bot Integration Setup

This guide will help you set up Discord bot integration for your FemboyPlugin.

## Prerequisites

1. A Discord server where you have admin permissions
2. Access to Discord Developer Portal

## Step 1: Create a Discord Bot

1. Go to https://discord.com/developers/applications
2. Click "New Application" and give it a name (e.g., "Server Bot")
3. Go to the "Bot" section in the left sidebar
4. Click "Add Bot"
5. Copy the bot token (you'll need this for the config)

## Step 2: Invite Bot to Your Server

1. In the Discord Developer Portal, go to "OAuth2" > "URL Generator"
2. Under "Scopes", select "bot"
3. Under "Bot Permissions", select:
   - Send Messages
   - Use Slash Commands
   - Read Message History
   - View Channels
4. Copy the generated URL and open it in your browser
5. Select your server and authorize the bot

## Step 3: Get Channel IDs

1. Enable Developer Mode in Discord (User Settings > Advanced > Developer Mode)
2. Right-click on the channels where you want logs sent
3. Click "Copy ID" to get the channel ID
4. Note down these IDs for your config

## Step 4: Configure the Plugin

Edit your `config.yml`:

```yaml
discord_bot:
  # Enable Discord bot functionality
  enabled: true
  
  # Bot token from Discord Developer Portal
  token: "YOUR_BOT_TOKEN_HERE"
  
  # Channel IDs for different types of logs
  channels:
    commands: "1234567890123456789"          # Commands channel ID
    player_events: "1234567890123456789"     # Player join/leave events
    admin_actions: "1234567890123456789"     # Admin actions
    reports: "1234567890123456789"           # Reports
    general: "1234567890123456789"           # General events
    chat: "1234567890123456789"              # Player chat (if enabled)
    deaths: "1234567890123456789"            # Player deaths
    items: "1234567890123456789"             # Item usage
    alerts: "1234567890123456789"            # Server alerts
  
  # Event logging toggles
  events:
    log_commands: true              # Log command usage
    log_player_events: true         # Log joins/leaves
    log_admin_actions: true         # Log admin actions
    log_reports: true               # Log reports
    log_plugin_events: true         # Log plugin start/stop
    log_permission_denials: false   # Log permission denials
    log_chat: false                 # Log player chat (privacy concern)
    log_deaths: true                # Log player deaths
    log_item_usage: true            # Log custom item usage
    log_world_changes: true         # Log dimension switching
    log_advancements: false         # Log achievements
    log_server_alerts: true         # Log server performance
    log_all: false                  # Override all settings (true = log everything)
    log_all_commands: false         # Log ALL server commands, not just plugin commands
```

## Step 5: Test the Setup

1. Restart your server or reload the plugin
2. Use the command `/discord status` to check bot connection
3. Use `/discord test general` to test sending a message
4. Use `/discord channels` to verify channel configuration
5. Use `/discord events` to see event logging status

## Available Commands

- `/discord status` - Check Discord bot status
- `/discord reload` - Reload Discord bot configuration  
- `/discord test <channel>` - Test Discord bot connection
- `/discord toggle <event>` - Toggle event logging on/off
- `/discord channels` - List configured channels
- `/discord events` - List event logging status

## Channel Types

- **commands**: Command usage logs
- **player_events**: Player join/leave events
- **admin_actions**: Admin actions (giving items, etc.)
- **reports**: Player/issue reports
- **general**: General server events, world changes, achievements
- **chat**: Player chat messages (if enabled)
- **deaths**: Player death events
- **items**: Custom item usage
- **alerts**: Server performance alerts

## Event Types You Can Toggle

- `commands` - Command usage
- `player_events` - Player join/leave
- `admin_actions` - Admin actions
- `reports` - Report submissions
- `plugin_events` - Plugin start/stop
- `permission_denials` - Permission denied messages
- `chat` - Player chat
- `deaths` - Player deaths
- `item_usage` - Custom item usage
- `world_changes` - Dimension switching
- `advancements` - Achievement unlocks
- `server_alerts` - Performance alerts
- `all` - All events (overrides individual settings)

## Privacy Notes

- Chat logging is disabled by default for privacy
- Permission denials are disabled by default to reduce spam
- Advancements are disabled by default to reduce spam
- You can toggle any event type on/off as needed

## Troubleshooting

1. **Bot not connecting**: Check your bot token in config.yml
2. **No messages in channels**: Verify channel IDs and bot permissions
3. **Some events not logging**: Check event toggles in `/discord events`
4. **Permission errors**: Ensure the bot has "Send Messages" permission in target channels

## Permissions

- `femboyplugin.discord` - Access to Discord admin commands (default: op)
- `femboyplugin.admin` - Required for Discord admin commands