package com.endcrypt.hitlist.commands;

import com.endcrypt.hitlist.HitlistPlugin;
import com.endcrypt.hitlist.bounty.BountyData;
import com.endcrypt.hitlist.commands.arg.BountyTargetArgument;
import com.endcrypt.hitlist.utils.ColorUtils;
import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.DoubleArgument;
import dev.jorel.commandapi.arguments.EntitySelectorArgument;
import dev.jorel.commandapi.arguments.IntegerArgument;
import dev.jorel.commandapi.executors.CommandArguments;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
                        .executesPlayer(this::place))

                .withSubcommand(new CommandAPICommand("list")
                        .withArguments(new IntegerArgument("page", 1).setOptional(true))
                .executes(this::list))

                .withSubcommand(new CommandAPICommand("edit")
                        .withSubcommand(new CommandAPICommand("remove")
                                .withArguments(new BountyTargetArgument())
                                .executesPlayer(this::remove))
                        .withSubcommand(new CommandAPICommand("lower")
                                .withArguments(new BountyTargetArgument())
                                .withArguments(new DoubleArgument("amount"))
                                .executesPlayer(this::lower)))
                .register();
    }

    private void bountyGUI(CommandSender commandSender, CommandArguments args) {
        plugin.getGuiManager().getMainBountyGUI().open((Player) commandSender);
    }

    private void place(CommandSender commandSender, CommandArguments args) {
        Player target = args.getUnchecked("target");
        double amount = args.getUnchecked("amount");


        assert target != null;
        plugin.getBountyManager().placeBounty(target, (Player) commandSender, amount);
    }

    private void list(CommandSender commandSender, CommandArguments args) {
        int page = (int) args.getOptional("page").orElse(1);
        Map<UUID, BountyData> bounties = plugin.getBountyManager().getActiveBounties();

        // Calculate total pages
        int totalBounties = bounties.size();
        int totalPages = (int) Math.ceil(totalBounties / 10.0);

        if (totalBounties == 0) {
            commandSender.sendMessage("§cThere are no active bounties.");
            return;
        }

        if (page > totalPages) {
            commandSender.sendMessage("§cPage " + page + " does not exist. Total pages: " + totalPages);
            return;
        }

        // Calculate start and end indices for the current page
        int startIndex = (page - 1) * 10;
        int endIndex = Math.min(startIndex + 10, totalBounties);

        commandSender.sendMessage("§6Active Bounties (Page " + page + "/" + totalPages + "):");
        commandSender.sendMessage("§7----------------------------------------");

        // Convert map entries to list for easier pagination
        List<Map.Entry<UUID, BountyData>> bountyList = new ArrayList<>(bounties.entrySet());

        for (int i = startIndex; i < endIndex; i++) {
            Map.Entry<UUID, BountyData> entry = bountyList.get(i);
            UUID target = entry.getKey();
            BountyData bounty = entry.getValue();

            String placerName = bounty.isAnonymous() ? "Anonymous" :
                    Bukkit.getOfflinePlayer(bounty.getPlacerId()).getName();

            commandSender.sendMessage(ColorUtils.color("<yellow><target> <gray>- <gold><amount> <gray>by <placer>",
                    Placeholder.parsed("target", Bukkit.getOfflinePlayer(target).getName()),
                    Placeholder.parsed("amount", String.valueOf(bounty.getAmount())),
                    Placeholder.parsed("placer", placerName)));
        }

        commandSender.sendMessage("§7----------------------------------------");
        if (page < totalPages) {
            commandSender.sendMessage("§7Use §e/bounty list " + (page + 1) + " §7to see the next page");
        }
    }

    private void remove(CommandSender commandSender, CommandArguments args) {
        OfflinePlayer target = args.getUnchecked("target");
        plugin.getBountyManager().removeBounty(target, (Player) commandSender);
    }

    private void lower(CommandSender commandSender, CommandArguments args) {
        OfflinePlayer target = args.getUnchecked("target");
        double amount = args.getUnchecked("amount");

        plugin.getBountyManager().lowerBounty(target, (Player) commandSender, amount);

    }
}
