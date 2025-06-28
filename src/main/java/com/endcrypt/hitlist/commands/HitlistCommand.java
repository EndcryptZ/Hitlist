package com.endcrypt.hitlist.commands;

import com.endcrypt.hitlist.HitlistPlugin;
import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.executors.CommandArguments;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class HitlistCommand {

    private final static HitlistPlugin plugin = HitlistPlugin.instance;
    public HitlistCommand() {
        registerCommands();
    }

    private void registerCommands() {
        new CommandAPICommand("hitlistreload")
                .withPermission("hitlist.cmd.reload")
                .executes(this::reload)
                .register();
    }


    private void reload(CommandSender commandSender, CommandArguments args) {
        plugin.getConfigManager().reloadConfigs();
        if(commandSender instanceof Player) commandSender.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(plugin.getConfigManager().getMessages().getSystemConfigReload()));
        if(commandSender instanceof ConsoleCommandSender) commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfigManager().getMessages().getSystemConfigReload()));
    }
}
