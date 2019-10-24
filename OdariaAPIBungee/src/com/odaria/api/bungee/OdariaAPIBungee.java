package com.odaria.api.bungee;

import com.odaria.api.bungee.data.management.sql.DatabaseManager;
import com.odaria.api.bungee.listeners.ProxyQuitListener;
import com.odaria.api.bungee.listeners.RedisPubSubListener;
import com.odaria.api.bungee.utils.ConsoleManager;
import com.odaria.api.bungee.listeners.ProxyJoinListener;
import com.odaria.api.commons.data.management.redis.RedisAccess;
import net.md_5.bungee.api.plugin.Plugin;
import org.redisson.api.RTopic;
import org.redisson.api.listener.MessageListener;
import org.redisson.client.codec.LongCodec;

public class OdariaAPIBungee extends Plugin {

    public static OdariaAPIBungee INSTANCE;

    @Override
    public void onEnable() {
        INSTANCE = this;

        ConsoleManager.infoMessage("MySQL loading...");
        DatabaseManager.initAllDatabaseConnections();
        ConsoleManager.successMessage("MySQL loaded");

        ConsoleManager.infoMessage("Redis loading...");
        RedisAccess.init();
        ConsoleManager.successMessage("Redis loaded");

        ConsoleManager.infoMessage("Listeners loading...");
        loadListeners();
        ConsoleManager.successMessage("Listeners loaded");

        ConsoleManager.infoMessage("Commands loading...");
        loadCommands();
        ConsoleManager.successMessage("Commands loaded");
    }

    @Override
    public void onDisable() {
        ConsoleManager.infoMessage("MySQL closing...");
        DatabaseManager.closeAllDatabaseConnections();
        ConsoleManager.successMessage("MySQL closed");

        ConsoleManager.infoMessage("Redis closing...");
        RedisAccess.close();
        ConsoleManager.successMessage("Redis closed");
    }

    public void loadListeners() {
        this.getProxy().getPluginManager().registerListener(this, new ProxyJoinListener());
        this.getProxy().getPluginManager().registerListener(this, new ProxyQuitListener());

        RTopic topic = RedisAccess.INSTANCE.getRedissonClient().getTopic(RedisAccess.CHANNEL);
        topic.addListener((MessageListener<String>) (channel, message) -> new RedisPubSubListener().onRedisPubSubListener(channel, message));
    }

    public void loadCommands() {

    }

}
