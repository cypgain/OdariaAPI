package com.odaria.api.bungee.listeners.redis.game;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.odaria.api.bungee.OdariaAPIBungee;
import com.odaria.api.commons.message.RedisMessage;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.lang.reflect.Type;
import java.util.List;

public class TeleportAllPlayersToHubListener {
    public static void Action(RedisMessage redisMessage) {
        OdariaAPIBungee.INSTANCE.getProxy().getScheduler().runAsync(OdariaAPIBungee.INSTANCE, new Runnable() {
            @Override
            public void run() {
                Type listType = new TypeToken<List<String>>(){}.getType();
                List<String> players = new Gson().fromJson(redisMessage.getParam("players"), listType);

                for(String playerName : players) {
                    ProxiedPlayer player = OdariaAPIBungee.INSTANCE.getProxy().getPlayer(playerName);
                    if(player != null) {
                        player.connect(ProxyServer.getInstance().getServerInfo("hub"));
                    }
                }
            }
        });
    }
}
