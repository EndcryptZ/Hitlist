package com.endcrypt.hitlist.config;

import com.endcrypt.hitlist.HitlistPlugin;
import com.endcrypt.hitlist.config.gui.GUIConfig;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import java.io.IOException;

@Getter
public class ConfigManager {
    private final HitlistPlugin plugin;
    private FileConfiguration mainConfig;
    private FileConfiguration messagesConfig;
    private FileConfiguration guiConfig;
    private File configFile;
    private File messagesFile;
    private File guiFile;
    private MainConfig main;
    private MessagesConfig messages;
    private GUIConfig gui;


    public ConfigManager() {
        this.plugin = HitlistPlugin.instance;
        loadConfigs();
    }

    public void loadConfigs() {
        loadDefaultConfigs();
        loadConfig();
        loadMessages();
        loadGUI();
        main = new MainConfig(mainConfig);
        messages = new MessagesConfig(messagesConfig);
        gui = new GUIConfig(guiConfig);
    }

    private void loadDefaultConfigs() {
        // Save default configs if they don't exist
        plugin.saveDefaultConfig();
        if (!new File(plugin.getDataFolder(), "messages.yml").exists()) {
            plugin.saveResource("messages.yml", false);
        }
        if (!new File(plugin.getDataFolder(), "gui.yml").exists()) {
            plugin.saveResource("gui.yml", false);
        }
    }

    private void loadConfig() {
        configFile = new File(plugin.getDataFolder(), "config.yml");
        mainConfig = YamlConfiguration.loadConfiguration(configFile);
    }

    private void loadMessages() {
        messagesFile = new File(plugin.getDataFolder(), "messages.yml");
        messagesConfig = YamlConfiguration.loadConfiguration(messagesFile);
    }

    private void loadGUI() {
        guiFile = new File(plugin.getDataFolder(), "gui.yml");
        guiConfig = YamlConfiguration.loadConfiguration(guiFile);
    }

    public void reloadConfigs() {
        loadConfig();
        loadMessages();
        loadGUI();
        main = new MainConfig(mainConfig);
        messages = new MessagesConfig(messagesConfig);
        gui = new GUIConfig(guiConfig);
        plugin.getLogger().info("Reloaded Hitlist configs!");
    }

    public void saveConfig() {
        try {
            mainConfig.save(configFile);
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

    public void saveGUI() {
        try {
            guiConfig.save(guiFile);
        } catch (IOException e) {
            plugin.getLogger().severe("Could not save gui.yml!");
        }
    }

    // Config getters with type safety
    public double getDouble(ConfigEnum path) {
        if (path.isMessage()) {
            throw new IllegalArgumentException("Tried to access message config as main config: " + path);
        }
        return mainConfig.getDouble(path.getPath());
    }

    public boolean getBoolean(ConfigEnum path) {
        if (path.isMessage()) {
            throw new IllegalArgumentException("Tried to access message config as main config: " + path);
        }
        return mainConfig.getBoolean(path.getPath());
    }

    public String getString(ConfigEnum path) {
        if (path.isMessage()) {
            throw new IllegalArgumentException("Tried to access message config as main config: " + path);
        }
        return mainConfig.getString(path.getPath());
    }

    public int getInt(ConfigEnum path) {
        if (path.isMessage()) {
            throw new IllegalArgumentException("Tried to access message config as main config: " + path);
        }
        return mainConfig.getInt(path.getPath());
    }
}