package com.endcrypt.hitlist.leaderboard;

import com.endcrypt.hitlist.HitlistPlugin;
import com.endcrypt.hitlist.player.PlayerData;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Getter
public class LeaderboardManager {

    private static final HitlistPlugin plugin = HitlistPlugin.instance;
    private final Map<UUID, Double> TopClaimedBounties = new HashMap<>();
    private final Map<UUID, Double> TopPlacedBounties = new HashMap<>();

    public LeaderboardManager() {
        loadLeaderboards();
        new LeaderboardTask();
    }

    public void loadLeaderboards() {
        List<PlayerData> playerDataList = plugin.getStorageManager().getPlayerStorage().loadAllPlayers();
        playerDataList.forEach(playerData -> {
            TopClaimedBounties.put(playerData.getUniqueId(), playerData.getTotalClaimedBounty());
            TopPlacedBounties.put(playerData.getUniqueId(), playerData.getTotalPlacedBounty());
        });
        plugin.getLogger().info("Refreshing leaderboards...");
    }
}
