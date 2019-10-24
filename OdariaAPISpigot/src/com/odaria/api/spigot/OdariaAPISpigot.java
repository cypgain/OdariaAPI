package com.odaria.api.spigot;

import com.odaria.api.commons.data.management.redis.RedisAccess;
import com.odaria.api.spigot.listeners.PlayerJoinListener;
import com.odaria.api.spigot.utils.ConsoleManager;
import org.bukkit.plugin.java.JavaPlugin;

public class OdariaAPISpigot extends JavaPlugin {

    public static OdariaAPISpigot INSTANCE;

    @Override
    public void onEnable() {
        INSTANCE = this;

        ConsoleManager.infoMessage("Redis loading...");
        RedisAccess.init();
        ConsoleManager.successMessage("Redis loaded");
    }

    @Override
    public void onDisable() {
        ConsoleManager.infoMessage("Redis closing...");
        RedisAccess.close();
        ConsoleManager.successMessage("Redis closed");
    }

    public void loadListeners() {
        this.getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
    }

}
