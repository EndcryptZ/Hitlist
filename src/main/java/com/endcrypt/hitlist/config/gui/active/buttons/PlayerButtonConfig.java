package com.endcrypt.hitlist.config.gui.active.buttons;

import com.endcrypt.hitlist.bounty.BountyData;
import com.endcrypt.hitlist.config.ConfigEnum;
import org.bukkit.configuration.file.FileConfiguration;

public class PlayerButtonConfig {

    private final String name;
    private final String[] lore;
    public PlayerButtonConfig(FileConfiguration config) {
        this.name = config.getString(ConfigEnum.GUI_ACTIVE_BOUNTIES_PLAYER_BUTTON_NAME.getPath());
        this.lore = config.getStringList(ConfigEnum.GUI_ACTIVE_BOUNTIES_PLAYER_BUTTON_LORE.getPath()).toArray(new String[0]);
    }

    public String getName(String playerName) {
        return name.replace("%player%", playerName);
    }

    public String[] getLore(String playerName, BountyData bountyData) {
        String[] processed = new String[lore.length];
        for (int i = 0; i < lore.length; i++) {
            String placeholder = bountyData != null ? String.valueOf(bountyData.getAmount()) : "0";
            processed[i] = lore[i]
                    .replace("%player%", playerName)
                    .replace("%bounty%", placeholder);
        }
        return processed;
    }

}
