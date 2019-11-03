package com.odaria.api.bungee.listeners.redis.game;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.odaria.api.bungee.OdariaAPIBungee;
import com.odaria.api.bungee.core.AccountProvider;
import com.odaria.api.commons.core.Account;
import com.odaria.api.commons.message.RedisMessage;
import com.odaria.api.commons.ranks.Ranks;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Random;

public class AddRewardsListener {
    public static void Action(RedisMessage redisMessage) {
        OdariaAPIBungee.INSTANCE.getProxy().getScheduler().runAsync(OdariaAPIBungee.INSTANCE, new Runnable() {
            @Override
            public void run() {
                Type listType = new TypeToken<List<String>>(){}.getType();
                List<String> players = new Gson().fromJson(redisMessage.getParam("players"), listType);

                int expMin = Integer.parseInt(redisMessage.getParam("expMin"));
                int expMax = Integer.parseInt(redisMessage.getParam("expMax"));
                int odaBox = Integer.parseInt(redisMessage.getParam("odaBox"));

                for(String playerName : players) {
                    ProxiedPlayer player = OdariaAPIBungee.INSTANCE.getProxy().getPlayer(playerName);
                    if(player != null) {
                        AccountProvider accountProvider = new AccountProvider(player);
                        Account account = accountProvider.getAccountFromRedis();

                        if(account != null) {
                            if(account.getRankId() == Ranks.VIP.getGroupId()) {
                                Random r = new Random();
                                int random = r.nextInt((expMax - expMin) + 1) + expMin;

                                account.setOdapassExp(account.getOdapassExp() + random);
                            }

                            account.setOdaBox(account.getOdaBox() + odaBox);

                            accountProvider.sendAccountToRedis(account);
                        }
                    }
                }
            }
        });
    }
}
