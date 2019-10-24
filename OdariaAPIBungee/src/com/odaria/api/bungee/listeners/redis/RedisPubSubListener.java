package com.odaria.api.bungee.listeners.redis;

import com.google.gson.Gson;
import com.odaria.api.commons.message.RedisMessage;

public class RedisPubSubListener {
    public void onRedisPubSubListener(String channel, String json) {
        if(!(channel.equalsIgnoreCase(channel)))
            return;

        RedisMessage redisMessage = new Gson().fromJson(json, RedisMessage.class);
        switch (redisMessage.getAction()) {
            case CHANGE_SERVER_STATE:
                ChangeServerStateListener.Action(redisMessage);
                break;
        }
    }
}
