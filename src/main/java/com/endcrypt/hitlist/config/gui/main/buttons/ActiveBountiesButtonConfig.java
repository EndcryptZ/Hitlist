package com.endcrypt.hitlist.config.gui.main.buttons;

import com.endcrypt.hitlist.config.ConfigEnum;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

@Getter
public class ActiveBountiesButtonConfig {

    private final Material material;
    private final int slot;
    private final String name;
    private final String[] lore;
    public ActiveBountiesButtonConfig(FileConfiguration config) {
        this.material = Material.valueOf(config.getString(ConfigEnum.GUI_MAIN_BOUNTY_ACTIVE_BOUNTIES_BUTTON_NAME.getPath()));
        this.slot = config.getInt((ConfigEnum.GUI_MAIN_BOUNTY_ACTIVE_BOUNTIES_BUTTON_SLOT.getPath()));
        this.name = config.getString(ConfigEnum.GUI_MAIN_BOUNTY_ACTIVE_BOUNTIES_BUTTON_NAME.getPath());
        this.lore = config.getStringList(ConfigEnum.GUI_MAIN_BOUNTY_ACTIVE_BOUNTIES_BUTTON_LORE.getPath()).toArray(new String[0]);
    }
}
