package com.odaria.api.spigot;

import com.odaria.api.commons.data.management.redis.RedisAccess;
import com.odaria.api.commons.servers.ServerState;
import com.odaria.api.spigot.commands.TestCommand;
import com.odaria.api.spigot.guimanager.GUIManager;
import com.odaria.api.spigot.listeners.PlayerChatListener;
import com.odaria.api.spigot.listeners.PlayerJoinListener;
import com.odaria.api.spigot.senders.server.ChangeServerStateSender;
import com.odaria.api.spigot.utils.ConsoleManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class OdariaAPISpigot extends JavaPlugin {

    public static OdariaAPISpigot INSTANCE;

    @Override
    public void onEnable() {
        INSTANCE = this;

        ConsoleManager.infoMessage("Redis loading...");
        RedisAccess.init();
        ConsoleManager.successMessage("Redis loaded");

        ConsoleManager.infoMessage("Listeners loading...");
        loadListeners();
        ConsoleManager.successMessage("Listeners loaded");

        ConsoleManager.infoMessage("Commands loading...");
        loadCommands();
        ConsoleManager.successMessage("Commands loaded");

        GUIManager guiManager = new GUIManager();
        Bukkit.getPluginManager().registerEvents(guiManager, this);

        ChangeServerStateSender.Action(Bukkit.getPort(), ServerState.OPEN);
    }

    @Override
    public void onDisable() {
        ConsoleManager.infoMessage("Redis closing...");
        RedisAccess.close();
        ConsoleManager.successMessage("Redis closed");
        ChangeServerStateSender.Action(Bukkit.getPort(), ServerState.SHUTDOWN);
    }

    private void loadListeners() {
        this.getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerChatListener(), this);
    }

    private void loadCommands() {
        this.getCommand("testspigot").setExecutor(new TestCommand());
    }

}
