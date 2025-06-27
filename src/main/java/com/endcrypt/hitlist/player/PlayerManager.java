package com.endcrypt.hitlist.player;

import com.endcrypt.hitlist.HitlistPlugin;
import com.endcrypt.hitlist.bounty.BountyData;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class PlayerManager {

    private final static HitlistPlugin plugin = HitlistPlugin.instance;
    private final HashMap<Player, PlayerData> playerDataMap = new HashMap<>();
    public PlayerManager() {
    }

    public void addPlayer(Player player, PlayerData playerData) {
        playerDataMap.put(player, playerData);
    }

    public void removePlayer(Player player) {
        playerDataMap.remove(player);
    }

    public PlayerData getPlayerData(Player player) {
        return playerDataMap.get(player);
    }

    public void loadPlayers() {
        for (Player player : plugin.getServer().getOnlinePlayers()) {
            PlayerData playerData = new PlayerData(player.getUniqueId());
        }
    }
}
