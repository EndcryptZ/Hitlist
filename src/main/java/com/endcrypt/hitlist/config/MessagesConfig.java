package com.endcrypt.hitlist.config;

import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.ChatColor;

@Getter
public class MessagesConfig {
    private final FileConfiguration config;
    private final String prefix;

    public MessagesConfig(FileConfiguration config) {
        this.config = config;
        this.prefix = colorize(getString(ConfigEnum.MESSAGE_PREFIX));
    }

    private String getString(ConfigEnum path) {
        if (!path.isMessage()) {
            throw new IllegalArgumentException("Tried to access non-message config: " + path);
        }
        return config.getString(path.getPath(), "");
    }

    private String colorize(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    private String format(String message, Object... args) {
        String formatted = prefix + message;
        if (args != null && args.length > 0) {
            for (int i = 0; i < args.length; i += 2) {
                if (i + 1 < args.length) {
                    formatted = formatted.replace(String.valueOf(args[i]), String.valueOf(args[i + 1]));
                }
            }
        }
        return colorize(formatted);
    }

    public String getBountyPlaced(String target, String amount) {
        return format(getString(ConfigEnum.MESSAGE_BOUNTY_PLACED),
                "%target%", target,
                "%amount%", amount);
    }

    public String getBountyClaimed(String killer, String target) {
        return format(getString(ConfigEnum.MESSAGE_BOUNTY_CLAIMED),
                "%killer%", killer,
                "%target%", target);
    }

    public String getOptInSuccess() {
        return format(getString(ConfigEnum.MESSAGE_OPT_IN_SUCCESS));
    }

    public String getOptOutSuccess() {
        return format(getString(ConfigEnum.MESSAGE_OPT_OUT_SUCCESS));
    }

    public String getCooldownActive(String time) {
        return format(getString(ConfigEnum.MESSAGE_COOLDOWN_ACTIVE),
                "%time%", time);
    }

    public String getBountyExpired(String target) {
        return format(getString(ConfigEnum.MESSAGE_BOUNTY_EXPIRED),
                "%target%", target);
    }

    public String getSelfBounty() {
        return format(getString(ConfigEnum.MESSAGE_SELF_BOUNTY));
    }
}