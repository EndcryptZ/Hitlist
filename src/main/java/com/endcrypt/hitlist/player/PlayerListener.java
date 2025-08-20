package com.endcrypt.hitlist.player;

import com.endcrypt.hitlist.HitlistPlugin;
import com.endcrypt.hitlist.utils.ColorUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {

    private static final HitlistPlugin plugin = HitlistPlugin.instance;

    public PlayerListener() {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        plugin.getPlayerManager().loadPlayer(event.getPlayer());
        plugin.getLogger().info("Loaded player data for " + event.getPlayer().getName());

        handleOptCooldownOnJoin(event.getPlayer());

    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        handleOptCooldownOnQuit(event.getPlayer());

        plugin.getPlayerManager().removePlayer(event.getPlayer());
        plugin.getLogger().info("Saved player data for " + event.getPlayer().getName() + " to disk.");

    }

    @EventHandler
    public void onPlayerKillOtherPlayer(PlayerDeathEvent event) {
        if(event.getEntity().getKiller() == null) {
            return;
        }

        if(!(event.getEntity().getKiller() instanceof Player killer)) {
            return;
        }

        plugin.getBountyManager().claimBounty(killer, event.getPlayer());

    }

    private void handleOptCooldownOnJoin(Player player) {
        PlayerData playerData = plugin.getPlayerManager().getPlayerData(player);

        boolean isOptInCooldownCountOnlineOnly = plugin.getConfigManager().getMain().isOptInCooldownCountOnlineOnly();
        boolean isOptOutCooldownCountOnlineOnly = plugin.getConfigManager().getMain().isOptOutCooldownCountOnlineOnly();
        boolean isPlayerOptedOut = playerData.isOptedOut();
        long currentPlayerOptCooldown = playerData.getOptCooldown();
        long lastPlayed = player.getLastPlayed();
        long now = System.currentTimeMillis();
        long timeElapsed = now - lastPlayed;
        long newOptCooldown = (timeElapsed >= currentPlayerOptCooldown) ? 0 : currentPlayerOptCooldown - timeElapsed;

        if (isPlayerOptedOut) {
            if (!isOptOutCooldownCountOnlineOnly) {
                plugin.getPlayerManager().getPlayerData(player).setOptCooldown(newOptCooldown);
            }
        } else {
            if (!isOptInCooldownCountOnlineOnly) {
                plugin.getPlayerManager().getPlayerData(player).setOptCooldown(newOptCooldown);
            }
        }
    }

    private void handleOptCooldownOnQuit(Player player) {
        PlayerData playerData = plugin.getPlayerManager().getPlayerData(player);
        long currentPlayerOptCooldown = playerData.getOptCooldown();
        long lastLogin = player.getLastLogin();
        long now = System.currentTimeMillis();
        long timeElapsed = now - lastLogin;
        long newOptCooldown = (timeElapsed >= currentPlayerOptCooldown) ? 0 : currentPlayerOptCooldown - timeElapsed;

        plugin.getPlayerManager().getPlayerData(player).setOptCooldown(newOptCooldown);
    }
}
