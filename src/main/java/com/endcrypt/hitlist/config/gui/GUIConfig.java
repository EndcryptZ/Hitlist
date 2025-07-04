package com.endcrypt.hitlist.config.gui;

import com.endcrypt.hitlist.config.gui.active.ActiveBountiesConfig;
import com.endcrypt.hitlist.config.gui.main.MainBountyConfig;
import com.endcrypt.hitlist.config.gui.place.PlaceBountyConfig;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;

@Getter
public class GUIConfig {
    private final FileConfiguration config;
    private final MainBountyConfig mainBounty;
    private final PlaceBountyConfig placeBounty;
    private final ActiveBountiesConfig activeBounties;

    public GUIConfig(FileConfiguration config) {
        this.config = config;
        this.mainBounty = new MainBountyConfig(config);
        this.placeBounty = new PlaceBountyConfig(config);
        this.activeBounties = new ActiveBountiesConfig(config);
    }


}
