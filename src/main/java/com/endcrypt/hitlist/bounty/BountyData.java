package com.endcrypt.hitlist.bounty;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.UUID;

/**
 * Represents the data structure for an active bounty.
 * Stores information about who placed it, who it's on, the amount,
 * whether it was placed anonymously, and the time it was placed.
 */
@Getter
@Setter
public class BountyData {
    private final UUID targetId;
    private final UUID placerId;
    private final Map<UUID, Double> placersMap;
    private double amount;
    private long placementTime;
    private final boolean anonymous;

    /**
     * Constructs a new BountyData instance.
     *
     * @param targetId     The UUID of the player being targeted
     * @param placerId     The UUID of the player placing the bounty
     * @param placersMap   The map of placer UUIDs to their bounty amounts on the target
     * @param amount       The total bounty amount on the target
     * @param anonymous    Whether the bounty was placed anonymously
     */
    public BountyData(UUID targetId, UUID placerId, Map<UUID, Double> placersMap, double amount, boolean anonymous) {
        this.targetId = targetId;
        this.placerId = placerId;
        this.placersMap = placersMap;
        this.amount = amount;
        this.placementTime = System.currentTimeMillis(); // Set current time as placement timestamp
        this.anonymous = anonymous;
    }
}
