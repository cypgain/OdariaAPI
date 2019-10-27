package com.odaria.api.bungee.listeners.redis.account;

import com.odaria.api.bungee.OdariaAPIBungee;
import com.odaria.api.bungee.core.AccountProvider;
import com.odaria.api.bungee.data.management.exceptions.AccountNotFoundException;
import com.odaria.api.commons.message.RedisMessage;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.sql.SQLException;

public class SaveAccountToDatabaseListener {
    public static void Action(RedisMessage message) throws AccountNotFoundException, SQLException {
        BungeeCord.getInstance().getScheduler().runAsync(OdariaAPIBungee.INSTANCE, new Runnable() {
            @Override
            public void run() {
                String playerName = message.getParam("playerName");

                ProxiedPlayer player = OdariaAPIBungee.INSTANCE.getProxy().getPlayer(playerName);
                if(player != null) {
                    AccountProvider accountProvider = new AccountProvider(player);
                    try {
                        accountProvider.saveAccountToDatabase();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
