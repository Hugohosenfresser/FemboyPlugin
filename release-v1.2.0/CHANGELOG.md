# FemboyPlugin Changelog

## Version 1.2.0 (Latest Release) - Discord Bot Integration

### Major New Features
- **Full Discord Bot Integration**: Complete JDA-powered Discord bot with real-time connection
- **Comprehensive Event Logging**: Track all server activities with configurable event types
- **Multiple Channel Support**: Route different event types to different Discord channels
- **Advanced Admin Management**: In-game commands to control Discord bot functionality
- **Privacy-Friendly Defaults**: Sensible defaults with chat logging disabled by default

### Event Logging System
- **Player Events**: Join/leave notifications with IP addresses and player counts
- **Command Logging**: Track command usage (plugin commands or all server commands)
- **Admin Actions**: Log item giving, permissions, and administrative activities
- **Death Events**: Player deaths with location and cause information
- **World Changes**: Dimension switching and world teleportation
- **Chat Monitoring**: Optional player chat logging (disabled by default for privacy)
- **Achievement Tracking**: Player advancement/achievement unlocks (optional)
- **Server Alerts**: Performance monitoring and system notifications

### New Admin Commands
- `/discord status` - Check Discord bot connection and status information
- `/discord reload` - Reload Discord bot configuration without server restart
- `/discord test <channel>` - Test Discord bot connection to specific channels
- `/discord toggle <event>` - Toggle specific event logging types on/off
- `/discord channels` - List all configured Discord channel mappings
- `/discord events` - View current event logging configuration status
- Command aliases: `/discordbot`

### Configuration Enhancements
- **Discord Bot Settings**: Complete bot configuration section in config.yml
- **Channel Mapping**: Map different event types to different Discord channels
- **Granular Event Controls**: Enable/disable individual event types
- **Performance Settings**: Configurable logging options for optimal server performance
- **Dual Discord Support**: Both webhook and bot integration supported simultaneously

### New Permissions
- `femboyplugin.discord` - Access to Discord bot admin commands (default: op only)
- Enhanced permission system integration for Discord management

### Technical Improvements
- **Asynchronous Operations**: All Discord operations run asynchronously to prevent server lag
- **Enhanced Error Handling**: Robust error handling with detailed logging
- **Auto-Dependency Management**: JDA and OkHttp dependencies automatically downloaded
- **Graceful Degradation**: Plugin functions normally even if Discord integration fails
- **Memory Optimization**: Efficient event processing with minimal memory footprint

### Documentation Updates
- **DISCORD_SETUP.md**: Complete Discord bot setup guide
- **Updated README.md**: Comprehensive documentation with Discord integration
- **Enhanced Configuration Examples**: Detailed config.yml examples with comments

### Compatibility
- **Backward Compatible**: All existing webhook functionality preserved
- **Configuration Migration**: Existing configurations work without changes
- **Permission System**: Compatible with all major permission plugins

---

## Version 1.1.0 (Released)

### New Features
- **`/commands` Help System**: Added comprehensive help command showing all available commands
  - Shows command descriptions, usage examples, and required permissions
  - Aliases: `/help`, `/pluginhelp`
  - Beautiful formatted output with color coding

- **Configurable Permission System**: Complete permission system overhaul
  - All commands now support configurable permissions
  - Permissions can be disabled by setting to empty string in config
  - Custom "no permission" messages
  - Integration with any permission plugin (LuckPerms, PermissionsEx, etc.)

### Commands Added
- `/commands` - Display all available plugin commands with help information

### Permissions Added
- `femboyplugin.givecustomitem` - Use /givecustomitem command (default: op only)
- `femboyplugin.givegui` - Use /givegui command (default: everyone)
- `femboyplugin.report` - Use /report commands (default: everyone)
- `femboyplugin.commands` - Use /commands help (default: everyone)
- `femboyplugin.admin` - Admin permissions (default: op only)
- `femboyplugin.*` - All plugin permissions

### Configuration Changes
- Added `permissions` section to config.yml
- Each command permission is fully configurable
- Customizable permission denial messages

### Documentation Updates
- Updated README.md with permissions and commands information
- Updated REPORT_SYSTEM.md with permission configuration examples
- Added detailed permission management instructions

## Version 1.0.0 (Released)

### Initial Release
- Custom Items System (Cock Slicer, Bandage, Medkit)
- Discord Report System with webhooks
- GUI for item management
- Local report storage
- Tab completion
- Comprehensive configuration system