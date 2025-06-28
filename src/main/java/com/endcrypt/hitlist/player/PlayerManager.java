package com.endcrypt.hitlist.player;

import com.endcrypt.hitlist.HitlistPlugin;
import com.endcrypt.hitlist.bounty.BountyData;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class PlayerManager {

    private final static HitlistPlugin plugin = HitlistPlugin.instance;
    private final HashMap<Player, PlayerData> playerDataMap = new HashMap<>();
    public PlayerManager() {
    }

    public PlayerData getPlayerData(Player player) {
        return playerDataMap.get(player);
    }

    public void addPlayer(Player player, PlayerData playerData) {
        playerDataMap.put(player, playerData);
    }

    public void loadPlayers() {
        for (Player player : plugin.getServer().getOnlinePlayers()) {
            loadPlayer(player);
        }
    }

    public void loadPlayer(Player player) {
        if(plugin.getStorageManager().getPlayerStorage().getPlayerBounty(player.getUniqueId()) == null) return;
        PlayerData playerData = new PlayerData(player.getUniqueId());
        BountyData bounty = plugin.getStorageManager().getPlayerStorage().getPlayerBounty(player.getUniqueId());
        playerData.setBounty(bounty);
        playerDataMap.put(player, playerData);
    }

    public void savePlayer(Player player) {
        if(playerDataMap.get(player) == null) return;
        BountyData bounty = playerDataMap.get(player).getBounty();
        if(bounty == null) return;
        plugin.getStorageManager().getPlayerStorage().setPlayerBounty(player.getUniqueId(), bounty);
    }
}
