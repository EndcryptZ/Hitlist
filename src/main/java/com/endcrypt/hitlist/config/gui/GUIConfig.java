package com.endcrypt.hitlist.config.gui;

import com.endcrypt.hitlist.config.gui.main.MainBountyConfig;
import com.endcrypt.hitlist.config.gui.place.PlaceBountyConfig;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;

@Getter
public class GUIConfig {
    private final FileConfiguration config;
    private final MainBountyConfig mainBounty;
    private final PlaceBountyConfig placeBounty;

    public GUIConfig(FileConfiguration config) {
        this.config = config;
        this.mainBounty = new MainBountyConfig(config);
        this.placeBounty = new PlaceBountyConfig(config);
    }


}
