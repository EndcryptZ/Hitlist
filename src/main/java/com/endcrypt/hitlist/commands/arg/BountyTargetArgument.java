package com.endcrypt.hitlist.commands.arg;

import com.endcrypt.hitlist.HitlistPlugin;
import dev.jorel.commandapi.arguments.ArgumentSuggestions;
import dev.jorel.commandapi.arguments.CustomArgument;
import dev.jorel.commandapi.arguments.StringArgument;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.concurrent.CompletableFuture;

public class BountyTargetArgument extends CustomArgument<OfflinePlayer, String> {
    private static final HitlistPlugin plugin = HitlistPlugin.instance;

    public BountyTargetArgument() {
        super(new StringArgument("target"), info -> {
            String targetName = info.currentInput();
            OfflinePlayer target = Bukkit.getOfflinePlayer(targetName);
            if(!plugin.getBountyManager().getActiveBounties().containsKey(target)) {
                throw CustomArgumentException.fromString("Bounty target not found: " + targetName);
            }
            return target;
        });

        this.replaceSuggestions(ArgumentSuggestions.stringCollectionAsync(info ->
            CompletableFuture.supplyAsync(() ->
                    plugin.getBountyManager().getActiveBounties().keySet().stream().map(OfflinePlayer::getName).toList()
            )
        ));
    }
}
