# FemboyPlugin v1.1.0 Release Notes

## New Features Update

This release adds a comprehensive help system and configurable permissions to make the plugin more user-friendly and administrator-friendly.

## What's New

### Commands Help System
- **New Command**: `/commands` - Display all available plugin commands
- **Command Aliases**: `/help`, `/pluginhelp`
- **Rich Information**: Shows command descriptions, usage examples, and required permissions
- **Beautiful Formatting**: Color-coded output with organized sections
- **Permission Display**: See exactly what permissions are needed for each command

### Configurable Permission System
- **Full Permission Control**: All commands now support configurable permissions
- **Flexible Configuration**: Enable or disable permission checks per command
- **Custom Messages**: Customize the "no permission" message
- **Permission Integration**: Works with any permission plugin (LuckPerms, PermissionsEx, etc.)
- **Sensible Defaults**: Op-only for admin commands, everyone for basic features

## New Permissions

- `femboyplugin.givecustomitem` - Use /givecustomitem command (default: op only)
- `femboyplugin.givegui` - Use /givegui command (default: everyone)
- `femboyplugin.report` - Use /report commands (default: everyone)
- `femboyplugin.commands` - Use /commands help (default: everyone)
- `femboyplugin.admin` - Admin permissions (default: op only)
- `femboyplugin.*` - All plugin permissions

## Configuration Updates

The config.yml now includes a permissions section:

```yaml
# Permissions System
permissions:
  # Command permissions (set to "" to disable permission check)
  givecustomitem: "femboyplugin.givecustomitem"
  givegui: "femboyplugin.givegui"
  report: "femboyplugin.report"
  commands: "femboyplugin.commands"
  admin: "femboyplugin.admin"
  no_permission: "&cYou don't have permission to use this command!"
```

## Permission Examples

### Allow everyone to use reports (disable permission check):
```yaml
permissions:
  report: ""
```

### Use custom permission for giving items:
```yaml
permissions:
  givecustomitem: "myserver.admin.items"
```

### Custom permission denial message:
```yaml
permissions:
  no_permission: "&c&lACCESS DENIED! &7Contact staff for help."
```

## Compatibility

- **Backwards Compatible**: Existing installations will work without changes
- **Permission Plugins**: Compatible with LuckPerms, PermissionsEx, GroupManager, etc.
- **Default Behavior**: If no permissions are configured, uses sensible defaults

## Migration from v1.0.0

1. **Automatic**: The plugin will automatically add the new permissions section to your config
2. **Optional**: Customize permissions in config.yml if desired
3. **No Breaking Changes**: All existing functionality remains the same

## Commands Summary

- `/givecustomitem <item> [amount]` - Give custom items (permission: `femboyplugin.givecustomitem`)
- `/givegui` - Open items GUI (permission: `femboyplugin.givegui`)
- `/report player <name> <reason>` - Report a player (permission: `femboyplugin.report`)
- `/report issue <description>` - Report an issue (permission: `femboyplugin.report`)
- `/commands` - Show help menu (permission: `femboyplugin.commands`)

## Installation

### New Installation:
1. Download `FemboyPlugin-v1.1.0.jar`
2. Place in your server's `plugins/` folder
3. Start server to generate config files
4. Configure Discord webhook if desired
5. Customize permissions if needed

### Upgrade from v1.0.0:
1. Replace old JAR with `FemboyPlugin-v1.1.0.jar`
2. Restart server
3. New permissions section will be added to config automatically

## Technical Details

- **Build**: Maven 3.9+
- **Size**: ~35KB
- **Java**: 17+
- **Minecraft**: 1.21+ (Paper/Spigot/Bukkit)
- **Dependencies**: OkHttp (included)

## Documentation

- **Setup Guide**: README.md
- **Report System**: REPORT_SYSTEM.md
- **Full Changelog**: CHANGELOG.md

---

**Download**: `FemboyPlugin-v1.1.0.jar`  
**GitHub**: [Release Page](https://github.com/Hugohosenfresser/FemboyPlugin/releases/tag/v1.1.0)