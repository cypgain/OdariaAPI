package com.odaria.api.bungee.listeners.redis;

import com.google.gson.Gson;
import com.odaria.api.bungee.data.management.exceptions.AccountNotFoundException;
import com.odaria.api.bungee.listeners.redis.account.LoadAccountFromDatabaseListener;
import com.odaria.api.bungee.listeners.redis.account.SaveAccountToDatabaseListener;
import com.odaria.api.bungee.listeners.redis.friends.AcceptFriendRequestListener;
import com.odaria.api.bungee.listeners.redis.friends.DenyFriendRequestListener;
import com.odaria.api.bungee.listeners.redis.friends.SendFriendRequestListener;
import com.odaria.api.commons.message.RedisMessage;

import java.sql.SQLException;

public class RedisPubSubListener {
    public void onRedisPubSubListener(String channel, String json) throws AccountNotFoundException, SQLException {
        if(!(channel.equalsIgnoreCase(channel)))
            return;

        RedisMessage redisMessage = new Gson().fromJson(json, RedisMessage.class);
        switch (redisMessage.getAction()) {
            case CHANGE_SERVER_STATE:
                ChangeServerStateListener.Action(redisMessage);
                break;
            case SAVE_ACCOUNT_TO_DATABASE:
                SaveAccountToDatabaseListener.Action(redisMessage);
                break;
            case LOAD_ACCOUNT_FROM_DATABASE:
                LoadAccountFromDatabaseListener.Action(redisMessage);
                break;
            case SEND_FRIEND_REQUEST:
                SendFriendRequestListener.Action(redisMessage);
                break;
            case ACCEPT_FRIEND_REQUEST:
                AcceptFriendRequestListener.Action(redisMessage);
                break;
            case DENY_FRIEND_REQUEST:
                DenyFriendRequestListener.Action(redisMessage);
                break;
        }
    }
}
