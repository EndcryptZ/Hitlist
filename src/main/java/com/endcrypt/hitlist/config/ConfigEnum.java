
package com.endcrypt.hitlist.config;

import lombok.Getter;

@Getter
public enum ConfigEnum {
    // Bounty Rules
    BOUNTY_MIN_AMOUNT("bounty.min-amount"),
    BOUNTY_MAX_AMOUNT("bounty.max-amount"),
    BOUNTY_PLACEMENT_FEE_ENABLED("bounty.placement-fee.enabled"),
    BOUNTY_PLACEMENT_FEE_AMOUNT("bounty.placement-fee.amount"),
    BOUNTY_STACKING_ENABLED("bounty.stacking.enabled"),
    BOUNTY_EXPIRATION_ENABLED("bounty.expiration.enabled"),
    BOUNTY_EXPIRATION_TIME("bounty.expiration.time"),
    BOUNTY_ANONYMOUS_ENABLED("bounty.anonymous.enabled"),

    // Player Opt-In/Out Settings
    AUTO_OPT_OUT_ON_FIRST_JOIN("auto-opt-out-on-first-join"),
    OPT_IN_COOLDOWN_TIME("opt-in.cooldown.time"),
    OPT_IN_COOLDOWN_COUNT_ONLINE("opt-in.cooldown.count-only-while-online"),
    OPT_OUT_COOLDOWN_TIME("opt-out.cooldown.time"),
    OPT_OUT_COOLDOWN_COUNT_ONLINE("opt-out.cooldown.count-only-while-online"),

    // Claim Settings
    CLAIM_COOLDOWN_ENABLED("claim.cooldown.enabled"),
    CLAIM_COOLDOWN_TIME("claim.cooldown.time"),

    // Head Drop Settings
    HEAD_DROP_ENABLED("head-drop.enabled"),

    // Staff Settings
    STAFF_CAN_REMOVE_BOUNTIES("staff.can-remove-bounties"),
    STAFF_REFUND_ON_REMOVAL("staff.refund-on-removal"),

    // Economy Settings
    ECONOMY_PLUGIN("economy.plugin"),
    ECONOMY_INTEGRATION_ENABLED("economy.integration-enabled"),

    // Messages
    MESSAGE_PREFIX("messages.prefix"),
    MESSAGE_BOUNTY_PLACE("messages.bounty-place"),
    MESSAGE_BOUNTY_PLACE_STACK("messages.bounty-place-stack"),
    MESSAGE_BOUNTY_CLAIMED("messages.bounty-claimed"),
    MESSAGE_OPT_IN_SUCCESS("messages.opt-in-success"),
    MESSAGE_OPT_OUT_SUCCESS("messages.opt-out-success"),
    MESSAGE_COOLDOWN_ACTIVE("messages.cooldown-active"),
    MESSAGE_BOUNTY_EXPIRED("messages.bounty-expired"),
    MESSAGE_BOUNTY_REMOVE("messages.bounty-remove"),
    MESSAGE_BOUNTY_LOWER("messages.bounty-lower"),

    // Error Messages
    ERROR_MESSAGE_MAX_AMOUNT("messages.error-max-amount"),
    ERROR_MESSAGE_MIN_AMOUNT("messages.error-min-amount"),
    ERROR_MESSAGE_SELF_BOUNTY("messages.error-self-bounty"),
    ERROR_MESSAGE_ACTIVE_BOUNTY("messages.error-target-already-have-bounty"),
    ERROR_MESSAGE_NOT_ENOUGH_MONEY("messages.error-not-enough-money"),
    ERROR_MESSAGE_NOT_ENOUGH_PLACED_MONEY("messages.error-not-enough-placed-money"),
    ERROR_MESSAGE_MAX_BOUNTIES_LIMIT("messages.error-max-bounties-limit"),

    // Permission Messages
    NO_PERMISSION_BOUNTY_EDIT_OTHERS("messages.no-permission-bounty-edit-others"),

    // System Messages
    SYSTEM_MESSAGE_CONFIG_RELOAD("messages.system-config-reload");

    private final String path;

    ConfigEnum(String path) {
        this.path = path;
    }

    public boolean isMessage() {
        return path.startsWith("messages.");
    }
}