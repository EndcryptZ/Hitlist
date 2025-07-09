package com.endcrypt.hitlist.player;

import com.endcrypt.hitlist.HitlistPlugin;
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
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
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
}
