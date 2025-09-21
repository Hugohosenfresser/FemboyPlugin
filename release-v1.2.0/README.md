# FemboyPlugin

A comprehensive Minecraft plugin featuring custom items, Discord bot integration, and advanced server logging.

> **Version**: 1.2.0 | **Minecraft**: 1.21+ | **Java**: 17+

## ✨ Features

### 🗡️ Custom Items System
- **Cock Slicer**: Powerful sword with special combat effects
- **Bandage**: Quick healing item for health restoration  
- **Medkit**: Advanced healing item with greater restoration power
- **Interactive GUI**: User-friendly interface for item management
- **Admin Commands**: Easy item distribution and management

### 🤖 Discord Bot Integration
- **Full Discord Bot**: JDA-powered bot with real-time connection
- **Comprehensive Event Logging**: Track all server activities
- **Multiple Channel Support**: Route different events to different channels
- **Configurable Events**: Toggle individual event types on/off
- **Real-time Notifications**: Instant Discord alerts for server events
- **Admin Management**: In-game commands to control Discord integration

### 📊 Advanced Logging
- **Player Events**: Join/leave, deaths, world changes, achievements
- **Command Logging**: Track all command usage (configurable)
- **Admin Actions**: Log item giving, permissions, and admin activities
- **Chat Monitoring**: Optional player chat logging (privacy-friendly defaults)
- **Server Alerts**: Performance monitoring and system notifications

### 📝 Report System
- **Dual Integration**: Both Discord webhooks AND bot support
- **Player Reports**: Report problematic players with detailed reasons
- **Issue Reports**: Report server bugs and technical problems
- **Rich Discord Embeds**: Beautiful formatted reports with timestamps
- **Cooldown Protection**: Anti-spam measures with configurable timing
- **Local Backup**: Automatic storage of all reports in YAML format
- **Tab Completion**: Smart autocomplete for enhanced user experience

## 🎮 Commands

### Core Commands
- `/givecustomitem <item> [amount]` - Give custom items to players
- `/givegui` - Open the interactive custom items GUI
- `/commands` - Display comprehensive help with all available commands

### Report System
- `/report player <playername> <reason>` - Report a problematic player
- `/report issue <description>` - Report server issues or bugs
- **Aliases**: `/reports`, `/ticket`

### Discord Bot Management ⚙️
- `/discord status` - Check Discord bot connection and status
- `/discord reload` - Reload Discord bot configuration
- `/discord test <channel>` - Test Discord bot connection to specific channels
- `/discord toggle <event>` - Toggle specific event logging on/off
- `/discord channels` - List all configured Discord channels
- `/discord events` - View current event logging configuration
- **Aliases**: `/discordbot`

### Help System
- `/help` or `/pluginhelp` - Alternative aliases for `/commands`

## 🚀 Quick Start

### Basic Installation
1. Download `FemboyPlugin-v1.2.0.jar` from releases
2. Place the JAR file in your server's `plugins/` folder
3. Start/restart your server to generate configuration files
4. Configure Discord integration (optional)
5. Enjoy the enhanced server experience!

### Discord Bot Setup (Recommended) 🤖

For full Discord integration with advanced logging:

1. **Create a Discord Bot**:
   - Go to https://discord.com/developers/applications
   - Create a new application and add a bot
   - Copy the bot token

2. **Configure the Plugin**:
   ```yaml
   discord_bot:
     enabled: true
     token: "YOUR_BOT_TOKEN_HERE"
   ```

3. **Set Channel IDs**:
   - Enable Developer Mode in Discord
   - Right-click channels and "Copy ID"
   - Add channel IDs to your config

**📄 For detailed setup instructions, see [DISCORD_SETUP.md](DISCORD_SETUP.md)**

### Legacy Webhook Setup (Alternative)

For basic Discord reporting without a bot:

1. Right-click your Discord channel
2. Edit Channel → Integrations → Webhooks → New Webhook
3. Copy the webhook URL
4. Add to config:
   ```yaml
   discord:
     webhook_url: "YOUR_WEBHOOK_URL_HERE"
   ```

## 🔐 Permissions

The plugin uses a fully configurable permission system compatible with any permission plugin:

