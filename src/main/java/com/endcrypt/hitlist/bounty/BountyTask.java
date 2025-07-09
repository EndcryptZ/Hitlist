package com.endcrypt.hitlist.bounty;

import com.endcrypt.hitlist.HitlistPlugin;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BountyTask {

    private final HitlistPlugin plugin = HitlistPlugin.instance;
    public BountyTask() {
        run();
    }

    private void run() {
        Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            // Copy keys first to avoid concurrent modification
            List<UUID> uuids = new ArrayList<>(plugin.getBountyManager().getActiveBounties().keySet());
            for (UUID uuid : uuids) {
                checkBounty(uuid);
            }
        }, 20L, 100L);
    }

    private void checkBounty(UUID uuid) {
        BountyData bountyData = plugin.getBountyManager().getActiveBounties().get(uuid);
        long expirationTimeMillis = bountyData.getPlacementTime() + plugin.getConfigManager().getMain().getExpirationTimeMillis();
        if (expirationTimeMillis < System.currentTimeMillis()) {
            plugin.getBountyManager().expireBounty(uuid);
        }
    }
}
