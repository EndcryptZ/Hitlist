package com.endcrypt.hitlist.config;

import com.endcrypt.hitlist.bounty.BountyData;
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

    public String getBountyLower(String target, double amount, double newAmount) {
        return format(getString(ConfigEnum.MESSAGE_BOUNTY_LOWER),
                "%target%", target,
                "%amount%", amount,
                "%new_amount%", newAmount);
    }

    public String getErrorMaxAmount() {
        return format(getString(ConfigEnum.ERROR_MESSAGE_MAX_AMOUNT));
    }

    public String getErrorMinAmount() {
        return format(getString(ConfigEnum.ERROR_MESSAGE_MIN_AMOUNT));
    }

    public String getErrorSelfBounty() {
        return format(getString(ConfigEnum.ERROR_MESSAGE_SELF_BOUNTY));
    }

    public String getErrorActiveBounty(String target) {
        return format(getString(ConfigEnum.ERROR_MESSAGE_ACTIVE_BOUNTY),
                "%target%", target);
    }

    public String getErrorNotEnoughMoney(double requiredAmount) {
        return format(getString(ConfigEnum.ERROR_MESSAGE_NOT_ENOUGH_MONEY),
                "%required_money%", requiredAmount);
    }

    public String getErrorNotEnoughPlacedMoney(double placedAmount) {
        return format(getString(ConfigEnum.ERROR_MESSAGE_NOT_ENOUGH_PLACED_MONEY),
                "%placed_money%", placedAmount);
    }

    public String getErrorMaxBountiesLimit(int limit) {
        return format(getString(ConfigEnum.ERROR_MESSAGE_MAX_BOUNTIES_LIMIT),
                "%limit%", limit);
    }

    public String getNoPermissionBountyEditOthers() {
        return format(getString(ConfigEnum.NO_PERMISSION_BOUNTY_EDIT_OTHERS));
    }

    public String getSystemConfigReload() {
        return format(getString(ConfigEnum.SYSTEM_MESSAGE_CONFIG_RELOAD));
    }
}