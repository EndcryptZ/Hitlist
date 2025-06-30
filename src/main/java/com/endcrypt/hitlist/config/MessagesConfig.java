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

    public String getBountyPlace(String target, String amount) {
        return format(getString(ConfigEnum.MESSAGE_BOUNTY_PLACE),
                "%target%", target,
                "%amount%", amount);
    }

    public String getBountyPlaceStack(String target, String amount) {
        return format(getString(ConfigEnum.MESSAGE_BOUNTY_PLACE_STACK),
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

    public String getBountyRemove(String target) {
        return format(getString(ConfigEnum.MESSAGE_BOUNTY_REMOVE),
                "%target%", target);
    }

    public String getErrorSelfBounty() {
        return format(getString(ConfigEnum.ERROR_MESSAGE_SELF_BOUNTY));
    }

    public String getErrorActiveBounty(String target) {
        return format(getString(ConfigEnum.ERROR_MESSAGE_ACTIVE_BOUNTY),
                "%target%", target);
    }

    public String getErrorNotEnoughMoney() {
        return format(getString(ConfigEnum.ERROR_MESSAGE_NOT_ENOUGH_MONEY));
    }

    public String getNoPermissionBountyCancelOthers() {
        return format(getString(ConfigEnum.NO_PERMISSION_BOUNTY_REMOVE_OTHERS));
    }

    public String getSystemConfigReload() {
        return format(getString(ConfigEnum.SYSTEM_MESSAGE_CONFIG_RELOAD));
    }
}