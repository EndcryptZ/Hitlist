package com.endcrypt.hitlist.gui;

import lombok.Getter;

@Getter
public enum SortType {

    ALPHABETICAL("Alphabetical"),
    HIGHEST_VALUE("Highest Value"),
    LOWEST_VALUE("Lowest Value");

    private final String name;
    SortType(String name) {
        this.name = name;
    }
}
