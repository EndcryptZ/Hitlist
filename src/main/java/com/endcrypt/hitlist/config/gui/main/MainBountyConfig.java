package com.endcrypt.hitlist.config.gui.main;

import com.endcrypt.hitlist.config.ConfigEnum;
import com.endcrypt.hitlist.config.gui.main.buttons.BountyListButtonConfig;
import com.endcrypt.hitlist.config.gui.main.buttons.PlaceBountyButtonConfig;
import com.endcrypt.hitlist.gui.FillType;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

@Getter
public class MainBountyConfig {

    private final String title;
    private final int rows;
    private final Material fill;
    private final FillType fillType;
    private final BountyListButtonConfig bountyListButtonConfig;
    private final PlaceBountyButtonConfig placeBountyButtonConfig;
    public MainBountyConfig(FileConfiguration config) {
        this.title = config.getString(ConfigEnum.GUI_MAIN_BOUNTY_TITLE.getPath());
        this.rows = config.getInt(ConfigEnum.GUI_MAIN_BOUNTY_ROWS.getPath());
        this.fill = Material.valueOf(config.getString(ConfigEnum.GUI_MAIN_BOUNTY_FILL.getPath()));
        this.fillType = FillType.valueOf(config.getString(ConfigEnum.GUI_MAIN_BOUNTY_FILL_TYPE.getPath()));
        this.bountyListButtonConfig = new BountyListButtonConfig(config);
        this.placeBountyButtonConfig = new PlaceBountyButtonConfig(config);
    }
}
