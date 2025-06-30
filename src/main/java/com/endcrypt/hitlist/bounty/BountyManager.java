package com.endcrypt.hitlist.bounty;

import com.endcrypt.hitlist.HitlistPlugin;
import com.endcrypt.hitlist.permissions.PermissionsEnum;
import com.endcrypt.hitlist.utils.EconomyUtils;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.Map;

@Getter
public class BountyManager {

    private static final HitlistPlugin plugin = HitlistPlugin.instance;
    private final Map<OfflinePlayer, BountyData> activeBounties;

    public BountyManager() {
        activeBounties = plugin.getStorageManager().getBountyStorage().loadAllBounties();
    }


    public void placeBounty(Player target, Player placer, double amount) {
        if(target == placer) {
            String message = plugin.getConfigManager().getMessages().getErrorSelfBounty();
            plugin.sendMessage(placer, message);
            return;
        }

        if(!EconomyUtils.hasEnoughMoney(placer, amount)) {
            plugin.sendMessage(placer, plugin.getConfigManager().getMessages().getErrorNotEnoughMoney());
            return;
        }

        boolean isStacking = plugin.getConfigManager().getMainConfig().isStackingEnabled();
        boolean hasActiveBounty = activeBounties.containsKey(target);

        // Check if target already has an active bounty and stacking is disabled
        if (!isStacking && hasActiveBounty) {
            plugin.sendMessage(placer, plugin.getConfigManager().getMessages().getErrorActiveBounty(target.getName()));
            return;
        }

        // Create new bounty data
        BountyData bountyData = activeBounties.getOrDefault(target, new BountyData(target.getUniqueId(), placer.getUniqueId(), amount, false));

        // Handle stacking if enabled
        if (isStacking && hasActiveBounty) {
            double newAmount = bountyData.getAmount() + amount;
            bountyData = new BountyData(target.getUniqueId(), placer.getUniqueId(), newAmount, false);
            plugin.sendMessage(placer, plugin.getConfigManager().getMessages().getBountyPlaceStack(target.getName(), String.valueOf(amount)));
        } else {
            plugin.sendMessage(placer, plugin.getConfigManager().getMessages().getBountyPlace(target.getName(), String.valueOf(amount)));
        }

        // Set the bounty
        EconomyUtils.withdraw(placer, amount);
        plugin.getStorageManager().getBountyStorage().saveBounty(bountyData);
        activeBounties.put(target, bountyData);
    }

    public void removeBounty(OfflinePlayer target, Player canceller) {
        BountyData bountyData = activeBounties.get(target);
        OfflinePlayer placer = Bukkit.getOfflinePlayer(bountyData.getPlacerId());

        if(!canceller.hasPermission(PermissionsEnum.PERMISSION_BOUNTY_REMOVE_OTHERS.getPermission()) && placer != canceller) {
            plugin.sendMessage(canceller, plugin.getConfigManager().getMessages().getNoPermissionBountyCancelOthers());
            return;
        }

        activeBounties.remove(target);
        plugin.getStorageManager().getBountyStorage().removeBounty(target.getUniqueId());
        plugin.sendMessage(canceller, plugin.getConfigManager().getMessages().getBountyRemove(target.getName()));

    }


}
