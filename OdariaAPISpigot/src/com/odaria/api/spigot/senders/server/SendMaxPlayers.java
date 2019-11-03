package com.odaria.api.spigot.senders.server;

import com.odaria.api.commons.data.management.redis.RedisAccess;
import com.odaria.api.commons.message.MessageAction;
import com.odaria.api.commons.message.RedisMessage;
import com.odaria.api.spigot.OdariaAPISpigot;
import org.bukkit.Bukkit;
import org.redisson.api.RTopic;

public class SendMaxPlayers {
    public static void Action(int maxPlayers) {
        OdariaAPISpigot.INSTANCE.getServer().getScheduler().runTaskAsynchronously(OdariaAPISpigot.INSTANCE, new Runnable() {
            @Override
            public void run() {
                String json = new RedisMessage(MessageAction.SEND_MAX_PLAYERS)
                        .setParam("port", String.valueOf(Bukkit.getPort()))
                        .setParam("maxPlayers", String.valueOf(maxPlayers))
                        .toJSON();
                RTopic channel = RedisAccess.INSTANCE.getRedissonClient().getTopic(RedisAccess.CHANNEL);
                channel.publish(json);
            }
        });
    }
}
