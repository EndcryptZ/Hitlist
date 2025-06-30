package com.endcrypt.hitlist.permissions;

import com.endcrypt.hitlist.HitlistPlugin;
import org.bukkit.permissions.Permission;

import java.util.Arrays;

public class PermissionManager {

    private final HitlistPlugin plugin = HitlistPlugin.instance;
    public PermissionManager() {
        Arrays.stream(PermissionsEnum.values())
                .forEach(permission -> {
                    if(plugin.getServer().getPluginManager().getPermission(permission.getPermission()) == null) {
                        plugin.getServer().getPluginManager()
                                .addPermission(new Permission(permission.getPermission()));
                    }
                });
    }
}
