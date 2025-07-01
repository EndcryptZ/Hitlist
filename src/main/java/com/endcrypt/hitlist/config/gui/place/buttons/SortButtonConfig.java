package com.endcrypt.hitlist.config.gui.place.buttons;

import com.endcrypt.hitlist.config.ConfigEnum;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

@Getter
public class SortButtonConfig {

    private final Material material;
    private final String name;
    private final String[] lore;
    public SortButtonConfig(FileConfiguration config) {
        this.material = Material.valueOf(config.getString(ConfigEnum.GUI_PLACE_BOUNTY_SORT_BUTTON_MATERIAL.getPath()));
        this.name = config.getString(ConfigEnum.GUI_PLACE_BOUNTY_SORT_BUTTON_NAME.getPath());
        this.lore = config.getStringList(ConfigEnum.GUI_PLACE_BOUNTY_SORT_BUTTON_LORE.getPath()).toArray(new String[0]);
    }

    public String getName(String sortType) {
        return name.replace("%sort_type%", sortType);
    }
}
