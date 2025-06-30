package com.endcrypt.hitlist.permissions;

import lombok.Getter;

@Getter
public enum PermissionsEnum {

    PERMISSION_BOUNTY_EDIT_OTHERS("hitlist.bounty.edit.others"),
    PERMISSION_BOUNTY_PLACE_LIMIT("hitlist.bounty.place.limit");


    private final String permission;
    PermissionsEnum(String permission) {
        this.permission = permission;
    }
}