### Default Permissions
- `femboyplugin.givecustomitem` - Use /givecustomitem command (**op only**)
- `femboyplugin.givegui` - Use /givegui command (**everyone**)
- `femboyplugin.report` - Use /report commands (**everyone**)
- `femboyplugin.commands` - Use /commands help (**everyone**)
- `femboyplugin.admin` - Admin permissions (**op only**)
- `femboyplugin.discord` - Use /discord commands (**op only**) 🆕
- `femboyplugin.*` - All plugin permissions

### Permission Configuration
You can disable permission checks by setting them to empty strings:

```yaml
permissions:
  givecustomitem: "femboyplugin.givecustomitem"  # Require permission
  report: ""  # Allow everyone (disable permission check)
  discord: "femboyplugin.discord"  # Discord admin commands
  no_permission: "&cYou don't have permission to use this command!"
```

## ⚙️ Configuration

### Core Configuration
Edit `plugins/FemboyPlugin/config.yml`:

```yaml
# Permissions (can be disabled by setting to "")
permissions:
  givecustomitem: "femboyplugin.givecustomitem"
  givegui: "femboyplugin.givegui"
  report: "femboyplugin.report"
  commands: "femboyplugin.commands"
  admin: "femboyplugin.admin"
  discord: "femboyplugin.discord"  # NEW: Discord admin commands
  no_permission: "&cYou don't have permission to use this command!"

# Discord Bot Integration (NEW in v1.2.0)
discord_bot:
  enabled: true  # Enable Discord bot features
  token: "YOUR_BOT_TOKEN_HERE"  # Bot token from Discord Developer Portal
  
  # Channel IDs for different log types
  channels:
    commands: "CHANGE_ME"          # Command usage logs
    player_events: "CHANGE_ME"     # Player join/leave
    admin_actions: "CHANGE_ME"     # Admin actions
    reports: "CHANGE_ME"           # Report submissions
    general: "CHANGE_ME"           # General events
    chat: "CHANGE_ME"              # Player chat (if enabled)
    deaths: "CHANGE_ME"            # Player deaths
    items: "CHANGE_ME"             # Item usage
    alerts: "CHANGE_ME"            # Server alerts
  
  # Event logging toggles
  events:
    log_commands: true             # Log command usage
    log_player_events: true        # Log player join/leave
    log_admin_actions: true        # Log admin actions
    log_reports: true              # Log reports
    log_plugin_events: true        # Log plugin start/stop
    log_permission_denials: false  # Log permission denials
    log_chat: false                # Log chat (privacy)
    log_deaths: true               # Log player deaths
    log_item_usage: true           # Log item usage
    log_world_changes: true        # Log world changes
    log_advancements: false        # Log achievements
    log_server_alerts: true        # Log server alerts
    log_all: false                 # Override all (log everything)
    log_all_commands: false        # Log ALL commands (not just plugin)

# Legacy Discord Webhook (still supported)
discord:
  webhook_url: "CHANGE_ME"         # Discord webhook URL
  bot_name: "Server Reports"       # Webhook bot name
  bot_avatar: ""                   # Webhook avatar URL

# Report System
reports:
  cooldown_seconds: 300            # 5 minutes between reports
  max_message_length: 500          # Max report length
  log_to_console: true             # Console logging
  store_locally: true              # Local YAML storage
```

## 💻 Requirements

- **Minecraft Server**: 1.21+ (Paper/Spigot/Bukkit)
- **Java**: 17+
- **Dependencies**: Automatically downloaded (JDA, OkHttp)
- **Optional**: Discord server for bot integration

## 🔍 What's New in v1.2.0

🆕 **Major Discord Bot Integration**
- Full JDA-powered Discord bot with real-time connection
- Comprehensive server event logging to Discord
- Admin commands for Discord bot management
- Multiple channel support with configurable routing
- Privacy-friendly defaults with granular event controls

## 🛠️ Building

```bash
mvn clean package
```

## 📄 Documentation

- **Discord Setup**: [DISCORD_SETUP.md](DISCORD_SETUP.md) - Complete Discord bot setup guide
- **Report System**: [REPORT_SYSTEM.md](REPORT_SYSTEM.md) - Detailed reporting documentation
- **Changelog**: [CHANGELOG.md](CHANGELOG.md) - Version history and changes

## 💬 Support

Need help? Check out our documentation or create an issue!

1. 📄 Review the setup guides in the documentation files
2. 🔍 Check server console for error messages
3. ⚙️ Verify your Discord bot token and channel IDs
4. 📝 Create a GitHub issue with details

## 📜 License

This project is licensed under the MIT License.
