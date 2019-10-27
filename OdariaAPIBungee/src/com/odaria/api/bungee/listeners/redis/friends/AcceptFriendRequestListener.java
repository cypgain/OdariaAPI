package com.odaria.api.bungee.listeners.redis.friends;

import com.odaria.api.bungee.OdariaAPIBungee;
import com.odaria.api.commons.message.RedisMessage;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class AcceptFriendRequestListener {
    public static void Action(RedisMessage message) {
        String fromPlayerName = message.getParam("fromPlayer");
        String toPlayerName = message.getParam("toPlayer");

        ProxiedPlayer fromPlayer = OdariaAPIBungee.INSTANCE.getProxy().getPlayer(fromPlayerName);
        if(fromPlayer != null) {
            fromPlayer.sendMessage(new TextComponent(toPlayerName + " a accepté votre demande d'amis"));
        }
    }
}
