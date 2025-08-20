package com.endcrypt.hitlist.bounty;

import com.endcrypt.hitlist.HitlistPlugin;
import com.endcrypt.hitlist.permissions.PermissionsEnum;
import com.endcrypt.hitlist.player.PlayerData;
import com.endcrypt.hitlist.utils.EconomyUtils;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.*;

@Getter
public class BountyManager {

    private static final HitlistPlugin plugin = HitlistPlugin.instance;
    private final Map<UUID, BountyData> activeBounties;

        public BountyManager() {
            activeBounties = plugin.getStorageManager().getBountyStorage().loadAllBounties();
            new BountyTask();
        }


    /**
     * Places a bounty on a target player by a placer.
     * Handles max bounty limits, amount validation, economy checks,
     * stacking behavior, and active bounty state.
     */
    public void placeBounty(Player target, Player placer, double amount) {
        double placementFee = 0;
        int placerBountyCount = 0;

        // Get the max number of bounties this player can place based on permission
        int limit = plugin.getPermissionManager().getMaxBountiesAllowed(placer);

        // If a placement fee is enabled, retrieve the fee amount
        if (plugin.getConfigManager().getMain().isPlacementFeeEnabled()) {
            placementFee = plugin.getConfigManager().getMain().getPlacementFeeAmount();
        }

        // Count the number of active bounties this player has placed
        for (BountyData bountyData : activeBounties.values()) {
            if (bountyData.getPlacerId() == placer.getUniqueId()) {
                placerBountyCount++;
            }

            // If they've reached the limit, cancel the bounty placement
            if (placerBountyCount >= limit) {
                plugin.sendMessage(placer, plugin.getConfigManager().getMessages().getErrorMaxBountiesLimit(limit));
                return;
            }
        }

        // Prevent placing a bounty on oneself
        if (target == placer) {
            plugin.sendMessage(placer, plugin.getConfigManager().getMessages().getErrorSelfBounty());
            return;
        }

        // Check if the bounty amount is within allowed bounds
        if (amount > plugin.getConfigManager().getMain().getMaxBountyAmount()) {
            plugin.sendMessage(placer, plugin.getConfigManager().getMessages().getErrorMaxAmount(String.valueOf(plugin.getConfigManager().getMain().getMaxBountyAmount())));
            placer.playSound(placer.getLocation(), Sound.BLOCK_ANVIL_LAND, 1, 1);
            return;
        }

        if (amount < plugin.getConfigManager().getMain().getMinBountyAmount()) {
            plugin.sendMessage(placer, plugin.getConfigManager().getMessages().getErrorMinAmount(String.valueOf(plugin.getConfigManager().getMain().getMinBountyAmount())));
            placer.playSound(placer.getLocation(), Sound.BLOCK_ANVIL_LAND, 1, 1);
            return;
        }

        // Verify the player has enough money to cover the bounty + fee
        if (!EconomyUtils.hasEnoughMoney(placer, amount + placementFee)) {
            placer.playSound(placer.getLocation(), Sound.BLOCK_ANVIL_LAND, 1, 1);
            plugin.sendMessage(placer, plugin.getConfigManager().getMessages().getErrorNotEnoughMoney(amount + placementFee));
            return;
        }

        boolean isStacking = plugin.getConfigManager().getMain().isStackingEnabled();
        boolean hasActiveBounty = activeBounties.containsKey(target.getUniqueId());

        // If stacking is disabled and the target already has a bounty, cancel
        if (!isStacking && hasActiveBounty) {
            plugin.sendMessage(placer, plugin.getConfigManager().getMessages().getErrorActiveBounty(target.getName()));
            return;
        }

        // Get or create bounty data for the target
        BountyData bountyData = activeBounties.getOrDefault(
                target.getUniqueId(),
                new BountyData(target.getUniqueId(), placer.getUniqueId(), new HashMap<>(), amount, false)
        );
        Map<UUID, Double> placersMap = bountyData.getPlacersMap();
        if(!placersMap.containsKey(placer.getUniqueId())) {
            placersMap.put(placer.getUniqueId(), amount);
        } else {
            placersMap.replace(placer.getUniqueId(), placersMap.get(placer.getUniqueId()) + amount);
        }

        // Handle bounty-stacking logic
        if (isStacking && hasActiveBounty) {
            long placementTime = bountyData.getPlacementTime();
            double newAmount = bountyData.getAmount() + amount;

            // If the current placer is different from the original placer
            if (!placer.getUniqueId().equals(bountyData.getPlacerId())) {
                bountyData = new BountyData(
                        target.getUniqueId(),
                        bountyData.getPlacerId(),
                        placersMap,
                        newAmount,
                        false
                );
            } else {
                bountyData = new BountyData(
                        target.getUniqueId(),
                        placer.getUniqueId(),
                        placersMap,
                        newAmount,
                        false
                );
            }

            bountyData.setPlacementTime(placementTime);
            plugin.sendMessage(placer, plugin.getConfigManager().getMessages().getBountyPlaceStack(
                    target.getName(), String.valueOf(amount)
            ));
        } else {
            // New bounty without stacking
            plugin.sendMessage(placer, plugin.getConfigManager().getMessages().getBountyPlace(
                    target.getName(), String.valueOf(amount)
            ));
        }

        // Withdraw the money and save the bounty
        EconomyUtils.withdraw(placer, amount + placementFee);
        plugin.getStorageManager().getBountyStorage().saveBounty(bountyData);
        activeBounties.put(target.getUniqueId(), bountyData);
    }

