package com.endcrypt.hitlist.storage;

import com.endcrypt.hitlist.HitlistPlugin;
import lombok.Getter;

@Getter
public class StorageManager {

    private static final HitlistPlugin plugin = HitlistPlugin.instance;
    private final BountyStorage bountyStorage;
    private final String STORAGE_FOLDER = plugin.getDataFolder().getPath() + "/data";
    public StorageManager() {
        bountyStorage = new BountyStorage(new java.io.File(STORAGE_FOLDER), plugin.getLogger());
    }
}
