
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

public class BountyStorage {
    private final File storageFile;
    private final YamlConfiguration config;
    private final Logger logger;

    public BountyStorage(File dataFolder, Logger logger) {
        this.logger = logger;
        this.storageFile = new File(dataFolder, "bounties.yml");
        this.config = YamlConfiguration.loadConfiguration(storageFile);
        createFileIfNotExists();
    }

    private void createFileIfNotExists() {
        if (!storageFile.exists()) {
            try {
                storageFile.getParentFile().mkdirs();
                storageFile.createNewFile();
                save();
            } catch (IOException e) {
                logger.log(Level.SEVERE, "Could not create bounties.yml", e);
            }
        }
    }

    public void saveBounty(BountyData bounty) {
        String path = bounty.getTargetId().toString();
        config.set(path + ".placerId", bounty.getPlacerId().toString());
        config.set(path + ".amount", bounty.getAmount());
        config.set(path + ".placementTime", bounty.getPlacementTime());
        config.set(path + ".anonymous", bounty.isAnonymous());
        save();
    }

    public BountyData loadBounty(UUID targetId) {
        String path = targetId.toString();
        if (!config.contains(path)) {
            return null;
        }

        try {
            UUID placerId = UUID.fromString(config.getString(path + ".placerId"));
            double amount = config.getDouble(path + ".amount");
            boolean anonymous = config.getBoolean(path + ".anonymous");

            return new BountyData(targetId, placerId, amount, anonymous);
        } catch (IllegalArgumentException e) {
            logger.log(Level.WARNING, "Failed to load bounty for target " + targetId, e);
            return null;
        }
    }

    public Map<UUID, BountyData> loadAllBounties() {
        Map<UUID, BountyData> bounties = new HashMap<>();
        for (String uuidStr : config.getKeys(false)) {
            try {
                UUID targetId = UUID.fromString(uuidStr);
                BountyData bounty = loadBounty(targetId);
                if (bounty != null) {
                    bounties.put(targetId, bounty);
                }
            } catch (IllegalArgumentException e) {
                logger.log(Level.WARNING, "Invalid UUID in bounties.yml: " + uuidStr);
            }
        }
        return bounties;
    }

    public void removeBounty(UUID targetId) {
        config.set(targetId.toString(), null);
        save();
    }

    public void save() {
        try {
            config.save(storageFile);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Could not save bounties.yml", e);
        }
    }

    public void reload() {
        try {
            config.load(storageFile);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Could not reload bounties.yml", e);
        }
    }
}