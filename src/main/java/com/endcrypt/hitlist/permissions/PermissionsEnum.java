package com.endcrypt.hitlist.permissions;

import lombok.Getter;

@Getter
public enum PermissionsEnum {

    PERMISSION_BOUNTY_REMOVE_OTHERS("hitlist.bounty-remove-others");


    private final String permission;
    PermissionsEnum(String permission) {
        this.permission = permission;
    }
}
