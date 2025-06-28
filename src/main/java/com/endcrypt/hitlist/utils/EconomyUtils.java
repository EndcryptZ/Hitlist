package com.endcrypt.hitlist.utils;

import com.endcrypt.hitlist.HitlistPlugin;
import org.bukkit.entity.Player;

public class EconomyUtils {

    private static final HitlistPlugin plugin = HitlistPlugin.instance;
    public static boolean hasEnoughMoney(Player player, double amount) {
        return plugin.getEcon().getBalance(player) >= amount;
    }

    public static double getBalance(Player player) {
        return plugin.getEcon().getBalance(player);
    }

    public static void withdraw(Player player, double amount) {
        plugin.getEcon().withdrawPlayer(player, amount);
    }

    public static void deposit(Player player, double amount) {
        plugin.getEcon().depositPlayer(player, amount);
    }
}
