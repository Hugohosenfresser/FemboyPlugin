# Report System Documentation

The FemboyPlugin includes a comprehensive report system with dual Discord integration, allowing players to report issues through both webhooks and a full Discord bot.

## 🎆 Features (Updated for v1.2.0)

- **Player Reports**: Report problematic players with detailed reasons
- **Issue Reports**: Report bugs, server issues, or general problems
- **Dual Discord Integration**: Both Discord webhooks AND full bot support
- **Enhanced Bot Logging**: Real-time Discord bot notifications for all reports
- **Multiple Channel Support**: Route reports to specific Discord channels
- **Cooldown System**: Prevent spam with configurable anti-abuse measures
- **Local Backup Storage**: Store reports locally in YAML format for redundancy
- **Tab Completion**: Smart tab completion for player names and report types
- **Rich Formatting**: Beautiful Discord embeds with timestamps and metadata
- **Admin Management**: In-game Discord bot controls for report monitoring

## 🚀 Setup Instructions

### 🤖 Option 1: Discord Bot Setup (Recommended)

**New in v1.2.0** - Full Discord bot integration with enhanced features:

1. **Create Discord Bot**:
   - Visit https://discord.com/developers/applications
   - Create a new application and add a bot
   - Copy the bot token

2. **Configure Plugin**:
   ```yaml
   discord_bot:
     enabled: true
     token: "YOUR_BOT_TOKEN_HERE"
     channels:
       reports: "YOUR_REPORTS_CHANNEL_ID"  # Main reports channel
       general: "YOUR_GENERAL_CHANNEL_ID"   # General notifications
     events:
       log_reports: true  # Enable report logging
   ```

3. **Get Channel IDs**:
   - Enable Developer Mode in Discord (User Settings > Advanced)
   - Right-click your reports channel > "Copy ID"
   - Add the channel ID to your config

4. **Test Setup**:
   ```
   /discord status      # Check bot connection
   /discord test reports # Test reports channel
   ```

**📄 Complete setup guide: [DISCORD_SETUP.md](DISCORD_SETUP.md)**

### 🔗 Option 2: Webhook Setup (Legacy)

For basic Discord reporting without a bot:

1. Go to your Discord server
2. Right-click on the channel where you want to receive reports
3. Select "Edit Channel" > "Integrations" > "Webhooks" > "New Webhook"
4. Copy the webhook URL
5. Edit `plugins/FemboyPlugin/config.yml` and replace `CHANGE_ME` with your webhook URL

### 2. Configuration Options

Edit `config.yml` to customize the report system:

```yaml
# Permissions system
permissions:
  givecustomitem: "femboyplugin.givecustomitem"
  givegui: "femboyplugin.givegui"
  report: "femboyplugin.report"
  commands: "femboyplugin.commands"
  admin: "femboyplugin.admin"
  no_permission: "&cYou don't have permission to use this command!"

discord:
  webhook_url: "YOUR_DISCORD_WEBHOOK_URL_HERE"
  bot_name: "Server Reports"
  bot_avatar: "https://i.imgur.com/4M34hi2.png"

reports:
  cooldown_seconds: 300      # 5 minutes between reports
  max_message_length: 500    # Maximum characters in a report
  log_to_console: true       # Log reports to server console
  store_locally: true        # Store reports in reports.yml

messages:
  report_sent: "&aYour report has been sent to the staff team!"
  cooldown: "&cYou must wait %time% seconds before sending another report."
  # ... (other customizable messages)
```

## Usage

### Player Reports
Report another player for misconduct:
```
/report player <playername> <reason>
```

Examples:
- `/report player BadPlayer123 griefing my house`
- `/report player Cheater using fly hacks`

### Issue Reports
Report server issues, bugs, or problems:
```
/report issue <description>
```

Examples:
- `/report issue The shop plugin is broken and not taking money`
- `/report issue Server lagging heavily in spawn area`

### Command Aliases
You can also use these aliases:
- `/reports player ...`
- `/ticket player ...`
- `/reports issue ...`
- `/ticket issue ...`

### Getting Help
Use the built-in help command to see all available commands:
```
/commands
/help
/pluginhelp
```

This displays all plugin commands with their descriptions, usage examples, and required permissions.

## 🤖 Discord Integration (Enhanced in v1.2.0)

### Discord Bot Features
**New in v1.2.0** - Full bot integration with advanced features:

- **Real-time Notifications**: Instant Discord alerts when reports are submitted
- **Dual Logging**: Reports sent to both webhook AND bot channels
- **Channel Routing**: Send reports to dedicated channels
- **Admin Management**: Control report logging with `/discord` commands
- **Status Monitoring**: Check bot health and report delivery

### Discord Message Format

Reports sent to Discord include rich embedded messages:

