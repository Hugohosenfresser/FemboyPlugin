# FemboyPlugin v1.2.0 - Installation Guide

## Quick Installation

### Step 1: Basic Setup
1. **Stop your Minecraft server**
2. **Place** `FemboyPlugin-1.2.0.jar` in your server's `plugins/` folder
3. **Start your server** - configuration files will auto-generate
4. **Verify installation** with `/commands` in-game

**Your plugin is now installed and working with basic features!**

---

## Discord Bot Setup (Optional but Recommended)

For full Discord integration with advanced logging:

### Step 1: Create Discord Bot
1. Go to https://discord.com/developers/applications
2. Create "New Application" → Add Bot
3. Copy the bot token

### Step 2: Configure Plugin
Edit `plugins/FemboyPlugin/config.yml`:
```yaml
discord_bot:
  enabled: true
  token: "YOUR_BOT_TOKEN_HERE"
  channels:
    commands: "CHANNEL_ID_HERE"
    player_events: "CHANNEL_ID_HERE"
    # ... set other channels as needed
```

### Step 3: Get Channel IDs
1. Enable Developer Mode in Discord (Settings → Advanced)
2. Right-click channels → "Copy ID"
3. Paste IDs in config

### Step 4: Test Setup
1. Restart server or `/reload`
2. Use `/discord status` to verify connection
3. Use `/discord test general` to test messaging

**For detailed setup: See [DISCORD_SETUP.md](DISCORD_SETUP.md)**

---

## Files Included

- `FemboyPlugin-1.2.0.jar` - Main plugin file
- `README.md` - Complete documentation
- `DISCORD_SETUP.md` - Discord bot setup guide
- `REPORT_SYSTEM.md` - Report system documentation
- `CHANGELOG.md` - Version history
- `RELEASE_NOTES_v1.2.0.md` - Detailed release information
- `INSTALL.md` - This installation guide

---

## What Works Immediately

After basic installation, these features work without Discord:

- **Custom Items**: `/givecustomitem`, `/givegui`  
- **Help System**: `/commands`, `/help`  
- **Report System**: Local storage (Discord optional)  
- **Permissions**: Full configurable permission system  

## What Requires Discord Setup

These advanced features require Discord bot configuration:

- **Event Logging**: Player join/leave, deaths, command usage  
- **Admin Management**: `/discord` commands for bot control  
- **Real-time Notifications**: Instant Discord alerts  
- **Multi-channel Logging**: Route events to different channels  

---

## Server Requirements

- **Minecraft**: 1.21+ (Paper/Spigot/Bukkit)
- **Java**: 17+
- **Dependencies**: Auto-downloaded (JDA, OkHttp)
- **RAM**: ~10MB additional memory usage
- **Discord**: Optional for enhanced features

---

## Need Help?

1. **Documentation**: Check README.md for comprehensive info
2. **Discord Setup**: See DISCORD_SETUP.md for step-by-step guide
3. **Commands**: Use `/discord status` to check bot health
4. **Console**: Check server logs for error messages
5. **Issues**: Create GitHub issue with error details

---

## Quick Commands to Try

After installation, test these commands:

```
/commands               # View all available commands
/givegui                # Open custom items GUI
/discord status         # Check Discord bot (if configured)
/report issue "test"    # Test the report system
```

---

**Enjoy your enhanced Minecraft server!**

*For technical support, check the documentation files or create a GitHub issue.*