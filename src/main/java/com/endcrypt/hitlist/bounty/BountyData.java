package com.endcrypt.hitlist.bounty;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * Represents the data structure for an active bounty.
 * Stores information about who placed it, who it's on, the amount,
 * whether it was placed anonymously, and the time it was placed.
 */
@Getter
@Setter
public class BountyData {
    // The UUID of the player who has the bounty on their head
    private final UUID targetId;

    // The UUID of the player who placed the bounty
    private final UUID placerId;

    // The specific amount of money this placer contributed to the bounty
    private double placedAmount;

    // The total bounty amount on the target (may include contributions from others)
    private double amount;

    // The system timestamp of when the bounty was placed (milliseconds)
    private final long placementTime;

    // Whether the bounty was placed anonymously
    private final boolean anonymous;

    /**
     * Constructs a new BountyData instance.
     *
     * @param targetId     The UUID of the player being targeted
     * @param placerId     The UUID of the player placing the bounty
     * @param placedAmount The amount of money the placer is contributing
     * @param amount       The total bounty amount on the target
     * @param anonymous    Whether the bounty was placed anonymously
     */
    public BountyData(UUID targetId, UUID placerId, double placedAmount, double amount, boolean anonymous) {
        this.targetId = targetId;
        this.placerId = placerId;
        this.placedAmount = placedAmount;
        this.amount = amount;
        this.placementTime = System.currentTimeMillis(); // Set current time as placement timestamp
        this.anonymous = anonymous;
    }
}
