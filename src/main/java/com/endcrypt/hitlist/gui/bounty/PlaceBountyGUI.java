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

public class PlaceBountyGUI {

    private final HitlistPlugin plugin = HitlistPlugin.instance;

    public void open(Player player, SortType sortType, String searchQuery) {
        player.openInventory(getInventory(player, sortType, searchQuery));
    }

    private Inventory getInventory(Player viewer, SortType sortType, String searchQuery) {
        PlaceBountyConfig placeBounty = plugin.getConfigManager().getGui().getPlaceBounty();
        String name = placeBounty.getTitle();
        int rows = placeBounty.getRows();
        if (rows < 2) rows = 2;
        if (rows > 6) rows = 6;

        SGMenu gui = plugin.getSpiGUI().create(name, rows, name);

        List<Player> validPlayers = new ArrayList<>();
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player == viewer) continue;
            if (plugin.getBountyManager().getActiveBounties().containsKey(player.getUniqueId())
                    && !plugin.getConfigManager().getMain().isStackingEnabled()) continue;

            validPlayers.add(player);
        }

        // Apply search filter if not null or empty
        if (searchQuery != null && !searchQuery.isBlank()) {
            String queryLower = searchQuery.toLowerCase();
            validPlayers = validPlayers.stream()
                    .filter(p -> p.getName().toLowerCase().contains(queryLower))
                    .toList();
        }

        List<Player> sortedPlayers = switch (sortType) {
            case ALPHABETICAL -> validPlayers.stream()
                    .sorted(Comparator.comparing(Player::getName))
                    .toList();

            case HIGHEST_VALUE -> validPlayers.stream()
                    .sorted(Comparator.comparingDouble((Player p) -> {
                        if (plugin.getBountyManager().getActiveBounties().containsKey(p.getUniqueId())) {
                            return plugin.getBountyManager().getActiveBounties().get(p.getUniqueId()).getAmount();
                        }
                        return 0.0;
                    }).reversed()) // Highest first
                    .toList();
        };

        int tempoSlot = 0;
        int slot = 0;
        handlePaging(gui, rows, slot, sortType, searchQuery);
        for (Player player : sortedPlayers) {
            if (tempoSlot == 0) {
                handlePaging(gui, rows, slot, sortType, searchQuery);
            }
            if (tempoSlot == (rows * 9) - 9) {
                slot += 9;
                tempoSlot = 0;
                gui.setButton(slot, playerButton(player, sortType, searchQuery));
                continue;
            }

            gui.setButton(slot, playerButton(player, sortType, searchQuery));
            tempoSlot++;
            slot++;
        }

        return gui.getInventory();
    }


    private SGButton playerButton(Player player, SortType sortType, String searchQuery) {
        PlaceBountyConfig placeBounty = plugin.getConfigManager().getGui().getPlaceBounty();

        return new SGButton(
                new ItemBuilder(HeadUtils.getPlayerHead(player.getName()))
                        .name(placeBounty.getPlayerButtonConfig().getName(player.getName()))
                        .lore(placeBounty.getPlayerButtonConfig().getLore(player.getName(), plugin.getBountyManager().getActiveBounties().get(player.getUniqueId())))
                        .build()
        ).withListener((InventoryClickEvent event) -> {
            Player playerClicked = (Player) event.getWhoClicked();
            plugin.getGuiManager().getBountyAmountAnvilGUI().open(playerClicked, player, sortType, searchQuery);
        });
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
                open(player, SortType.HIGHEST_VALUE, null);
            } else {
                open(player, SortType.ALPHABETICAL, null);
            }
        });
    }

    private SGButton searchButton(String searchTerm) {
        PlaceBountyConfig placeBounty = plugin.getConfigManager().getGui().getPlaceBounty();
        return new SGButton(
                new ItemBuilder(placeBounty.getSearchButtonConfig().getMaterial())
                        .name(placeBounty.getSearchButtonConfig().getName(searchTerm))
                        .lore(placeBounty.getSearchButtonConfig().getLore())
                        .build()
        ).withListener((InventoryClickEvent event) -> {
            Player player = (Player) event.getWhoClicked();
            if(event.isLeftClick()) {
                plugin.getGuiManager().getSearchAnvilGUI().open(player);
            } else if (event.isRightClick()) {
                open(player, SortType.ALPHABETICAL, null);
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

    private void handlePaging(SGMenu gui, int rows, int slot, SortType sortType, String searchQuery) {
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
        gui.setButton(base - 8, searchButton(searchQuery));
    }

}
