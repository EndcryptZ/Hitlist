package com.endcrypt.hitlist.bounty;

import com.endcrypt.hitlist.HitlistPlugin;
import com.endcrypt.hitlist.utils.TimeUtils;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class BountyData {
    private final UUID placerId;
    private double amount;
    private final long placementTime;
    private final boolean anonymous;
    private boolean active;
    private boolean claimed;
    private UUID claimedBy;
    private long claimTime;

    public BountyData(UUID placerId, double amount, boolean anonymous) {
        this.placerId = placerId;
        this.amount = amount;
        this.placementTime = System.currentTimeMillis();
        this.anonymous = anonymous;
        this.active = true;
        this.claimed = false;
        this.claimedBy = null;
        this.claimTime = 0;
    }
}
