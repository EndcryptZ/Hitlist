package com.endcrypt.hitlist.bounty;

import com.endcrypt.hitlist.HitlistPlugin;
import com.endcrypt.hitlist.player.PlayerData;
import com.endcrypt.hitlist.utils.EconomyUtils;
import org.bukkit.entity.Player;

public class BountyManager {

    private static final HitlistPlugin plugin = HitlistPlugin.instance;

    public BountyManager() {
    }


    public void placeBounty(Player target, Player placer, double amount) {
        if(!EconomyUtils.hasEnoughMoney(placer, amount)) {
            plugin.sendMessage(placer, plugin.getConfigManager().getMessages().getErrorNotEnoughMoney());
            return;
        }


        // Get existing PlayerData or create new one
        PlayerData targetData = plugin.getPlayerManager().getPlayerData(target);
        if (targetData == null) {
            targetData = new PlayerData(target.getUniqueId());
        }

        boolean isStacking = plugin.getConfigManager().getMainConfig().isStackingEnabled();
        boolean hasActiveBounty = targetData.getBounty() != null && targetData.getBounty().isActive();

        // Check if target already has an active bounty and stacking is disabled
        if (!isStacking && hasActiveBounty) {
            plugin.sendMessage(placer, plugin.getConfigManager().getMessages().getErrorActiveBounty(target.getName()));
            return;
        }

        // Create new bounty data
        BountyData bountyData = new BountyData(placer.getUniqueId(), amount, false);

        // Handle stacking if enabled
        if (isStacking && hasActiveBounty) {
            double newAmount = targetData.getBounty().getAmount() + amount;
            bountyData = new BountyData(placer.getUniqueId(), newAmount, false);
            plugin.sendMessage(placer, plugin.getConfigManager().getMessages().getStackBounty(target.getName(), String.valueOf(amount)));
        } else {
            plugin.sendMessage(placer, plugin.getConfigManager().getMessages().getPlaceBounty(target.getName(), String.valueOf(amount)));
        }

        // Set and save the bounty
        EconomyUtils.withdraw(placer, amount);
        targetData.setBounty(bountyData);
        plugin.getPlayerManager().addPlayer(target, targetData);
        plugin.getStorageManager().getPlayerStorage().setPlayerBounty(target.getUniqueId(), bountyData);
    }


}
