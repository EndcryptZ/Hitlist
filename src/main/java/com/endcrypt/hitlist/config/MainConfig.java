
package com.endcrypt.hitlist.config;

import com.endcrypt.hitlist.utils.TimeUtils;
import org.bukkit.configuration.file.FileConfiguration;

public class MainConfig {
    private final FileConfiguration config;

    public MainConfig(FileConfiguration config) {
        this.config = config;
        validateConfiguration();
    }

    private void validateConfiguration() {
        // Validate time formats
        validateTimeFormat(getExpirationTime(), "Expiration time");
        validateTimeFormat(getOptInCooldownTime(), "Opt-in cooldown time");
        validateTimeFormat(getOptOutCooldownTime(), "Opt-out cooldown time");
        validateTimeFormat(getClaimCooldownTime(), "Claim cooldown time");

        // Validate numerical values
        if (getMinBountyAmount() < 0) {
            throw new IllegalStateException("Minimum bounty amount cannot be negative");
        }
        if (getMaxBountyAmount() < getMinBountyAmount()) {
            throw new IllegalStateException("Maximum bounty amount cannot be less than minimum amount");
        }
    }

    private void validateTimeFormat(String time, String fieldName) {
        try {
            TimeUtils.parseTime(time);
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException("Invalid " + fieldName + ": " + time);
        }
    }

    // === Bounty Rules ===
    public double getMinBountyAmount() {
        return config.getDouble(ConfigEnum.BOUNTY_MIN_AMOUNT.getPath());
    }

    public double getMaxBountyAmount() {
        return config.getDouble(ConfigEnum.BOUNTY_MAX_AMOUNT.getPath());
    }

    public boolean isPlacementFeeEnabled() {
        return config.getBoolean(ConfigEnum.BOUNTY_PLACEMENT_FEE_ENABLED.getPath());
    }

    public double getPlacementFeeAmount() {
        return config.getDouble(ConfigEnum.BOUNTY_PLACEMENT_FEE_AMOUNT.getPath());
    }

    public boolean isStackingEnabled() {
        return config.getBoolean(ConfigEnum.BOUNTY_STACKING_ENABLED.getPath());
    }

    public boolean isExpirationEnabled() {
        return config.getBoolean(ConfigEnum.BOUNTY_EXPIRATION_ENABLED.getPath());
    }

    public String getExpirationTime() {
        return config.getString(ConfigEnum.BOUNTY_EXPIRATION_TIME.getPath());
    }

    public long getExpirationTimeMillis() {
        return TimeUtils.parseTime(getExpirationTime());
    }

    public boolean isAnonymousEnabled() {
        return config.getBoolean(ConfigEnum.BOUNTY_ANONYMOUS_ENABLED.getPath());
    }

    public boolean isClaimOwnBountyEnabled() { return config.getBoolean(ConfigEnum.BOUNTY_CLAIM_OWN_BOUNTY.getPath()); }

    // === Player Opt-In Settings ===
    public String getOptInCooldownTime() {
        return config.getString(ConfigEnum.OPT_IN_COOLDOWN_TIME.getPath());
    }

    public long getOptInCooldownTimeMillis() {
        return TimeUtils.parseTime(getOptInCooldownTime());
    }

    public boolean isOptInCooldownCountOnlineOnly() {
        return config.getBoolean(ConfigEnum.OPT_IN_COOLDOWN_COUNT_ONLINE.getPath());
    }

    // === Player Opt-Out Settings ===

    public String getOptOutCooldownTime() {
        return config.getString(ConfigEnum.OPT_OUT_COOLDOWN_TIME.getPath());
    }

    public long getOptOutCooldownTimeMillis() {
        return TimeUtils.parseTime(getOptOutCooldownTime());
    }

    public boolean isOptOutCooldownCountOnlineOnly() {
        return config.getBoolean(ConfigEnum.OPT_OUT_COOLDOWN_COUNT_ONLINE.getPath());
    }

    // === Claim Settings ===
    public boolean isClaimCooldownEnabled() {
        return config.getBoolean(ConfigEnum.CLAIM_COOLDOWN_ENABLED.getPath());
    }

    public String getClaimCooldownTime() {
        return config.getString(ConfigEnum.CLAIM_COOLDOWN_TIME.getPath());
    }

    public long getClaimCooldownTimeMillis() {
        return TimeUtils.parseTime(getClaimCooldownTime());
    }

    // === Head Drop Settings ===
    public boolean isHeadDropEnabled() {
        return config.getBoolean(ConfigEnum.HEAD_DROP_ENABLED.getPath());
    }

    // === Staff Settings ===
    public boolean canStaffRemoveBounties() {
        return config.getBoolean(ConfigEnum.STAFF_CAN_REMOVE_BOUNTIES.getPath());
    }

    public boolean isRefundOnRemovalEnabled() {
        return config.getBoolean(ConfigEnum.STAFF_REFUND_ON_REMOVAL.getPath());
    }

    // === Economy Settings ===
    public String getEconomyPlugin() {
        return config.getString(ConfigEnum.ECONOMY_PLUGIN.getPath());
    }

    public boolean isEconomyIntegrationEnabled() {
        return config.getBoolean(ConfigEnum.ECONOMY_INTEGRATION_ENABLED.getPath());
    }

}