    /**
     * Removes an active bounty on a target player.
     * Validates permission if the canceller is not the original placer.
     * Optionally refunds the bounty amount based on config.
     *
     * @param target The player who has the bounty.
     * @param canceller The player attempting to remove the bounty, or null if removed automatically.
     */
    public void removeBounty(OfflinePlayer target, @Nullable Player canceller) {
        // Retrieve bounty data associated with the target
        BountyData bountyData = activeBounties.get(target.getUniqueId());
        if (bountyData == null) return; // No bounty to remove

        // Get the original placer of the bounty
        OfflinePlayer placer = Bukkit.getOfflinePlayer(bountyData.getPlacerId());

        // If a canceller is provided, check permission
        if (canceller != null) {
            boolean isCancellerPlacer = placer.getUniqueId().equals(canceller.getUniqueId());
            if (!canceller.hasPermission(PermissionsEnum.PERMISSION_BOUNTY_EDIT_OTHERS.getPermission()) && !isCancellerPlacer) {
                plugin.sendMessage(canceller, plugin.getConfigManager().getMessages().getNoPermissionBountyEditOthers());
                return;
            }

            // Notify the canceller
            plugin.commandSenderMessage(canceller, plugin.getConfigManager().getMessages().getBountyRemove(target.getName()));
        }

        // Remove the bounty from memory and storage
        activeBounties.remove(target.getUniqueId());
        plugin.getStorageManager().getBountyStorage().removeBounty(target.getUniqueId());

        // Refund the bounty to the placer if refunding is enabled
        if (plugin.getConfigManager().getMain().isRefundOnRemovalEnabled()) {
            EconomyUtils.deposit(placer, bountyData.getPlacersMap().get(placer.getUniqueId()));
        }
    }


    /**
     * Lowers the bounty amount on a target player.
     * Validates if the lowering player is allowed and ensures the proper number of constraints.
     * Refunds the lowered amount to the player.
     *
     * @param target The player whose bounty is being lowered.
     * @param player The player requesting the bounty to be lowered.
     * @param amount The amount to lower the bounty by.
     */
    public void lowerBounty(OfflinePlayer target, Player player, double amount) {
        // Retrieve the bounty data and original placer
        BountyData bountyData = activeBounties.get(target.getUniqueId());
        OfflinePlayer placer = Bukkit.getOfflinePlayer(bountyData.getPlacerId());

        // Only the placer or someone with edit permission can lower the bounty
        boolean isPlacer = placer.getUniqueId().equals(player.getUniqueId());
        if (!player.hasPermission(PermissionsEnum.PERMISSION_BOUNTY_EDIT_OTHERS.getPermission()) && !isPlacer) {
            plugin.sendMessage(player, plugin.getConfigManager().getMessages().getNoPermissionBountyEditOthers());
            return;
        }

        // Ensure the resulting bounty does not go below the minimum allowed
        double newAmount = bountyData.getAmount() - amount;
        if (newAmount < plugin.getConfigManager().getMain().getMinBountyAmount()) {
            plugin.sendMessage(player, plugin.getConfigManager().getMessages().getErrorMinAmount(String.valueOf(plugin.getConfigManager().getMain().getMinBountyAmount())));
            return;
        }

        // Ensure the player has enough "placed" money to reduce
        double newPlacedAmount = bountyData.getPlacersMap().get(player.getUniqueId()) - amount;
        if (newPlacedAmount < 0) {
            plugin.sendMessage(player, plugin.getConfigManager().getMessages().getErrorNotEnoughPlacedMoney(bountyData.getPlacersMap().get(player.getUniqueId())));
            return;
        }

        // Update bounty values
        bountyData.getPlacersMap().replace(player.getUniqueId(), newPlacedAmount);
        bountyData.setAmount(newAmount);

        // Refund the player
        EconomyUtils.deposit(player, amount);

        // Save updated bounty
        activeBounties.put(target.getUniqueId(), bountyData);
        plugin.getStorageManager().getBountyStorage().saveBounty(bountyData);

        // Notify player
        plugin.sendMessage(player, plugin.getConfigManager().getMessages().getBountyLower(
                target.getName(),
                amount,
                newAmount
        ));
    }

    public void claimBounty(Player player, Player target) {
        if (!activeBounties.containsKey(target.getUniqueId())) {
            return;
        }

        BountyData bountyData = activeBounties.get(target.getUniqueId());

        if(bountyData.getPlacerId().equals(player.getUniqueId()) && !plugin.getConfigManager().getMain().isClaimOwnBountyEnabled()) {
            return;
        }

        PlayerData playerData = plugin.getPlayerManager().getPlayerData(player);
        double amount = bountyData.getAmount();
        UUID placerId = bountyData.getPlacerId();

        activeBounties.remove(target.getUniqueId());
        plugin.getStorageManager().getBountyStorage().removeBounty(target.getUniqueId());
        EconomyUtils.deposit(player, amount);
        playerData.setTotalClaimedBounty(amount + playerData.getTotalClaimedBounty());
        plugin.getPlayerManager().dropHead(player, target, amount);
        plugin.getPlayerManager().getPlayerDataMap().put(player.getUniqueId(), playerData);
        plugin.getPlayerManager().modifyPlacedBounty(placerId, amount);
        plugin.sendMessage(player, plugin.getConfigManager().getMessages().getBountyClaim(target.getName(), amount));

    }

    public void expireBounty(UUID uuid) {
        BountyData bountyData = activeBounties.get(uuid);
        OfflinePlayer placer = Bukkit.getOfflinePlayer(bountyData.getPlacerId());

        // Remove the bounty from memory and storage
        EconomyUtils.deposit(placer, bountyData.getPlacersMap().get(placer.getUniqueId()));
        activeBounties.remove(uuid);
        plugin.getStorageManager().getBountyStorage().removeBounty(uuid);
        plugin.getLogger().info("The bounty on " + Bukkit.getOfflinePlayer(uuid).getName() + " has expired.");
    }



}
