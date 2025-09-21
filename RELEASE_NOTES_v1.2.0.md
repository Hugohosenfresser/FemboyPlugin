# FemboyPlugin v1.2.0 Release Notes

## 🤖 Discord Bot Integration Release

This major release introduces comprehensive Discord bot integration, transforming FemboyPlugin into a complete server monitoring and management solution.

---

## 🌟 What's New

### 🤖 Full Discord Bot Integration
- **JDA-Powered Bot**: Real-time Discord bot connection with advanced features
- **Multi-Channel Logging**: Route different events to separate Discord channels
- **Admin Commands**: Manage Discord integration directly from your Minecraft server
- **Real-time Notifications**: Instant Discord alerts for all server activities

### 📊 Comprehensive Server Monitoring
- **Player Activity**: Join/leave events with detailed information
- **Command Tracking**: Monitor command usage across your server
- **Admin Actions**: Track administrative activities and item giving
- **Death Events**: Player deaths with locations and causes
- **World Changes**: Monitor dimension switching and teleportation
- **Achievement Tracking**: Optional player advancement notifications
- **Performance Alerts**: Server health and performance monitoring

### 🎛️ Advanced Management
- **In-Game Controls**: Manage Discord bot without leaving Minecraft
- **Event Toggles**: Enable/disable specific logging types on demand
- **Channel Testing**: Test Discord connections to verify setup
- **Configuration Reload**: Update settings without server restart
- **Status Monitoring**: Check bot connection and health in real-time

---

## 🚀 Quick Start

### 1. Download & Install
```bash
# Download FemboyPlugin-v1.2.0.jar
# Place in your server's plugins/ folder
# Restart server
```

### 2. Basic Setup (Works without Discord)
The plugin works immediately after installation with all core features:
- Custom items system
- Interactive GUI
- Local report storage
- Help system

### 3. Discord Bot Setup (Recommended)
For full Discord integration:

1. **Create Discord Bot**:
   - Visit https://discord.com/developers/applications
   - Create application → Add bot → Copy token

2. **Configure Plugin**:
   ```yaml
   discord_bot:
     enabled: true
     token: "YOUR_BOT_TOKEN_HERE"
   ```

3. **Set Channel IDs**:
   - Enable Developer Mode in Discord
   - Right-click channels → Copy ID
   - Update config with channel IDs

**📄 Complete setup guide: [DISCORD_SETUP.md](DISCORD_SETUP.md)**

---

## 🎮 New Commands

### Discord Bot Management
- `/discord status` - Check bot connection and status
- `/discord reload` - Reload bot configuration
- `/discord test <channel>` - Test specific channel connections
- `/discord toggle <event>` - Toggle event logging types
- `/discord channels` - List configured channels
- `/discord events` - View logging configuration
- **Aliases**: `/discordbot`

### Enhanced Help System
- `/commands` - Comprehensive command help with examples
- **Aliases**: `/help`, `/pluginhelp`

---

## ⚙️ Configuration Overview

### Discord Bot Settings
```yaml
discord_bot:
  enabled: true
  token: "YOUR_BOT_TOKEN_HERE"
  
  channels:
    commands: "CHANNEL_ID"      # Command usage
    player_events: "CHANNEL_ID" # Player join/leave
    admin_actions: "CHANNEL_ID" # Admin activities
    reports: "CHANNEL_ID"       # Player reports
    general: "CHANNEL_ID"       # General events
    chat: "CHANNEL_ID"          # Player chat (optional)
    deaths: "CHANNEL_ID"        # Player deaths
    items: "CHANNEL_ID"         # Item usage
    alerts: "CHANNEL_ID"        # Server alerts
  
  events:
    log_commands: true          # Track command usage
    log_player_events: true     # Track joins/leaves
    log_admin_actions: true     # Track admin actions
    log_reports: true           # Track reports
    log_deaths: true            # Track player deaths
    log_chat: false             # Track chat (privacy)
    # ... and many more options
```

### Channel Types Explained
- **commands**: `/givecustomitem`, `/report`, etc.
- **player_events**: Player joins/leaves with IP and counts
- **admin_actions**: Item giving, permission changes
- **reports**: Player/issue reports (both webhook + bot)
- **general**: Achievements, world changes, plugin events
- **chat**: Player messages (disabled by default)
- **deaths**: Player deaths with locations
- **items**: Custom item usage tracking
- **alerts**: Server performance notifications

---

## 🔐 Permissions

