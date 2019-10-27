package com.odaria.api.spigot.senders;

import com.odaria.api.commons.data.management.redis.RedisAccess;
import com.odaria.api.commons.message.MessageAction;
import com.odaria.api.commons.message.RedisMessage;
import com.odaria.api.commons.servers.ServerState;
import org.redisson.api.RTopic;

public class ChangeServerStateSender {
    public static void Action(int serverPort, ServerState serverState) {
        String json = new RedisMessage(MessageAction.CHANGE_SERVER_STATE)
                .setParam("port", String.valueOf(serverPort))
                .setParam("state", serverState.getName())
                .toJSON();
        RTopic channel = RedisAccess.INSTANCE.getRedissonClient().getTopic(RedisAccess.CHANNEL);
        channel.publish(json);
    }
}
