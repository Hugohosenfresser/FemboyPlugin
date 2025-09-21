# FemboyPlugin v1.0.0 Release Notes

## Initial Release

This is the first official release of FemboyPlugin, featuring a comprehensive custom items system and Discord-integrated reporting functionality.

## Features

### Custom Items System
- **Cock Slicer**: Powerful sword with special combat effects
- **Bandage**: Basic healing item for quick health restoration
- **Medkit**: Advanced healing item with greater restoration power
- **Interactive GUI**: User-friendly interface for item management

### Discord Report System
- **Player Reports**: Report problematic players with detailed reasons
- **Issue Reports**: Report server bugs and technical issues
- **Discord Integration**: Beautiful Discord embeds sent via webhooks
- **Cooldown System**: 5-minute cooldown to prevent spam
- **Local Storage**: Automatic backup of all reports in YAML format
- **Tab Completion**: Smart autocomplete for player names and commands

### Commands
- `/givecustomitem <item> [amount]` - Give custom items
- `/givegui` - Open the items GUI
- `/report player <name> <reason>` - Report a player
- `/report issue <description>` - Report an issue
- Command aliases: `/reports`, `/ticket`

## Technical Features

- **Asynchronous Operations**: Non-blocking Discord webhook calls
- **Configurable Settings**: Comprehensive config.yml with all options
- **Error Handling**: Robust error handling and validation
- **Extensive Logging**: Console logging for debugging and monitoring

## Requirements

- **Minecraft**: 1.21+ (Paper/Spigot/Bukkit)
- **Java**: 17+
- **Dependencies**: All included (OkHttp for Discord integration)

## Installation

1. Download `FemboyPlugin-v1.0.0.jar`
2. Place in your server's `plugins/` folder
3. Start the server to generate config files
4. Configure Discord webhook in `plugins/FemboyPlugin/config.yml`
5. Restart the server

## Configuration

Edit `plugins/FemboyPlugin/config.yml`:

```yaml
discord:
  webhook_url: "YOUR_DISCORD_WEBHOOK_URL_HERE"
  bot_name: "Server Reports"

reports:
  cooldown_seconds: 300      # 5 minutes
  max_message_length: 500    # Max characters
  log_to_console: true       # Enable console logging
  store_locally: true        # Enable local storage
```

### Getting a Discord Webhook URL:
1. Right-click your Discord channel
2. Edit Channel → Integrations → Webhooks → New Webhook  
3. Copy the webhook URL

## Documentation

- **Setup Guide**: See README.md
- **Report System**: See REPORT_SYSTEM.md for detailed documentation

## Known Issues

- None reported in this release

## Future Plans

- Additional custom items
- Advanced report management commands
- Permission system integration

## Support

If you encounter issues:
1. Check server console for error messages
2. Verify Discord webhook configuration
3. Review documentation files
4. Create an issue on GitHub

---

**File**: `FemboyPlugin-v1.0.0.jar`  
**Size**: ~33KB  
**Build**: Maven 3.9+  
**Tested**: Minecraft 1.21+ with Paper