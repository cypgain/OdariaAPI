package com.odaria.api.spigot.senders.account;

import com.odaria.api.commons.data.management.redis.RedisAccess;
import com.odaria.api.commons.message.MessageAction;
import com.odaria.api.commons.message.RedisMessage;
import org.redisson.api.RTopic;

public class LoadAccountFromDatabaseSender {
    public static void Action(String playerName) {
        String json = new RedisMessage(MessageAction.LOAD_ACCOUNT_FROM_DATABASE)
                .setParam("playerName", playerName)
                .toJSON();
        RTopic channel = RedisAccess.INSTANCE.getRedissonClient().getTopic(RedisAccess.CHANNEL);
        channel.publish(json);
    }
}
