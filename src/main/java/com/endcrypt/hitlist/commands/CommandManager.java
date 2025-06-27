package com.endcrypt.hitlist.commands;

public class CommandManager {

    public CommandManager() {
        registerCommands();
    }

    public void registerCommands() {
        new BountyCommand();
    }
}
