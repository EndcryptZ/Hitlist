package com.endcrypt.hitlist.commands;

import com.endcrypt.hitlist.HitlistPlugin;
import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.DoubleArgument;
import dev.jorel.commandapi.arguments.EntitySelectorArgument;
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
                .executes(this::bountyGUI)
                .withSubcommand(new CommandAPICommand("place")
                        .withArguments(new EntitySelectorArgument.OnePlayer("target"))
                        .withArguments(new DoubleArgument("amount"))
                        .executes(this::place))
                .register();
    }

    private void bountyGUI(CommandSender commandSender, CommandArguments args) {
        commandSender.sendMessage("This opens the Bounty GUI");
    }

    private void place(CommandSender commandSender, CommandArguments args) {
        Player target = args.getUnchecked("target");
        double amount = args.getUnchecked("amount");
        String message;
        if(target == commandSender) {
            message = plugin.getConfigManager().getMessages().getErrorSelfBounty();
            plugin.sendMessage((Player) commandSender, message);
            return;
        }

        assert target != null;
        plugin.getBountyManager().placeBounty(target, (Player) commandSender, amount);
    }
}
