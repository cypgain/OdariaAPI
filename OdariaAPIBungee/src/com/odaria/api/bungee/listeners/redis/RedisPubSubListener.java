package com.odaria.api.bungee.listeners.redis;

import com.google.gson.Gson;
import com.odaria.api.bungee.data.management.exceptions.AccountNotFoundException;
import com.odaria.api.bungee.listeners.redis.account.LoadAccountFromDatabaseListener;
import com.odaria.api.bungee.listeners.redis.account.SaveAccountToDatabaseListener;
import com.odaria.api.bungee.listeners.redis.friends.AcceptFriendRequestListener;
import com.odaria.api.bungee.listeners.redis.friends.DenyFriendRequestListener;
import com.odaria.api.bungee.listeners.redis.friends.RemoveFriendListener;
import com.odaria.api.bungee.listeners.redis.friends.SendFriendRequestListener;
import com.odaria.api.bungee.listeners.redis.game.JoinGameListener;
import com.odaria.api.bungee.listeners.redis.game.TeleportAllPlayersToHubListener;
import com.odaria.api.bungee.listeners.redis.party.InvitePlayerPartyListener;
import com.odaria.api.bungee.listeners.redis.party.TeleportPlayersPartyListener;
import com.odaria.api.bungee.listeners.redis.server.ChangeServerStateListener;
import com.odaria.api.bungee.listeners.redis.server.SendMaxPlayersListener;
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
            case REMOVE_FRIEND:
                RemoveFriendListener.Action(redisMessage);
                break;
            case SEND_MAX_PLAYERS:
                SendMaxPlayersListener.Action(redisMessage);
                break;
            case INVITE_PLAYER_PARTY:
                InvitePlayerPartyListener.Action(redisMessage);
                break;
            case TELEPORT_PLAYERS_PARTY:
                TeleportPlayersPartyListener.Action(redisMessage);
                break;
            case JOIN_GAME:
                JoinGameListener.Action(redisMessage);
                break;
            case TELEPORT_ALL_PLAYERS_TO_HUB:
                TeleportAllPlayersToHubListener.Action(redisMessage);
                break;
        }
    }
}
