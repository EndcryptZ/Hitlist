package com.endcrypt.hitlist.player;

import com.endcrypt.hitlist.HitlistPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {

    private static final HitlistPlugin plugin = HitlistPlugin.instance;
    public PlayerListener() {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerDataLoad(PlayerJoinEvent event) {
        PlayerData playerData = new PlayerData(event.getPlayer().getUniqueId());
        plugin.getPlayerManager().addPlayer(event.getPlayer(), playerData);
    }

    @EventHandler
    public void onPlayerDataUnload(PlayerQuitEvent event) {

        plugin.getPlayerManager().removePlayer(event.getPlayer());
    }
}
