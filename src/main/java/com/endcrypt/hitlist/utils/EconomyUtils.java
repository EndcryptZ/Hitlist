package com.endcrypt.hitlist.utils;

import com.endcrypt.hitlist.HitlistPlugin;
import org.bukkit.OfflinePlayer;

public class EconomyUtils {

    private static final HitlistPlugin plugin = HitlistPlugin.instance;
    public static boolean hasEnoughMoney(OfflinePlayer player, double amount) {
        return plugin.getEcon().getBalance(player) >= amount;
    }

    public static double getBalance(OfflinePlayer player) {
        return plugin.getEcon().getBalance(player);
    }

    public static void withdraw(OfflinePlayer player, double amount) {
        plugin.getEcon().withdrawPlayer(player, amount);
    }

    public static void deposit(OfflinePlayer player, double amount) {
        plugin.getEcon().depositPlayer(player, amount);
    }
}
