package com.endcrypt.hitlist.config.gui.place.buttons;

import com.endcrypt.hitlist.config.ConfigEnum;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

@Getter
public class SearchButtonConfig {

    private final Material material;
    private final String name;
    private final String[] lore;
    public SearchButtonConfig(FileConfiguration config) {
        this.material = Material.valueOf(config.getString(ConfigEnum.GUI_PLACE_BOUNTY_SEARCH_BUTTON_MATERIAL.getPath()));
        this.name = config.getString(ConfigEnum.GUI_PLACE_BOUNTY_SEARCH_BUTTON_NAME.getPath());
        this.lore = config.getStringList(ConfigEnum.GUI_PLACE_BOUNTY_SEARCH_BUTTON_LORE.getPath()).toArray(new String[0]);
    }

    public String getName(String searchQuery) {
        if(searchQuery == null || searchQuery.isEmpty()) searchQuery = "&7Search with name";
        return name.replace("%search_query%", searchQuery);
    }
}
