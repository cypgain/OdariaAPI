package com.odaria.api.bungee;

import com.google.common.collect.Maps;
import com.odaria.api.bungee.commands.*;
import com.odaria.api.bungee.core.RanksManager;
import com.odaria.api.bungee.data.management.exceptions.AccountNotFoundException;
import com.odaria.api.bungee.data.management.sql.DatabaseManager;
import com.odaria.api.bungee.listeners.game.ProxyQuitListener;
import com.odaria.api.bungee.listeners.redis.RedisPubSubListener;
import com.odaria.api.bungee.servers.ServersManager;
import com.odaria.api.bungee.utils.ConsoleManager;
import com.odaria.api.bungee.listeners.game.ProxyJoinListener;
import com.odaria.api.commons.data.management.redis.RedisAccess;
import net.md_5.bungee.api.plugin.Plugin;
import org.redisson.api.RTopic;
import org.redisson.api.listener.MessageListener;

import java.sql.SQLException;
import java.util.Map;

public class OdariaAPIBungee extends Plugin {

    public static final String IP = "127.0.0.1";

    public static OdariaAPIBungee INSTANCE;

    private Map<String, String> partyInvitation = Maps.newHashMap();

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

        new ServersManager();
        new RanksManager();
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
        topic.addListener((MessageListener<String>) (channel, message) -> {
            try {
                new RedisPubSubListener().onRedisPubSubListener(channel, message);
            } catch (AccountNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public void loadCommands() {
        getProxy().getPluginManager().registerCommand(this, new CloseAllServersCommand());
        getProxy().getPluginManager().registerCommand(this, new SeeAllServersCommand());
        getProxy().getPluginManager().registerCommand(this, new AcceptPartyInvitCommand());
        getProxy().getPluginManager().registerCommand(this, new DenyPartyInvitCommand());
        getProxy().getPluginManager().registerCommand(this, new LoadAccountFromDatabaseCommand());
        getProxy().getPluginManager().registerCommand(this, new SaveAccountToDatabaseCommand());
        getProxy().getPluginManager().registerCommand(this, new ChangePlayerRankCommand());
    }

    public Map<String, String> getPartyInvitation() {
        return partyInvitation;
    }

    public boolean playerHaveInvitation(String player, String playerLeader) {
        for(Map.Entry<String, String> entry : partyInvitation.entrySet()) {
            if(entry.getKey().equalsIgnoreCase(player) && entry.getValue().equalsIgnoreCase(playerLeader)) {
                return true;
            }
        }
        return false;
    }
}
