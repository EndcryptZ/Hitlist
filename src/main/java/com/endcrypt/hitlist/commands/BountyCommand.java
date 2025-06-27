package com.endcrypt.hitlist.commands;

import com.endcrypt.hitlist.HitlistPlugin;
import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.DoubleArgument;
import dev.jorel.commandapi.arguments.PlayerArgument;
import dev.jorel.commandapi.executors.CommandArguments;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BountyCommand {

    private final static HitlistPlugin plugin = HitlistPlugin.instance;
    public BountyCommand() {
        registerCommands();
    }

    private void registerCommands() {
        new CommandAPICommand("bounty")
                .withSubcommand(new CommandAPICommand("place")
                        .withArguments(new PlayerArgument("target"))
                        .withArguments(new DoubleArgument("amount"))
                        .executes(this::place))
                .register();
    }

    private void place(CommandSender commandSender, CommandArguments args) {
        Player target = args.getUnchecked("target");
        double amount = args.getUnchecked("amount");
        String message;
        if(target == commandSender) {
            message = plugin.getConfigManager().getMessages().getSelfBounty();
            plugin.sendMessage((Player) commandSender, message);
            return;
        }

        assert target != null;
        message = plugin.getConfigManager().getMessages().getBountyPlaced(target.getName(), String.valueOf(amount));
        plugin.getBountyManager().placeBounty(target, (Player) commandSender, amount);
        plugin.sendMessage((Player) commandSender, message);
    }
}
