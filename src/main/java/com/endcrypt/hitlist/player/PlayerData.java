package com.endcrypt.hitlist.player;

import com.endcrypt.hitlist.bounty.BountyData;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class PlayerData {
    private UUID uuid;
    BountyData bounty; // The UUID is a target of this bounty

    public PlayerData(UUID uuid) {
        this.uuid = uuid;
        this.bounty = null;
    }
}
