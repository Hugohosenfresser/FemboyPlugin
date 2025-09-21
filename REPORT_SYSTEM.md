# Report System Documentation

The FemboyPlugin now includes a comprehensive report system that allows players to report other players or server issues directly to a Discord channel.

## Features

- **Player Reports**: Report problematic players with reasons
- **Issue Reports**: Report bugs, server issues, or general problems
- **Discord Integration**: Send reports directly to a Discord channel via webhooks
- **Cooldown System**: Prevent spam with configurable cooldowns
- **Local Storage**: Store reports locally in YAML format
- **Tab Completion**: Smart tab completion for player names and report types
- **Configurable Messages**: Customize all messages in config.yml

## Setup Instructions

### 1. Configure Discord Webhook

1. Go to your Discord server
2. Right-click on the channel where you want to receive reports
3. Select "Edit Channel" > "Integrations" > "Webhooks" > "New Webhook"
4. Copy the webhook URL
5. Edit `plugins/FemboyPlugin/config.yml` and replace `CHANGE_ME` with your webhook URL

### 2. Configuration Options

Edit `config.yml` to customize the report system:

```yaml
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

## Discord Integration

Reports sent to Discord include:

**Player Reports:**
- 🚨 Red embed with "Player Report" title
- Reporter name
- Reported player name
- Server name
- Reason/description
- Unique report ID
- Timestamp

**Issue Reports:**
- 🐛 Yellow embed with "Issue Report" title
- Reporter name
- Server name
- Issue description
- Unique report ID
- Timestamp

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

Currently, the `/report` command is available to all players. You can restrict it using your permission plugin by denying the `femboyplugin.report` permission if needed.

## Error Handling

The system handles various error conditions:
- Invalid webhook URLs
- Network connectivity issues
- Rate limiting
- Malformed reports
- Cooldown violations

All errors are logged to the server console for debugging.

## Troubleshooting

### Reports not appearing in Discord
1. Check that the webhook URL is correct in config.yml
2. Ensure the webhook URL starts with `https://discord.com/api/webhooks/`
3. Check server console for error messages
4. Verify Discord channel permissions

### Players getting cooldown messages
- Adjust `cooldown_seconds` in config.yml
- Check if players are actually waiting the required time
- Restart server to reset cooldowns if needed

### Reports too long
- Adjust `max_message_length` in config.yml
- Inform players about the character limit

## Support

If you encounter issues:
1. Check server console for error messages
2. Verify all configuration settings
3. Test the Discord webhook URL manually
4. Ensure all plugin dependencies are installed