package com.odaria.api.bungee.listeners.redis.account;

import com.odaria.api.bungee.OdariaAPIBungee;
import com.odaria.api.bungee.core.AccountProvider;
import com.odaria.api.bungee.data.management.exceptions.AccountNotFoundException;
import com.odaria.api.commons.core.Account;
import com.odaria.api.commons.message.RedisMessage;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class LoadAccountFromDatabaseListener {
    public static void Action(RedisMessage message) throws AccountNotFoundException {
        BungeeCord.getInstance().getScheduler().runAsync(OdariaAPIBungee.INSTANCE, new Runnable() {
            @Override
            public void run() {
                String playerName = message.getParam("playerName");

                ProxiedPlayer player = OdariaAPIBungee.INSTANCE.getProxy().getPlayer(playerName);
                if(player != null) {
                    AccountProvider accountProvider = new AccountProvider(player);
                    Account account = null;
                    try {
                        account = accountProvider.getAccountFromDatabase();
                    } catch (AccountNotFoundException e) {
                        e.printStackTrace();
                    }
                    accountProvider.sendAccountToRedis(account);
                }
            }
        });
    }
}
