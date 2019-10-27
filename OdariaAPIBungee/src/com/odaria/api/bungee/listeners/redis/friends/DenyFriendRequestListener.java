package com.odaria.api.bungee.listeners.redis.friends;

import com.odaria.api.bungee.OdariaAPIBungee;
import com.odaria.api.commons.message.RedisMessage;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class DenyFriendRequestListener {
    public static void Action(RedisMessage message) {
        BungeeCord.getInstance().getScheduler().runAsync(OdariaAPIBungee.INSTANCE, new Runnable() {
            @Override
            public void run() {
                String fromPlayerName = message.getParam("fromPlayer");
                String toPlayerName = message.getParam("toPlayer");

                ProxiedPlayer fromPlayer = OdariaAPIBungee.INSTANCE.getProxy().getPlayer(fromPlayerName);
                if(fromPlayer != null) {
                    fromPlayer.sendMessage(new TextComponent(toPlayerName + " a refusé votre demande d'amis"));
                }
            }
        });
    }
}
