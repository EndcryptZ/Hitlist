package com.endcrypt.hitlist.config.gui.active.buttons;

import com.endcrypt.hitlist.HitlistPlugin;
import com.endcrypt.hitlist.bounty.BountyData;
import com.endcrypt.hitlist.config.ConfigEnum;
import com.endcrypt.hitlist.utils.DateUtils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

public class BountyButtonConfig {

    private final HitlistPlugin plugin = HitlistPlugin.instance;

    private final String name;
    private final String[] lore;
    public BountyButtonConfig(FileConfiguration config) {
        this.name = config.getString(ConfigEnum.GUI_ACTIVE_BOUNTIES_BOUNTY_BUTTON_NAME.getPath());
        this.lore = config.getStringList(ConfigEnum.GUI_ACTIVE_BOUNTIES_BOUNTY_BUTTON_LORE.getPath()).toArray(new String[0]);
    }

    public String getName(String playerName) {
        return name.replace("%target%", playerName);
    }

    public String[] getLore(String playerName, BountyData bountyData) {
        String[] processed = new String[lore.length];
        for (int i = 0; i < lore.length; i++) {
            String placeholder = bountyData != null ? String.valueOf(bountyData.getAmount()) : "0";
            String placerName = bountyData != null ? Bukkit.getOfflinePlayer(bountyData.getPlacerId()).getName() : "Unknown";
            String expiry = DateUtils.getRelativeTime(bountyData.getPlacementTime() + plugin.getConfigManager().getMain().getExpirationTimeMillis());
            processed[i] = lore[i]
                    .replace("%target%", playerName)
                    .replace("%placer%", placerName)
                    .replace("%amount%", placeholder)
                    .replace("%expiry%", expiry);
        }
        return processed;
    }

}
