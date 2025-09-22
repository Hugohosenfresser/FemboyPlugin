# FemboyPlugin

Comprehensive Minecraft server plugin featuring custom items, Discord bot integration, advanced logging, and a player/staff report system.

- Minecraft: 1.21+
- Java: 17+
- Build: Maven (pom.xml)
- Versioning: Refer to plugin.yml and GitHub Releases for the latest version

## Features

### Custom Items
- Cock Slicer: special-effect sword
- Bandage: quick healing item
- Medkit: advanced healing item
- Interactive GUI for item management
- Admin-friendly distribution commands

### Discord Integration (JDA)
- Full Discord bot using JDA
- Real-time event logging to Discord
- Multiple channel routing by event type
- Toggleable event categories
- In-game admin controls for the bot

### Advanced Logging
- Player events: join/leave, deaths, advancements (optional)
- Command logging (scoped or all, configurable)
- Admin actions and server alerts
- Optional chat logging with privacy-friendly defaults

### Report System
- Player and issue reports
- Discord embeds via bot or webhooks
- Cooldown and anti-spam
- Local YAML storage backup
- Tab completion for ease of use

## Commands

### Core
- /givecustomitem <item> [amount] — Give custom items
- /givegui — Open the custom item GUI
- /commands — Show all available plugin commands

### Reports
- /report player <playername> <reason> — Report a player
- /report issue <description> — Report a server issue
- Aliases: /reports, /ticket

### Discord Bot Management
- /discord status — Check bot status
- /discord reload — Reload bot configuration
- /discord test <channel> — Test connectivity to a channel
- /discord toggle <event> — Toggle an event category
- /discord channels — List configured channels
- /discord events — Show event logging configuration
- Aliases: /discordbot

### Help
- /help, /pluginhelp — Aliases for /commands

## Permissions

- femboyplugin.givecustomitem — Use /givecustomitem (default: op)
- femboyplugin.givegui — Use /givegui (default: everyone)
- femboyplugin.report — Use /report (default: everyone)
- femboyplugin.commands — Use /commands (default: everyone)
- femboyplugin.admin — Admin permissions (default: op)
- femboyplugin.discord — Use /discord (default: op)
- femboyplugin.* — All plugin permissions

Note: You can disable permission checks by setting a permission key to an empty string in config.

## Installation

1) Download the latest release JAR from GitHub Releases
2) Place the JAR into your server's plugins/ directory
3) Start or restart the server to generate configuration files
4) Configure Discord integration if desired

## Discord Setup

- Bot integration (recommended): see DISCORD_SETUP.md for a complete setup guide
- Legacy webhooks (alternative): configure a webhook URL in config.yml

## Configuration

Edit plugins/FemboyPlugin/config.yml. Example keys include:
- permissions: permission nodes and messages
- discord_bot: enabled, token, channels, events
- discord: webhook_url, bot_name, bot_avatar
- reports: cooldown_seconds, max_message_length, log_to_console, store_locally

See the repository documentation for detailed, commented examples.

## Building

mvn clean package

The built JAR will be in target/.

## Documentation

- Discord Setup: DISCORD_SETUP.md
- Report System: REPORT_SYSTEM.md
- Changelog: CHANGELOG.md

## Support

- Review the documentation files
- Check the server console for errors
- Verify Discord token and channel IDs
- Open a GitHub issue with details if needed

## License

MIT License
