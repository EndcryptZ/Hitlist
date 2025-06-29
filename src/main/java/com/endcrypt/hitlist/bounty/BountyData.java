package com.endcrypt.hitlist.bounty;

import com.endcrypt.hitlist.HitlistPlugin;
import com.endcrypt.hitlist.utils.TimeUtils;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class BountyData {
    private final UUID targetId;
    private final UUID placerId;
    private double amount;
    private final long placementTime;
    private final boolean anonymous;

    public BountyData(UUID targetId, UUID placerId, double amount, boolean anonymous) {
        this.targetId = targetId;
        this.placerId = placerId;
        this.amount = amount;
        this.placementTime = System.currentTimeMillis();
        this.anonymous = anonymous;
    }
}
