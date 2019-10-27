package com.odaria.api.bungee.listeners.redis.friends;

import com.odaria.api.bungee.OdariaAPIBungee;
import com.odaria.api.bungee.core.AccountProvider;
import com.odaria.api.commons.core.Account;
import com.odaria.api.commons.message.RedisMessage;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class RemoveFriendListener {
    public static void Action(RedisMessage redisMessage) {
        BungeeCord.getInstance().getScheduler().runAsync(OdariaAPIBungee.INSTANCE, new Runnable() {
            @Override
            public void run() {
                String playerName = redisMessage.getParam("player");
                String playerToRemoveName = redisMessage.getParam("playerToRemove");

                ProxiedPlayer playerToRemove = OdariaAPIBungee.INSTANCE.getProxy().getPlayer(playerToRemoveName);
                if(playerToRemove != null) {
                    AccountProvider accountProvider = new AccountProvider(playerToRemove);
                    Account account = accountProvider.getAccountFromRedis();
                    account.getFriends().remove(playerName);
                    playerToRemove.sendMessage(new TextComponent(playerName + " vous a supprimé de ses amis"));
                    accountProvider.sendAccountToRedis(account);
                }
            }
        });
    }
}
