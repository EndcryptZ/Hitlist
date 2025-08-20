package com.endcrypt.hitlist.player;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class PlayerData {

    UUID uniqueId;
    boolean isOptedOut = false;
    double totalClaimedBounty = 0;
    double totalPlacedBounty = 0;
    long optCooldown = 0;
    long lastCooldownUpdate = 0;

    public PlayerData(UUID uniqueId) {
        this.uniqueId = uniqueId;
    }
}
