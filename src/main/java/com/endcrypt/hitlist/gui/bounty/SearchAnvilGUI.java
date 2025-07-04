package com.endcrypt.hitlist.gui.bounty;

import com.endcrypt.hitlist.HitlistPlugin;
import com.endcrypt.hitlist.gui.GUIType;
import com.endcrypt.hitlist.gui.SortType;
import com.samjakob.spigui.item.ItemBuilder;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;
import java.util.List;

public class SearchAnvilGUI {

    private final HitlistPlugin plugin = HitlistPlugin.instance;

    public void open(Player player, GUIType guiType) {
        ItemStack outputItem = new ItemBuilder(Material.BOOK)
                .name(player.getName())
                .build();
        new AnvilGUI.Builder()
                .onClick((slot, stateSnapshot) -> {
                    if(slot != AnvilGUI.Slot.OUTPUT) return Collections.emptyList();

                    if(guiType == GUIType.PLACE_BOUNTY) plugin.getGuiManager().getPlaceBountyGUI().open(player, SortType.ALPHABETICAL, stateSnapshot.getText());
                    if(guiType == GUIType.ACTIVE_BOUNTIES) plugin.getGuiManager().getActiveBountiesGUI().open(player, SortType.ALPHABETICAL, stateSnapshot.getText());

                    return List.of(AnvilGUI.ResponseAction.close());
                })
                .itemOutput(outputItem)
                .title("Search for a player")
                .text("Search...")
                .plugin(plugin)
                .open(player);
    }

}