### New Permission
- `femboyplugin.discord` - Discord bot management commands (op only)

### All Permissions
- `femboyplugin.givecustomitem` - Give custom items (op only)
- `femboyplugin.givegui` - Access item GUI (everyone)
- `femboyplugin.report` - Submit reports (everyone)
- `femboyplugin.commands` - View help (everyone)
- `femboyplugin.admin` - Admin permissions (op only)
- `femboyplugin.*` - All permissions

---

## 🛡️ Privacy & Security

### Privacy-Friendly Defaults
- **Chat logging disabled** by default
- **Permission denials** logging disabled to reduce spam
- **Achievements** logging disabled to prevent notification overload
- **IP addresses** only in player events (admin channels only)

### Security Features
- **Secure token storage** in configuration
- **Permission-based access** to Discord management
- **Graceful error handling** prevents crashes
- **Async operations** prevent server lag

---

## 🔄 Backward Compatibility

### Existing Users
- **No breaking changes** - all existing functionality preserved
- **Webhook system** continues to work alongside bot
- **Configuration migration** - existing configs work without changes
- **Command compatibility** - all existing commands unchanged

### Migration Path
1. Install v1.2.0 (existing features continue working)
2. Optionally set up Discord bot for enhanced features
3. Configure event logging as desired
4. Test with `/discord test` commands

---

## 🛠️ Technical Details

### Dependencies (Auto-Downloaded)
- **JDA**: 5.0.0-beta.21 (Discord bot library)
- **OkHttp**: 4.11.0 (HTTP client for webhooks)

### Performance
- **Asynchronous operations** - no server lag
- **Efficient memory usage** - minimal footprint
- **Error resilience** - continues working even if Discord fails
- **Configurable logging levels** - tune performance as needed

### Compatibility
- **Minecraft**: 1.21+
- **Server Software**: Paper/Spigot/Bukkit
- **Java**: 17+
- **Permission Plugins**: LuckPerms, PermissionsEx, etc.

---

## 📚 Documentation

### Setup Guides
- **[DISCORD_SETUP.md](DISCORD_SETUP.md)** - Complete Discord bot setup
- **[REPORT_SYSTEM.md](REPORT_SYSTEM.md)** - Enhanced reporting documentation
- **[README.md](README.md)** - Updated main documentation

### Configuration
- **Example configs** with detailed comments
- **Channel setup** step-by-step guide
- **Permission configuration** examples
- **Troubleshooting** common issues

---

## 🐛 Known Issues & Limitations

### Minor Limitations
- Discord bot requires Java 17+ (server requirement)
- Some event types may generate high message volume
- Channel permissions must be configured correctly in Discord

### Solutions Provided
- **Configurable event toggles** to control message volume
- **Comprehensive documentation** for setup
- **Test commands** to verify configuration
- **Detailed error logging** for troubleshooting

---

## 🔮 Future Roadmap

### Planned Features
- **Custom Discord commands** - trigger Minecraft commands from Discord
- **Player statistics** - advanced analytics and reporting
- **Web dashboard** - browser-based configuration interface
- **Plugin integrations** - compatibility with popular plugins
- **Advanced filtering** - more granular event control

---

## 📦 Download & Installation

### Files Included
- `FemboyPlugin-v1.2.0.jar` - Main plugin file (~50KB)
- `DISCORD_SETUP.md` - Discord bot setup guide
- `CHANGELOG.md` - Version history
- `README.md` - Complete documentation

### Installation Steps
1. **Download** `FemboyPlugin-v1.2.0.jar` from releases
2. **Stop** your Minecraft server
3. **Place** JAR file in `plugins/` folder
4. **Start** server (config files auto-generate)
5. **Configure** Discord integration (optional)
6. **Test** with `/discord status` command
7. **Enjoy** enhanced server monitoring!

---

## 🤝 Support

### Getting Help
1. **Check documentation** - comprehensive guides included
2. **Verify setup** - use `/discord status` and test commands
3. **Check logs** - server console shows detailed error messages
4. **Create issue** - GitHub issues for bug reports

### Community
- **GitHub Repository** - source code and issue tracking
- **Discord Setup Guide** - step-by-step instructions
- **Configuration Examples** - working config templates

---

**Happy server monitoring! 🎮**

---

*Release Date: January 2025*  
*File: FemboyPlugin-v1.2.0.jar*  
*Size: ~50KB*  
*Build: Maven 3.9+ with Java 17+*
*Tested: Minecraft 1.21+ with Paper/Spigot*