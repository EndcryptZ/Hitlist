package com.endcrypt.hitlist.gui;

import com.endcrypt.hitlist.HitlistPlugin;
import com.endcrypt.hitlist.gui.bounty.BountyAmountAnvilGUI;
import com.endcrypt.hitlist.gui.bounty.MainBountyGUI;
import com.endcrypt.hitlist.gui.bounty.PlaceBountyGUI;
import com.endcrypt.hitlist.gui.bounty.SearchAnvilGUI;
import com.endcrypt.hitlist.utils.SlotUtils;
import com.samjakob.spigui.buttons.SGButton;
import com.samjakob.spigui.menu.SGMenu;
import lombok.Getter;

@Getter
public class GUIManager {

    private final HitlistPlugin plugin = HitlistPlugin.instance;

    private final MainBountyGUI mainBountyGUI;
    private final PlaceBountyGUI placeBountyGUI;
    private final SearchAnvilGUI searchAnvilGUI;
    private final BountyAmountAnvilGUI bountyAmountAnvilGUI;
    public GUIManager() {
        mainBountyGUI = new MainBountyGUI();
        placeBountyGUI = new PlaceBountyGUI();
        searchAnvilGUI = new SearchAnvilGUI();
        bountyAmountAnvilGUI = new BountyAmountAnvilGUI();
    }

    public void handleFill(FillType fillType, SGMenu gui, SGButton fillerButton, int rows) {
        if(fillType.equals(FillType.ALL)) {
            for (Integer slot : SlotUtils.getAllSlots(rows)) {
                gui.setButton(slot, fillerButton);
            }
        }

        if(fillType.equals(FillType.BORDER)) {
            for (Integer slot : SlotUtils.getBorderSlots(rows)) {
                gui.setButton(slot, fillerButton);
            }
        }
    }
}
