package com.endcrypt.hitlist.gui.bounty;

import com.endcrypt.hitlist.HitlistPlugin;
import com.endcrypt.hitlist.config.gui.place.PlaceBountyConfig;
import com.endcrypt.hitlist.gui.SortType;
import com.endcrypt.hitlist.utils.HeadUtils;
import com.samjakob.spigui.buttons.SGButton;
import com.samjakob.spigui.item.ItemBuilder;
import com.samjakob.spigui.menu.SGMenu;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class PlaceBountyGUI {

    private final HitlistPlugin plugin = HitlistPlugin.instance;

    public void open(Player player, SortType sortType) {
        player.openInventory(getInventory(player, sortType));
    }

    private Inventory getInventory(Player viewer, SortType sortType) {
        PlaceBountyConfig placeBounty = plugin.getConfigManager().getGui().getPlaceBounty();
        String name = placeBounty.getTitle();
        int rows = placeBounty.getRows();
        if(rows < 2) rows = 2;
        if(rows > 6) rows = 6;

        SGMenu gui = plugin.getSpiGUI().create(name, rows, name);

        List<Player> validPlayers = new ArrayList<>();
        List<Player> sortedPlayers = new ArrayList<>();
        for (Player player : Bukkit.getOnlinePlayers()) {
            if(player == viewer) continue;
            if(plugin.getBountyManager().getActiveBounties().containsKey(player.getUniqueId()) && !plugin.getConfigManager().getMain().isStackingEnabled()) continue;
            validPlayers.add(player);
        }

        switch (sortType) {
            case ALPHABETICAL -> sortedPlayers = validPlayers.stream()
                    .sorted(Comparator.comparing(p -> {
                        p.getName();
                        return p.getName();
                    }))
                    .toList();

            case HIGHEST_VALUE -> sortedPlayers = validPlayers.stream()
                    .filter(Objects::nonNull)
                    .sorted(Comparator.comparingDouble(player -> {
                        if(plugin.getBountyManager().getActiveBounties().containsKey(player.getUniqueId())) {
                            return plugin.getBountyManager().getActiveBounties().get(player.getUniqueId()).getAmount();
                        }
                        return 0.0;
                    })).toList();
        }

        int tempoSlot = 0;
        int slot = 0;
        handlePaging(gui, rows, slot, sortType);
        for (Player player : sortedPlayers) {
            if (tempoSlot == 0) {
                handlePaging(gui, rows, slot, sortType);
            }
            if(tempoSlot == (rows * 9) - 9) {
                slot += 9;
                tempoSlot = 0;
                gui.setButton(slot, playerButton(player));
                continue;
            }

            gui.setButton(slot, playerButton(player));
            tempoSlot++;
            slot++;
        }

        return gui.getInventory();
    }

    private SGButton playerButton(Player player) {
        PlaceBountyConfig placeBounty = plugin.getConfigManager().getGui().getPlaceBounty();

        return new SGButton(
                new ItemBuilder(HeadUtils.getPlayerHead(player.getName()))
                        .name(placeBounty.getPlayerButtonConfig().getName(player.getName()))
                        .lore(placeBounty.getPlayerButtonConfig().getLore(player.getName(), plugin.getBountyManager().getActiveBounties().get(player.getUniqueId()).getAmount()))
                        .build()
        );
    }
    private SGButton sortButton(SortType sortType) {
        PlaceBountyConfig placeBounty = plugin.getConfigManager().getGui().getPlaceBounty();
        return new SGButton(
                new ItemBuilder(placeBounty.getSortButtonConfig().getMaterial())
                        .name(placeBounty.getSortButtonConfig().getName(sortType.getName()))
                        .lore(placeBounty.getSortButtonConfig().getLore())
                        .build()
        ).withListener((InventoryClickEvent event) -> {
            Player player = (Player) event.getWhoClicked();
            if(sortType == SortType.ALPHABETICAL) {
                open(player, SortType.HIGHEST_VALUE);
            } else {
                open(player, SortType.ALPHABETICAL);
            }
        });
    }

    private SGButton previousPageButton(SGMenu gui) {
        return new SGButton(
                new ItemBuilder(Material.RED_CANDLE)
                        .name("&c&lPrevious Page")
                        .lore(
                                "&aClick to move back to",
                                "&apage " + (gui.getCurrentPage() - 1)
                        )
                        .build()
        )
                .withListener((InventoryClickEvent event) -> {
                    gui.setCurrentPage(gui.getCurrentPage() - 1);
                });
    }

    private SGButton nextPageButton(SGMenu gui) {
        return new SGButton(
                new ItemBuilder(Material.RED_CANDLE)
                        .name("&a&lNext Page")
                        .lore(
                                "&aClick to move forward to",
                                "&apage " + (gui.getCurrentPage() + 1)
                        )
                        .build()
        )
                .withListener((InventoryClickEvent event) -> {
                    gui.setCurrentPage(gui.getCurrentPage() + 1);
                });
    }

    private void handlePaging(SGMenu gui, int rows, int slot, SortType sortType) {
        int base = slot + ((rows * 9));
        if (gui.getMaxPage() > 1) {
            if (gui.getCurrentPage() > 1) {
                gui.setButton(base - 6, previousPageButton(gui));
            }
            if (gui.getMaxPage() != gui.getCurrentPage()) {
                gui.setButton(base - 4, nextPageButton(gui));
            }
        }
        gui.setButton(base - 5, sortButton(sortType));
    }

}
