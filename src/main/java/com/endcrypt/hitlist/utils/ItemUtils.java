package com.endcrypt.hitlist.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class ItemUtils {

    public static void itemMessage(JavaPlugin plugin, ItemStack item, String defaultItemName, String message, List<Component> defaultLore, List<Component> loreMessage) {
        ItemMeta itemMeta = item.getItemMeta();

        assert itemMeta != null;
        List<Component> veryDefaultLore = itemMeta.lore();

        if(loreMessage != null) {
            itemMeta.lore(loreMessage);
        }

        itemMeta.customName(LegacyComponentSerializer.legacyAmpersand().deserialize(message));

        item.setItemMeta(itemMeta);


        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            itemMeta.displayName(LegacyComponentSerializer.legacyAmpersand().deserialize(defaultItemName));

            if(defaultLore != null) {
                itemMeta.lore(defaultLore);
            } else {
                itemMeta.lore(veryDefaultLore);
            }

            item.setItemMeta(itemMeta);
        }, 20L);
    }

}
