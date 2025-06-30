package com.endcrypt.hitlist;

import com.endcrypt.hitlist.bounty.BountyManager;
import com.endcrypt.hitlist.commands.CommandManager;
import com.endcrypt.hitlist.config.ConfigManager;
import com.endcrypt.hitlist.permissions.PermissionManager;
import com.endcrypt.hitlist.storage.StorageManager;
import com.samjakob.spigui.SpiGUI;
import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIBukkitConfig;
import lombok.Getter;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class HitlistPlugin extends JavaPlugin {

    public static HitlistPlugin instance;


    private SpiGUI spiGUI;
    private Economy econ;

    // Instances
    BountyManager bountyManager;
    ConfigManager configManager;
    CommandManager commandManager;
    StorageManager storageManager;
    PermissionManager permissionManager;

    @Override
    public void onLoad() {
        instance = this;
        CommandAPI.onLoad(new CommandAPIBukkitConfig(this)
                .shouldHookPaperReload(true)
        );
    }

    @Override
    public void onEnable() {
        CommandAPI.onEnable();
        this.initializeInstances();
        this.initializeEconomy();

    }

    @Override
    public void onDisable() {
        CommandAPI.onDisable();
        // Plugin shutdown logic
    }


    private void initializeInstances() {

        spiGUI = new SpiGUI(this);
        storageManager = new StorageManager();
        bountyManager = new BountyManager();
        configManager = new ConfigManager();
        commandManager = new CommandManager();
        permissionManager = new PermissionManager();


    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    private void initializeEconomy() {
        if (!setupEconomy() ) {
            getLogger().severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
        }
    }

    public void sendMessage(Player recipient, String message) {
        recipient.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(message));
    }
}
