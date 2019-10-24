package com.odaria.api.bungee.listeners;

import com.odaria.api.bungee.utils.ConsoleManager;

public class RedisPubSubListener {
    public void onRedisPubSubListener(String channel, String message) {
        ConsoleManager.debugMessage("Channel: " + channel + " Message:" + message);
    }
}
