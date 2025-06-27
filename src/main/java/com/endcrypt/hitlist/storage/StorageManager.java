package com.endcrypt.hitlist.storage;

import com.endcrypt.hitlist.HitlistPlugin;

public class StorageManager {

    private static final HitlistPlugin plugin = HitlistPlugin.instance;
    private final String STORAGE_FOLDER = plugin.getDataFolder().getPath() + "/data";
    public StorageManager() {

    }
}
