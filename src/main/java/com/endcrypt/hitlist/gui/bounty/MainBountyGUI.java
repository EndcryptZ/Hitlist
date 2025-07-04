package com.endcrypt.hitlist.gui.bounty;

import com.endcrypt.hitlist.HitlistPlugin;
import com.endcrypt.hitlist.config.gui.main.MainBountyConfig;
import com.endcrypt.hitlist.gui.FillType;
import com.endcrypt.hitlist.gui.SortType;
import com.samjakob.spigui.buttons.SGButton;
import com.samjakob.spigui.item.ItemBuilder;
import com.samjakob.spigui.menu.SGMenu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class MainBountyGUI {

    private final HitlistPlugin plugin = HitlistPlugin.instance;

    public void open(Player player) {
        player.openInventory(getInventory());
    }

    private Inventory getInventory() {
        MainBountyConfig mainBounty = plugin.getConfigManager().getGui().getMainBounty();
        String name = mainBounty.getTitle();
        int rows = mainBounty.getRows();
        FillType fillType = mainBounty.getFillType();

        SGMenu gui = plugin.getSpiGUI().create(name, rows, name);

        plugin.getGuiManager().handleFill(fillType, gui, fillerButton(), rows);

        gui.setButton(mainBounty.getActiveBountiesButtonConfig().getSlot(), activeBountiesButton());
        gui.setButton(mainBounty.getPlaceBountyButtonConfig().getSlot(), placeBountyButton());

        return gui.getInventory();
    }

    private SGButton activeBountiesButton() {
        MainBountyConfig mainBounty = plugin.getConfigManager().getGui().getMainBounty();
        Material material = mainBounty.getActiveBountiesButtonConfig().getMaterial();
        String name = mainBounty.getActiveBountiesButtonConfig().getName();
        String[] lore = mainBounty.getActiveBountiesButtonConfig().getLore();

        return new SGButton(
                new ItemBuilder(material)
                        .name(name)
                        .lore(lore)
                        .build()
        ).withListener((InventoryClickEvent event) -> {
            Player player = (Player) event.getWhoClicked();
            plugin.getGuiManager().getActiveBountiesGUI().open(player, SortType.ALPHABETICAL, null);
        });
    }

    private SGButton placeBountyButton() {
        MainBountyConfig mainBounty = plugin.getConfigManager().getGui().getMainBounty();
        Material material = mainBounty.getPlaceBountyButtonConfig().getMaterial();
        String name = mainBounty.getPlaceBountyButtonConfig().getName();
        String[] lore = mainBounty.getPlaceBountyButtonConfig().getLore();

        return new SGButton(
                new ItemBuilder(material)
                        .name(name)
                        .lore(lore)
                        .build()
        ).withListener((InventoryClickEvent event) -> {
            Player player = (Player) event.getWhoClicked();
            plugin.getGuiManager().getPlaceBountyGUI().open(player, SortType.ALPHABETICAL, null);
        });
    }

    private SGButton fillerButton() {
        MainBountyConfig mainBounty = plugin.getConfigManager().getGui().getMainBounty();
        Material material = mainBounty.getFill();
        return new SGButton(
                new ItemBuilder(material)
                        .name(" ")
                        .build()
        );
    }


}
