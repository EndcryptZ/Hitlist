package com.endcrypt.hitlist.gui.bounty;

import com.endcrypt.hitlist.HitlistPlugin;
import com.endcrypt.hitlist.gui.SortType;
import com.endcrypt.hitlist.utils.EconomyUtils;
import com.samjakob.spigui.item.ItemBuilder;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;
import java.util.List;

public class BountyAmountAnvilGUI {

    private final HitlistPlugin plugin = HitlistPlugin.instance;

    public void open(Player player, Player target, SortType sortType, String searchQuery, String input) {
        ItemStack outputItem = new ItemBuilder(Material.BOOK)
                .name(player.getName())
                .build();
        new AnvilGUI.Builder()
                .onClick((slot, stateSnapshot) -> {
                    if (slot != AnvilGUI.Slot.OUTPUT) return Collections.emptyList();
                    try {
                        double amount = Double.parseDouble(stateSnapshot.getText());
                        if (!EconomyUtils.hasEnoughMoney(player, amount)) {
                            open(player, target, sortType, searchQuery, plugin.getConfigManager().getMessages().getGUINotEnoughMoney());
                            return List.of(AnvilGUI.ResponseAction.close());
                        }

                        if (amount < plugin.getConfigManager().getMain().getMinBountyAmount()) {
                            open(player, target, sortType, searchQuery, plugin.getConfigManager().getMessages().getGUIMinAmount());
                            return List.of(AnvilGUI.ResponseAction.close());
                        }

                        if (amount > plugin.getConfigManager().getMain().getMaxBountyAmount()) {
                            open(player, target, sortType, searchQuery, plugin.getConfigManager().getMessages().getGUIMaxAmount());
                            return List.of(AnvilGUI.ResponseAction.close());
                        }

                        plugin.getBountyManager().placeBounty(target, player, amount);
                        plugin.getGuiManager().getPlaceBountyGUI().open(player, sortType, searchQuery);
                    } catch (NumberFormatException e) {
                        open(player, target, sortType, searchQuery, plugin.getConfigManager().getMessages().getGUIInvalidNumber());

                    }

                    return List.of(AnvilGUI.ResponseAction.close());
                })
                .itemOutput(outputItem)
                .title("Enter Bounty Amount")
                .text(input)
                .plugin(plugin)
                .open(player);
    }
}
