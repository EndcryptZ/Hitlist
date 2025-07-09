package com.endcrypt.hitlist.player;

import com.endcrypt.hitlist.HitlistPlugin;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
public class PlayerManager {

    private static final HitlistPlugin plugin = HitlistPlugin.instance;
    private final Map<UUID, PlayerData> playerDataMap = new HashMap<>();

    public void loadPlayer(Player player) {
        UUID uuid = player.getUniqueId();
        PlayerData playerData = plugin.getStorageManager().getPlayerStorage().loadPlayer(uuid);
        playerDataMap.put(uuid, playerData);
    }

    public void removePlayer(Player player) {
        UUID uuid = player.getUniqueId();
        plugin.getStorageManager().getPlayerStorage().savePlayer(playerDataMap.get(uuid));
        playerDataMap.remove(uuid);
    }

    public PlayerData getPlayerData(Player player) {
        return playerDataMap.get(player.getUniqueId());
    }

    public void loadAllPlayers() {
        playerDataMap.clear();
        Bukkit.getOnlinePlayers().forEach(this::loadPlayer);
        plugin.getLogger().info("Loaded " + playerDataMap.size() + " player data.");
    }

    public void saveAllPlayers() {
        playerDataMap.forEach((uuid, playerData) -> plugin.getStorageManager().getPlayerStorage().savePlayer(playerData));
        plugin.getLogger().info("Saved " + playerDataMap.size() + " player data.");
    }
}
