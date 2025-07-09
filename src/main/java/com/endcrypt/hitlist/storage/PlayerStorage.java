package com.endcrypt.hitlist.storage;

import com.endcrypt.hitlist.player.PlayerData;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

public class PlayerStorage {
    private final File storageFile;
    private final YamlConfiguration config;
    private final Logger logger;

    public PlayerStorage(File dataFolder, Logger logger) {
        this.logger = logger;
        this.storageFile = new File(dataFolder, "players.yml");
        this.config = YamlConfiguration.loadConfiguration(storageFile);
        createFileIfNotExists();
    }

    private void createFileIfNotExists() {
        if (!storageFile.exists()) {
            try {
                storageFile.getParentFile().mkdirs();
                storageFile.createNewFile();
                save();
            } catch (Exception e) {
                logger.severe("Could not create players.yml");
            }
        }
    }

    public void savePlayer(PlayerData playerData) {
        String path = playerData.getUniqueId().toString();
        config.set(path + ".uniqueId", playerData.getUniqueId().toString());
        config.set(path + ".isOptedOut", playerData.isOptedOut());
        config.set(path + ".totalClaimedBounty", playerData.getTotalClaimedBounty());
        config.set(path + ".totalPlacedBounty", playerData.getTotalPlacedBounty());
        config.set(path + ".optCooldown", playerData.getOptCooldown());
        save();
    }

    public void modifyPlacedBounty(UUID uniqueId, double amount) {
        String path = uniqueId.toString();
        if (!config.contains(path)) {
            return;
        }

        config.set(path + ".totalPlacedBounty", config.getDouble(path + ".totalPlacedBounty") + amount);
        save();
    }

    public PlayerData loadPlayer(UUID uniqueId) {
        String path = uniqueId.toString();
        PlayerData playerData = new PlayerData(uniqueId);
        if (!config.contains(path)) {
            return playerData;
        }

        try {
            boolean isOptedOut = config.getBoolean(path + ".isOptedOut");
            double totalClaimedBounty = config.getDouble(path + ".totalClaimedBounty");
            double totalPlacedBounty = config.getDouble(path + ".totalPlacedBounty");
            long optCooldown = config.getLong(path + ".optCooldown");

            playerData.setOptedOut(isOptedOut);
            playerData.setTotalClaimedBounty(totalClaimedBounty);
            playerData.setTotalPlacedBounty(totalPlacedBounty);
            playerData.setOptCooldown(optCooldown);

            return playerData;
        } catch (IllegalArgumentException e) {
            logger.severe("Failed to load player data for " + uniqueId);
            return null;
        }
    }

    public List<PlayerData> loadAllPlayers() {
        List<PlayerData> players = config.getKeys(false).stream()
                .map(key -> loadPlayer(UUID.fromString(key)))
                .filter(playerData -> playerData != null)
                .toList();
        return players;
    }

    public void save() {
        try {
            config.save(storageFile);
        } catch (Exception e) {
            logger.severe("Could not save players.yml");
        }
    }

    public void reload() {
        try {
            config.load(storageFile);
        } catch (Exception e) {
            logger.severe("Could not reload players.yml");
        }
    }
}
