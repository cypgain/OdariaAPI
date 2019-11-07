package com.odaria.api.bungee.listeners.redis.commands;

import com.odaria.api.bungee.OdariaAPIBungee;
import com.odaria.api.bungee.core.AccountProvider;
import com.odaria.api.commons.core.Account;
import com.odaria.api.commons.message.RedisMessage;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class ChangeValueListener {
    public static void Action(RedisMessage redisMessage) {
        OdariaAPIBungee.INSTANCE.getProxy().getScheduler().runAsync(OdariaAPIBungee.INSTANCE, new Runnable() {
            @Override
            public void run() {
                try {
                    String arg = redisMessage.getParam("arg");
                    String playerName = redisMessage.getParam("playerName");
                    int value = Integer.parseInt(redisMessage.getParam("value"));

                    ProxiedPlayer player = OdariaAPIBungee.INSTANCE.getProxy().getPlayer(playerName);
                    if(player != null) {
                        AccountProvider accountProvider = new AccountProvider(player);
                        Account account = accountProvider.getAccountFromRedis();
                        switch (arg) {
                            case "giveodabox":
                                account.setOdaBox(account.getOdaBox() + value);
                                break;

                            case "group_id":
                                account.setRankId(value);
                                break;

                            default:
                                break;
                        }
                        accountProvider.sendAccountToRedis(account);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