**Player Reports:**
- 🚨 Red embed with "Player Report" title
- Reporter name and timestamp
- Reported player name
- Server name and location
- Detailed reason/description
- Unique report ID for tracking
- Auto-generated footer with metadata

**Issue Reports:**
- 🐛 Yellow embed with "Issue Report" title
- Reporter name and timestamp
- Server name and environment details
- Comprehensive issue description
- Unique report ID for reference
- Priority indicators and categorization

### Bot vs Webhook Comparison

| Feature | Discord Bot | Webhook |
|---------|-------------|----------|
| Real-time connection | ✅ Always online | ❌ Request-based |
| Multiple channels | ✅ Full support | ❌ Single channel |
| Admin management | ✅ In-game commands | ❌ Manual setup |
| Status monitoring | ✅ Live status | ❌ No monitoring |
| Advanced formatting | ✅ Rich embeds | ✅ Basic embeds |
| Setup complexity | 🟡 Moderate | 🟢 Simple |

## File Storage

When `store_locally` is enabled, reports are saved in `plugins/FemboyPlugin/reports.yml`:

```yaml
reports:
  RPT-1234567890123:
    type: PLAYER
    reporter: PlayerName
    content: "Reported Player: BadPlayer | Reason: griefing"
    timestamp: "2024-01-15T10:30:45"
    status: OPEN
```

## Permissions

The report system uses a configurable permission system:

### Report Command Permissions:
- **Default**: `femboyplugin.report` (everyone by default)
- **Configurable**: Can be changed or disabled in config.yml

### Permission Configuration:
```yaml
permissions:
  report: "femboyplugin.report"     # Require this permission
  report: "custom.report.use"       # Use custom permission
  report: ""                        # Disable permission check (allow everyone)
  no_permission: "&cNo permission!" # Custom no-permission message
```

### Discord Bot Management 🤖
**New in v1.2.0** - Admin commands for managing Discord report logging:

```bash
# Check Discord bot status and report delivery
/discord status

# Test report channel connectivity
/discord test reports

# Toggle report logging on/off
/discord toggle reports

# View current event logging settings
/discord events

# Reload Discord configuration
/discord reload
```

**Required Permission**: `femboyplugin.discord` (op only)

### Permission Plugin Integration:
You can use any permission plugin (LuckPerms, PermissionsEx, etc.) to manage access:

```yaml
# Grant report permission to a group
lp group default permission set femboyplugin.report true

# Grant Discord admin permission
lp group admin permission set femboyplugin.discord true

# Deny permission to a specific player
lp user BadPlayer permission set femboyplugin.report false
```

## Error Handling

The system handles various error conditions:
- Invalid webhook URLs
- Network connectivity issues
- Rate limiting
- Malformed reports
- Cooldown violations

All errors are logged to the server console for debugging.

## 🔧 Troubleshooting

### Discord Bot Issues (v1.2.0)
**Bot not connecting:**
1. Use `/discord status` to check connection
2. Verify bot token in config.yml is correct
3. Check server console for authentication errors
4. Ensure bot has proper Discord permissions

**Reports not appearing in Discord:**
1. Test with `/discord test reports`
2. Check channel ID configuration
3. Verify event logging with `/discord events`
4. Ensure `log_reports: true` in config

**Bot commands not working:**
1. Verify you have `femboyplugin.discord` permission
2. Check if you're an op or have admin permissions
3. Reload configuration with `/discord reload`

### Legacy Webhook Issues
**Reports not appearing in Discord:**
1. Check that the webhook URL is correct in config.yml
2. Ensure the webhook URL starts with `https://discord.com/api/webhooks/`
3. Check server console for error messages
4. Verify Discord channel permissions

### General Issues
**Players getting cooldown messages:**
- Adjust `cooldown_seconds` in config.yml
- Check if players are actually waiting the required time
- Restart server to reset cooldowns if needed

**Reports too long:**
- Adjust `max_message_length` in config.yml
- Inform players about the character limit
- Consider using `/report issue` for longer descriptions

**Permission errors:**
- Verify permission configuration in config.yml
- Check your permission plugin settings
- Use empty string ("") to disable permission checks

## 📞 Support

### Quick Diagnostics
1. **Check Bot Status**: `/discord status`
2. **Test Channels**: `/discord test reports`
3. **View Configuration**: `/discord channels` and `/discord events`
4. **Console Logs**: Check for error messages

### Getting Help
If you encounter issues:
1. 📄 Review [DISCORD_SETUP.md](DISCORD_SETUP.md) for setup guidance
2. 🔍 Check server console for detailed error messages
3. ⚙️ Verify all configuration settings in config.yml
4. 🧪 Test Discord webhook URL manually (for webhook setup)
5. 📦 Ensure all plugin dependencies are properly loaded
6. 📝 Create a GitHub issue with error details and configuration
