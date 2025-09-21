# FemboyPlugin

A comprehensive Minecraft plugin with custom items and a Discord-integrated report system.

> **Version**: 1.0.0 | **Minecraft**: 1.21+ | **Java**: 17+

## Features

### Custom Items
- **Cock Slicer**: A powerful sword with special effects
- **Bandage**: Healing item that restores health
- **Medkit**: Advanced healing item with greater restoration

### Report System
- Report problematic players directly to Discord
- Report server issues and bugs
- Discord webhook integration with rich embeds
- Cooldown system to prevent spam
- Local storage for report tracking
- Tab completion for enhanced user experience

### GUI System
- Interactive GUI for item management
- Custom item distribution interface
- User-friendly item selection

## Commands

- `/givecustomitem <item> [amount]` - Give custom items to players
- `/givegui` - Open the custom items GUI
- `/report player <playername> <reason>` - Report a player
- `/report issue <description>` - Report an issue
- Aliases: `/reports`, `/ticket`

## Setup

1. Download and place the plugin JAR in your `plugins/` folder
2. Start the server to generate configuration files
3. Configure Discord webhook in `plugins/FemboyPlugin/config.yml`
4. Restart the server

### Discord Integration Setup

1. Go to your Discord server
2. Right-click on the channel for reports
3. Edit Channel → Integrations → Webhooks → New Webhook
4. Copy the webhook URL
5. Replace `CHANGE_ME` in the config with your webhook URL

## Configuration

Edit `plugins/FemboyPlugin/config.yml`:

```yaml
discord:
  webhook_url: "YOUR_WEBHOOK_URL_HERE"
  bot_name: "Server Reports"

reports:
  cooldown_seconds: 300
  max_message_length: 500
  log_to_console: true
  store_locally: true
```

## Requirements

- Minecraft Server 1.21+
- Paper/Spigot/Bukkit
- Java 17+

## Building

```bash
mvn clean package
```

## License

This project is licensed under the MIT License.

## Support

For detailed documentation on the report system, see [REPORT_SYSTEM.md](REPORT_SYSTEM.md).