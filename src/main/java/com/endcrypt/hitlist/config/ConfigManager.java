package com.endcrypt.hitlist.config;

import com.endcrypt.hitlist.HitlistPlugin;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import java.io.IOException;

@Getter
public class ConfigManager {
    private final HitlistPlugin plugin;
    private FileConfiguration config;
    private FileConfiguration messagesConfig;
    private File configFile;
    private File messagesFile;
    private MainConfig mainConfig;
    private MessagesConfig messages;

    public ConfigManager() {
        this.plugin = HitlistPlugin.instance;
        loadConfigs();
    }

    public void loadConfigs() {
        loadDefaultConfigs();
        loadConfig();
        loadMessages();
        mainConfig = new MainConfig(config);
        messages = new MessagesConfig(messagesConfig);
    }

    private void loadDefaultConfigs() {
        // Save default configs if they don't exist
        plugin.saveDefaultConfig();
        if (!new File(plugin.getDataFolder(), "messages.yml").exists()) {
            plugin.saveResource("messages.yml", false);
        }
    }

    private void loadConfig() {
        configFile = new File(plugin.getDataFolder(), "config.yml");
        config = YamlConfiguration.loadConfiguration(configFile);
    }

    private void loadMessages() {
        messagesFile = new File(plugin.getDataFolder(), "messages.yml");
        messagesConfig = YamlConfiguration.loadConfiguration(messagesFile);
    }

    public void reloadConfigs() {
        loadConfig();
        loadMessages();
        mainConfig = new MainConfig(config);
        messages = new MessagesConfig(messagesConfig);
    }

    public void saveConfig() {
        try {
            config.save(configFile);
        } catch (IOException e) {
            plugin.getLogger().severe("Could not save config.yml!");
            e.printStackTrace();
        }
    }

    public void saveMessages() {
        try {
            messagesConfig.save(messagesFile);
        } catch (IOException e) {
            plugin.getLogger().severe("Could not save messages.yml!");
            e.printStackTrace();
        }
    }

    // Config getters with type safety
    public double getDouble(ConfigEnum path) {
        if (path.isMessage()) {
            throw new IllegalArgumentException("Tried to access message config as main config: " + path);
        }
        return config.getDouble(path.getPath());
    }

    public boolean getBoolean(ConfigEnum path) {
        if (path.isMessage()) {
            throw new IllegalArgumentException("Tried to access message config as main config: " + path);
        }
        return config.getBoolean(path.getPath());
    }

    public String getString(ConfigEnum path) {
        if (path.isMessage()) {
            throw new IllegalArgumentException("Tried to access message config as main config: " + path);
        }
        return config.getString(path.getPath());
    }

    public int getInt(ConfigEnum path) {
        if (path.isMessage()) {
            throw new IllegalArgumentException("Tried to access message config as main config: " + path);
        }
        return config.getInt(path.getPath());
    }
}