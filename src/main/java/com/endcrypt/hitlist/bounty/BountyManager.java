package com.endcrypt.hitlist.bounty;

import com.endcrypt.hitlist.HitlistPlugin;
import com.endcrypt.hitlist.player.PlayerData;
import org.bukkit.entity.Player;

public class BountyManager {

    private static final HitlistPlugin plugin = HitlistPlugin.instance;

    public BountyManager() {
    }

    public boolean placeBounty(Player target, Player placer, double amount) {
        BountyData bountyData;
        PlayerData targetData;
        if (plugin.getConfigManager().getMainConfig().isStackingEnabled()) {
            bountyData = plugin.getPlayerManager().getPlayerData(target).getBounty();
            targetData = plugin.getPlayerManager().getPlayerData(target);

            bountyData.setAmount(bountyData.getAmount() + amount);
            targetData.setBounty(bountyData);
            return true;
        } else {
            bountyData = new BountyData(placer.getUniqueId(), amount, false);
            targetData = plugin.getPlayerManager().getPlayerData(target);
            targetData.setBounty(bountyData);
        }
        plugin.getPlayerManager().addPlayer(target, targetData);
    }
}
