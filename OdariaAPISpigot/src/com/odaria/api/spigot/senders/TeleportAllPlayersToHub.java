package com.odaria.api.spigot.senders;

import com.google.gson.Gson;
import com.odaria.api.commons.data.management.redis.RedisAccess;
import com.odaria.api.commons.message.MessageAction;
import com.odaria.api.commons.message.RedisMessage;
import com.odaria.api.spigot.OdariaAPISpigot;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.redisson.api.RTopic;

import java.util.ArrayList;
import java.util.List;

public class TeleportAllPlayersToHub {
    public static void Action() {
        OdariaAPISpigot.INSTANCE.getServer().getScheduler().runTaskAsynchronously(OdariaAPISpigot.INSTANCE, new Runnable() {
            @Override
            public void run() {
                List<String> players = new ArrayList<>();
                for(Player player : Bukkit.getOnlinePlayers()) {
                    players.add(player.getDisplayName());
                }

                String json = new RedisMessage(MessageAction.TELEPORT_ALL_PLAYERS_TO_HUB)
                        .setParam("players", new Gson().toJson(players))
                        .toJSON();
                RTopic channel = RedisAccess.INSTANCE.getRedissonClient().getTopic(RedisAccess.CHANNEL);
                channel.publish(json);
            }
        });
    }
}
