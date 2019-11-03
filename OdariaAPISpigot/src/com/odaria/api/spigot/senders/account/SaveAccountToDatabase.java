package com.odaria.api.spigot.senders.account;

import com.odaria.api.commons.data.management.redis.RedisAccess;
import com.odaria.api.commons.message.MessageAction;
import com.odaria.api.commons.message.RedisMessage;
import com.odaria.api.spigot.OdariaAPISpigot;
import org.redisson.api.RTopic;

public class SaveAccountToDatabase {
    public static void Action(String playerName) {
        OdariaAPISpigot.INSTANCE.getServer().getScheduler().runTaskAsynchronously(OdariaAPISpigot.INSTANCE, new Runnable() {
            @Override
            public void run() {
                String json = new RedisMessage(MessageAction.SAVE_ACCOUNT_TO_DATABASE)
                        .setParam("playerName", playerName)
                        .toJSON();
                RTopic channel = RedisAccess.INSTANCE.getRedissonClient().getTopic(RedisAccess.CHANNEL);
                channel.publish(json);
            }
        });
    }
}
