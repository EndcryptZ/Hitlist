
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
    BOUNTY_CLAIM_OWN_BOUNTY("bounty.claim-own-bounty"),

    // Player Opt-In/Out Settings
    AUTO_OPT_OUT_ON_FIRST_JOIN("auto-opt-out-on-first-join"),
    OPT_IN_COOLDOWN_TIME("opt-in.cooldown.time"),
    OPT_IN_COOLDOWN_COUNT_ONLINE("opt-in.cooldown.count-only-while-online"),
    OPT_OUT_COOLDOWN_TIME("opt-out.cooldown.time"),
    OPT_OUT_COOLDOWN_COUNT_ONLINE("opt-out.cooldown.count-only-while-online"),

    // Head Drop Settings
    HEAD_DROP_ENABLED("head-drop.enabled"),
    HEAD_DROP_ITEM_NAME("head-drop.item.name"),
    HEAD_DROP_ITEM_LORE("head-drop.item.lore"),

    // Staff Settings
    STAFF_CAN_REMOVE_BOUNTIES("staff.can-remove-bounties"),
    STAFF_REFUND_ON_REMOVAL("staff.refund-on-removal"),

    // Leaderboards Settings
    LEADERBOARDS_REFRESH_INTERVAL("leaderboards.refresh-interval"),

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
    MESSAGE_BOUNTY_CLAIM("messages.bounty-claim"),

    // Error Messages
    ERROR_MESSAGE_MAX_AMOUNT("messages.error-max-amount"),
    ERROR_MESSAGE_MIN_AMOUNT("messages.error-min-amount"),
    ERROR_MESSAGE_SELF_BOUNTY("messages.error-self-bounty"),
    ERROR_MESSAGE_ACTIVE_BOUNTY("messages.error-target-already-have-bounty"),
    ERROR_MESSAGE_NOT_ENOUGH_MONEY("messages.error-not-enough-money"),
    ERROR_MESSAGE_NOT_ENOUGH_PLACED_MONEY("messages.error-not-enough-placed-money"),
    ERROR_MESSAGE_MAX_BOUNTIES_LIMIT("messages.error-max-bounties-limit"),

    // Permission Messages
    NO_PERMISSION_MESSAGE_BOUNTY_EDIT_OTHERS("messages.no-permission-bounty-edit-others"),

    // System Messages
    SYSTEM_MESSAGE_CONFIG_RELOAD("messages.system-config-reload"),

    // GUI Messages
    GUI_MESSAGE_MAX_AMOUNT("messages.gui-max-amount"),
    GUI_MESSAGE_MIN_AMOUNT("messages.gui-min-amount"),
    GUI_INVALID_NUMBER("messages.gui-invalid-number"),
    GUI_NOT_ENOUGH_MONEY("messages.gui-not-enough-money"),

    // GUI Settings
    GUI_MAIN_BOUNTY_TITLE("gui.main-bounty.title"),
    GUI_MAIN_BOUNTY_ROWS("gui.main-bounty.rows"),
    GUI_MAIN_BOUNTY_FILL("gui.main-bounty.fill"),
    GUI_MAIN_BOUNTY_FILL_TYPE("gui.main-bounty.fill-type"),

    GUI_MAIN_BOUNTY_ACTIVE_BOUNTIES_BUTTON_MATERIAL("gui.main-bounty.active-bounties-button.material"),
    GUI_MAIN_BOUNTY_ACTIVE_BOUNTIES_BUTTON_SLOT("gui.main-bounty.active-bounties-button.slot"),
    GUI_MAIN_BOUNTY_ACTIVE_BOUNTIES_BUTTON_NAME("gui.main-bounty.active-bounties-button.name"),
    GUI_MAIN_BOUNTY_ACTIVE_BOUNTIES_BUTTON_LORE("gui.main-bounty.active-bounties-button.lore"),

    GUI_MAIN_BOUNTY_PLACE_BOUNTY_BUTTON_MATERIAL("gui.main-bounty.place-bounty-button.material"),
    GUI_MAIN_BOUNTY_PLACE_BOUNTY_BUTTON_SLOT("gui.main-bounty.place-bounty-button.slot"),
    GUI_MAIN_BOUNTY_PLACE_BOUNTY_BUTTON_NAME("gui.main-bounty.place-bounty-button.name"),
    GUI_MAIN_BOUNTY_PLACE_BOUNTY_BUTTON_LORE("gui.main-bounty.place-bounty-button.lore"),

    // Place Bounty GUI Settings
    GUI_PLACE_BOUNTY_TITLE("gui.place-bounty.title"),
    GUI_PLACE_BOUNTY_ROWS("gui.place-bounty.rows"),

    GUI_PLACE_BOUNTY_PLAYER_BUTTON_NAME("gui.place-bounty.player-button.name"),
    GUI_PLACE_BOUNTY_PLAYER_BUTTON_LORE("gui.place-bounty.player-button.lore"),

    GUI_PLACE_BOUNTY_SORT_BUTTON_ENABLED("gui.place-bounty.sort-button.enabled"),
    GUI_PLACE_BOUNTY_SORT_BUTTON_MATERIAL("gui.place-bounty.sort-button.material"),
    GUI_PLACE_BOUNTY_SORT_BUTTON_NAME("gui.place-bounty.sort-button.name"),
    GUI_PLACE_BOUNTY_SORT_BUTTON_LORE("gui.place-bounty.sort-button.lore"),

    GUI_PLACE_BOUNTY_SEARCH_BUTTON_MATERIAL("gui.place-bounty.search-button.material"),
    GUI_PLACE_BOUNTY_SEARCH_BUTTON_NAME("gui.place-bounty.search-button.name"),
    GUI_PLACE_BOUNTY_SEARCH_BUTTON_LORE("gui.place-bounty.search-button.lore"),

    // Active Bounties GUI Settings
    GUI_ACTIVE_BOUNTIES_TITLE("gui.active-bounties.title"),
    GUI_ACTIVE_BOUNTIES_ROWS("gui.active-bounties.rows"),

    GUI_ACTIVE_BOUNTIES_BOUNTY_BUTTON_NAME("gui.active-bounties.bounty-button.name"),
    GUI_ACTIVE_BOUNTIES_BOUNTY_BUTTON_LORE("gui.active-bounties.bounty-button.lore"),

    GUI_ACTIVE_BOUNTIES_SORT_BUTTON_ENABLED("gui.active-bounties.sort-button.enabled"),
    GUI_ACTIVE_BOUNTIES_SORT_BUTTON_MATERIAL("gui.active-bounties.sort-button.material"),
    GUI_ACTIVE_BOUNTIES_SORT_BUTTON_NAME("gui.active-bounties.sort-button.name"),
    GUI_ACTIVE_BOUNTIES_SORT_BUTTON_LORE("gui.active-bounties.sort-button.lore"),

    GUI_ACTIVE_BOUNTIES_SEARCH_BUTTON_MATERIAL("gui.active-bounties.search-button.material"),
    GUI_ACTIVE_BOUNTIES_SEARCH_BUTTON_NAME("gui.active-bounties.search-button.name"),
    GUI_ACTIVE_BOUNTIES_SEARCH_BUTTON_LORE("gui.active-bounties.search-button.lore");


    private final String path;

    ConfigEnum(String path) {
        this.path = path;
    }

    public boolean isMessage() {
        return path.startsWith("messages.");
    }
}