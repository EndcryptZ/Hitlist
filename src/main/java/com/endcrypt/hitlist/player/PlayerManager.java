package com.endcrypt.hitlist.player;

import com.endcrypt.hitlist.HitlistPlugin;
import com.samjakob.spigui.item.ItemBuilder;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

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

    public void modifyPlacedBounty(UUID uniqueId, double amount) {
        if(playerDataMap.containsKey(uniqueId)) {
            PlayerData playerData = playerDataMap.get(uniqueId);
            playerData.setTotalPlacedBounty(playerData.getTotalPlacedBounty() + amount);
            playerDataMap.put(uniqueId, playerData);
            return;
        }

        plugin.getStorageManager().getPlayerStorage().modifyPlacedBounty(uniqueId, amount);

    }

    public void dropHead(Player player, Player killer, double amount) {
        ItemStack head = new ItemBuilder(Material.PLAYER_HEAD)
                .name(plugin.getConfigManager().getMain().getHeadDropItemName(player.getName(), killer.getName(), String.valueOf(amount)))
                .lore(plugin.getConfigManager().getMain().getHeadDropItemLore(player.getName(), killer.getName(), String.valueOf(amount)))
                .build();

        SkullMeta meta = (SkullMeta) head.getItemMeta();

        if (meta != null) {
            meta.setOwningPlayer(player); // works for online and offline players
            head.setItemMeta(meta);
        }

        player.getWorld().dropItem(player.getLocation(), head);
    }
}
