package com.odaria.api.bungee.listeners.redis.friends;

import com.odaria.api.bungee.OdariaAPIBungee;
import com.odaria.api.commons.message.RedisMessage;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class SendFriendRequestListener {
    public static void Action(RedisMessage message) {
        BungeeCord.getInstance().getScheduler().runAsync(OdariaAPIBungee.INSTANCE, new Runnable() {
            @Override
            public void run() {
                String toPlayer = message.getParam("toPlayer");
                ProxiedPlayer player = OdariaAPIBungee.INSTANCE.getProxy().getPlayer(toPlayer);
                if(player != null) {
                    player.sendMessage(new TextComponent("Vous avez reçu une nouvelle demande d'amis"));
                }
            }
        });
    }
}
