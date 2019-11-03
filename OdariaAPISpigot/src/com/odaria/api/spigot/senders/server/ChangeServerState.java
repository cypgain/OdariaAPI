package com.odaria.api.spigot.senders.server;

import com.odaria.api.commons.data.management.redis.RedisAccess;
import com.odaria.api.commons.message.MessageAction;
import com.odaria.api.commons.message.RedisMessage;
import com.odaria.api.commons.servers.ServerState;
import com.odaria.api.spigot.OdariaAPISpigot;
import org.bukkit.Bukkit;
import org.redisson.api.RTopic;

public class ChangeServerState {
    public static void Action(ServerState serverState) {
        OdariaAPISpigot.INSTANCE.getServer().getScheduler().runTaskAsynchronously(OdariaAPISpigot.INSTANCE, new Runnable() {
            @Override
            public void run() {
                String json = new RedisMessage(MessageAction.CHANGE_SERVER_STATE)
                        .setParam("port", String.valueOf(Bukkit.getPort()))
                        .setParam("state", serverState.getName())
                        .toJSON();
                RTopic channel = RedisAccess.INSTANCE.getRedissonClient().getTopic(RedisAccess.CHANNEL);
                channel.publish(json);
            }
        });
    }
}
