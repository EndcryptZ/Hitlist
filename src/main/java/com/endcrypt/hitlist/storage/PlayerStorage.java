package com.endcrypt.hitlist.storage;

import com.endcrypt.hitlist.bounty.BountyData;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PlayerStorage {
    private final File storageFile;
    private final YamlConfiguration config;
    private final Logger logger;
    private final Map<UUID, BountyData> playerBounties;

    public PlayerStorage(File dataFolder, Logger logger) {
        this.logger = logger;
        this.storageFile = new File(dataFolder, "players.yml");
        this.config = YamlConfiguration.loadConfiguration(storageFile);
        this.playerBounties = new HashMap<>();
        createFileIfNotExists();
        loadAllBounties();
    }

    private void createFileIfNotExists() {
        if (!storageFile.exists()) {
            try {
                storageFile.getParentFile().mkdirs();
                storageFile.createNewFile();
                save();
            } catch (IOException e) {
                logger.log(Level.SEVERE, "Could not create players.yml", e);
            }
        }
    }

    private void loadAllBounties() {
        playerBounties.clear();
        for (String uuidStr : config.getKeys(false)) {
            try {
                UUID playerId = UUID.fromString(uuidStr);
                BountyData bounty = loadPlayerBounty(playerId);
                if (bounty != null) {
                    playerBounties.put(playerId, bounty);
                }
            } catch (IllegalArgumentException e) {
                logger.log(Level.WARNING, "Invalid UUID in players.yml: " + uuidStr);
            }
        }
    }

    public void setPlayerBounty(UUID playerId, BountyData bounty) {
        playerBounties.put(playerId, bounty);
        savePlayerBounty(playerId, bounty);
    }

    public BountyData getPlayerBounty(UUID playerId) {
        return playerBounties.get(playerId);
    }

    public void removePlayerBounty(UUID playerId) {
        playerBounties.remove(playerId);
        config.set(playerId.toString(), null);
        save();
    }

    public boolean hasActiveBounty(UUID playerId) {
        return playerBounties.containsKey(playerId);
    }

    private void savePlayerBounty(UUID playerId, BountyData bounty) {
        String path = playerId.toString();
        config.set(path + ".placerId", bounty.getPlacerId().toString());
        config.set(path + ".amount", bounty.getAmount());
        config.set(path + ".placementTime", bounty.getPlacementTime());
        config.set(path + ".anonymous", bounty.isAnonymous());
        config.set(path + ".active", bounty.isActive());
        config.set(path + ".claimed", bounty.isClaimed());

        if (bounty.getClaimedBy() != null) {
            config.set(path + ".claimedBy", bounty.getClaimedBy().toString());
        }
        config.set(path + ".claimTime", bounty.getClaimTime());

        save();
    }

    private BountyData loadPlayerBounty(UUID playerId) {
        String path = playerId.toString();
        if (!config.contains(path)) {
            return null;
        }

        try {
            UUID placerId = UUID.fromString(config.getString(path + ".placerId"));
            double amount = config.getDouble(path + ".amount");
            boolean anonymous = config.getBoolean(path + ".anonymous");

            BountyData bounty = new BountyData(placerId, amount, anonymous);
            bounty.setActive(config.getBoolean(path + ".active"));
            bounty.setClaimed(config.getBoolean(path + ".claimed"));

            String claimedByStr = config.getString(path + ".claimedBy");
            if (claimedByStr != null) {
                bounty.setClaimedBy(UUID.fromString(claimedByStr));
            }

            bounty.setClaimTime(config.getLong(path + ".claimTime"));

            return bounty;
        } catch (IllegalArgumentException e) {
            logger.log(Level.WARNING, "Failed to load bounty for player " + playerId, e);
            return null;
        }
    }

    public void save() {
        try {
            config.save(storageFile);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Could not save players.yml", e);
        }
    }

    public void reload() {
        try {
            config.load(storageFile);
            loadAllBounties();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Could not reload players.yml", e);
        }
    }

    // Example YAML structure:
    /*
    550e8400-e29b-41d4-a716-446655440000:
        placerId: "uuid-string"
        amount: 1000.0
        placementTime: 1234567890
        anonymous: false
        active: true
        claimed: false
        claimedBy: "uuid-string"
        claimTime: 1234567890
    */
}