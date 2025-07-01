package com.endcrypt.hitlist.utils;

import com.endcrypt.hitlist.gui.FillType;
import com.samjakob.spigui.buttons.SGButton;
import com.samjakob.spigui.menu.SGMenu;

import java.util.HashSet;
import java.util.Set;

public class SlotUtils {

    public static Set<Integer> getBorderSlots(int rows) {
        Set<Integer> slots = new HashSet<>();
        int size = rows * 9;

        for (int i = 0; i < size; i++) {
            int row = i / 9;
            int col = i % 9;

            if (row == 0 || row == rows - 1 || col == 0 || col == 8) {
                slots.add(i);
            }
        }

        return slots;
    }

    public static Set<Integer> getAllSlots(int rows) {
        Set<Integer> slots = new HashSet<>();
        int size = rows * 9;

        for (int i = 0; i < size; i++) {
            slots.add(i);
        }

        return slots;
    }

}
