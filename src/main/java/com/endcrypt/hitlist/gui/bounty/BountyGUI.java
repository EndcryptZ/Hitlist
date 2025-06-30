package com.endcrypt.hitlist.gui.bounty;

import com.endcrypt.hitlist.HitlistPlugin;
import com.samjakob.spigui.menu.SGMenu;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class BountyGUI {

    private final HitlistPlugin plugin = HitlistPlugin.instance;
    public BountyGUI() {
    }

    public void open(Player player) {
        player.openInventory(getInventory());
    }

    private Inventory getInventory() {
        SGMenu gui = plugin.getSpiGUI().create("Bounty GUI", 4, "Bounty GUI");

        return gui.getInventory();
    }


}
