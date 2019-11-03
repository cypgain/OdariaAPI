package com.odaria.api.spigot.senders;

import com.google.gson.Gson;
import com.odaria.api.commons.data.management.redis.RedisAccess;
import com.odaria.api.commons.message.MessageAction;
import com.odaria.api.commons.message.RedisMessage;
import com.odaria.api.spigot.OdariaAPISpigot;
import org.redisson.api.RTopic;
import java.util.List;

public class AddRewards {
    public static void Action(List<String> players, int expMin, int expMax, int odaBox) {
        OdariaAPISpigot.INSTANCE.getServer().getScheduler().runTaskAsynchronously(OdariaAPISpigot.INSTANCE, new Runnable() {
            @Override
            public void run() {
                String json = new RedisMessage(MessageAction.ADD_REWARDS)
                        .setParam("players", new Gson().toJson(players))
                        .setParam("expMin", String.valueOf(expMin))
                        .setParam("expMax", String.valueOf(expMax))
                        .setParam("odaBox", String.valueOf(odaBox))
                        .toJSON();
                RTopic channel = RedisAccess.INSTANCE.getRedissonClient().getTopic(RedisAccess.CHANNEL);
                channel.publish(json);
            }
        });
    }
}
