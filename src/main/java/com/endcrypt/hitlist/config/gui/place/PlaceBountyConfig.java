package com.endcrypt.hitlist.config.gui.place;

import com.endcrypt.hitlist.config.ConfigEnum;
import com.endcrypt.hitlist.config.gui.place.buttons.PlayerButtonConfig;
import com.endcrypt.hitlist.config.gui.place.buttons.SearchButtonConfig;
import com.endcrypt.hitlist.config.gui.place.buttons.SortButtonConfig;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;

@Getter
public class PlaceBountyConfig {

    private final String title;
    private final int rows;
    private final PlayerButtonConfig playerButtonConfig;
    private final SortButtonConfig sortButtonConfig;
    private final SearchButtonConfig searchButtonConfig;
    public PlaceBountyConfig(FileConfiguration config) {
        this.title = config.getString(ConfigEnum.GUI_PLACE_BOUNTY_TITLE.getPath());
        this.rows = config.getInt(ConfigEnum.GUI_PLACE_BOUNTY_ROWS.getPath());
        this.playerButtonConfig = new PlayerButtonConfig(config);
        this.sortButtonConfig = new SortButtonConfig(config);
        this.searchButtonConfig = new SearchButtonConfig(config);
    }
}
