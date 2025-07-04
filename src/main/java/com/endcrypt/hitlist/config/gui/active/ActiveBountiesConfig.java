package com.endcrypt.hitlist.config.gui.active;

import com.endcrypt.hitlist.config.ConfigEnum;
import com.endcrypt.hitlist.config.gui.active.buttons.BountyButtonConfig;
import com.endcrypt.hitlist.config.gui.active.buttons.SortButtonConfig;
import com.endcrypt.hitlist.config.gui.place.buttons.SearchButtonConfig;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;

@Getter
public class ActiveBountiesConfig {

    private final String title;
    private final int rows;
    private final BountyButtonConfig bountyButtonConfig;
    private final SearchButtonConfig searchButtonConfig;
    private final SortButtonConfig sortButtonConfig;
    public ActiveBountiesConfig(FileConfiguration config) {
        this.title = config.getString(ConfigEnum.GUI_ACTIVE_BOUNTIES_TITLE.getPath());
        this.rows = config.getInt(ConfigEnum.GUI_ACTIVE_BOUNTIES_ROWS.getPath());
        this.bountyButtonConfig = new BountyButtonConfig(config);
        this.searchButtonConfig = new SearchButtonConfig(config);
        this.sortButtonConfig = new SortButtonConfig(config);
    }
}
