package com.endcrypt.hitlist.gui.bounty;

import com.endcrypt.hitlist.HitlistPlugin;
import com.endcrypt.hitlist.config.gui.active.ActiveBountiesConfig;
import com.endcrypt.hitlist.config.gui.place.PlaceBountyConfig;
import com.endcrypt.hitlist.gui.GUIType;
import com.endcrypt.hitlist.gui.SortType;
import com.endcrypt.hitlist.utils.HeadUtils;
import com.samjakob.spigui.buttons.SGButton;
import com.samjakob.spigui.item.ItemBuilder;
import com.samjakob.spigui.menu.SGMenu;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.Comparator;
import java.util.List;

public class ActiveBountiesGUI {

    private final HitlistPlugin plugin = HitlistPlugin.instance;

    public void open(Player player, SortType sortType, String searchQuery) {
        player.openInventory(getInventory(player, sortType, searchQuery));
    }

    private Inventory getInventory(Player viewer, SortType sortType, String searchQuery) {
        ActiveBountiesConfig activeBounties = plugin.getConfigManager().getGui().getActiveBounties();
        String title = activeBounties.getTitle();
        int rows = activeBounties.getRows();
        if (rows < 2) rows = 2;
        if (rows > 6) rows = 6;

        SGMenu gui = plugin.getSpiGUI().create(title, rows, title);

        List<OfflinePlayer> validPlayerBounties = plugin.getBountyManager().getActiveBounties().keySet().stream().map(uuid -> Bukkit.getOfflinePlayer(uuid)).toList();
        // Apply search filter if not null or empty
        if (searchQuery != null && !searchQuery.isBlank()) {
            String queryLower = searchQuery.toLowerCase();
            validPlayerBounties = validPlayerBounties.stream()
                    .filter(p -> p.getName().toLowerCase().contains(queryLower))
                    .toList();
        }

        List<OfflinePlayer> sortedPlayers = switch (sortType) {
            case ALPHABETICAL -> validPlayerBounties.stream()
                    .sorted(Comparator.comparing(OfflinePlayer::getName))
                    .toList();

            case HIGHEST_VALUE -> validPlayerBounties.stream()
                    .sorted(Comparator.comparingDouble((OfflinePlayer p) -> {
                        if (plugin.getBountyManager().getActiveBounties().containsKey(p.getUniqueId())) {
                            return plugin.getBountyManager().getActiveBounties().get(p.getUniqueId()).getAmount();
                        }
                        return 0.0;
                    }).reversed()) // Highest first
                    .toList();

            case LOWEST_VALUE -> validPlayerBounties.stream()
                    .sorted(Comparator.comparingDouble((OfflinePlayer p) -> {
                        if (plugin.getBountyManager().getActiveBounties().containsKey(p.getUniqueId())) {
                            return plugin.getBountyManager().getActiveBounties().get(p.getUniqueId()).getAmount();
                        }
                        return 0.0;
                    })) // Lowest first (default)
                    .toList();
        };

        int tempoSlot = 0;
        int slot = 0;
        handlePaging(gui, rows, slot, sortType, searchQuery);
        for (OfflinePlayer player : sortedPlayers) {
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

    private SGButton playerButton(OfflinePlayer player, SortType sortType, String searchQuery) {
        ActiveBountiesConfig activeBounties = plugin.getConfigManager().getGui().getActiveBounties();

        return new SGButton(
                new ItemBuilder(HeadUtils.getPlayerHead(player.getName()))
                        .name(activeBounties.getPlayerButtonConfig().getName(player.getName()))
                        .lore(activeBounties.getPlayerButtonConfig().getLore(player.getName(), plugin.getBountyManager().getActiveBounties().get(player.getUniqueId())))
                        .build()
        ).withListener((InventoryClickEvent event) -> {
            Player playerClicked = (Player) event.getWhoClicked();
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

            // Cycle through sort types
            SortType nextSortType = switch (sortType) {
                case ALPHABETICAL -> SortType.HIGHEST_VALUE;
                case HIGHEST_VALUE -> SortType.LOWEST_VALUE;
                case LOWEST_VALUE -> SortType.ALPHABETICAL;
            };

            open(player, nextSortType, null);
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
                plugin.getGuiManager().getSearchAnvilGUI().open(player, GUIType.ACTIVE_BOUNTIES);
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
        PlaceBountyConfig placeBounty = plugin.getConfigManager().getGui().getPlaceBounty();
        int base = slot + ((rows * 9));
        if (gui.getMaxPage() > 1) {
            if (gui.getCurrentPage() > 1) {
                gui.setButton(base - 6, previousPageButton(gui));
            }
            if (gui.getMaxPage() != gui.getCurrentPage()) {
                gui.setButton(base - 4, nextPageButton(gui));
            }
        }
        if(placeBounty.getSortButtonConfig().isEnabled()) gui.setButton(base - 8, sortButton(sortType));
        gui.setButton(base - 5, searchButton(searchQuery));
    }
}
