package com.endcrypt.hitlist.leaderboard;

import com.endcrypt.hitlist.HitlistPlugin;
import org.bukkit.Bukkit;

public class LeaderboardTask {
    private final HitlistPlugin plugin = HitlistPlugin.instance;

    public LeaderboardTask() {
        run();
    }

    private void run() {
        Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            plugin.getLeaderboardManager().loadLeaderboards();
        }, 20L, plugin.getConfigManager().getMain().getLeaderboardsRefreshIntervalMillis());
    }
}
