package com.odaria.api.bungee.listeners.redis;

import com.odaria.api.bungee.servers.Server;
import com.odaria.api.bungee.servers.ServersManager;
import com.odaria.api.commons.message.RedisMessage;
import com.odaria.api.commons.servers.ServerState;

public class ChangeServerStateListener {
    public static void Action(RedisMessage message) {
        ServerState serverState = ServerState.getServerState(message.getParam("state"));
        int port = Integer.parseInt(message.getParam("port"));

        Server server = ServersManager.INSTANCE.getServer(port);
        server.setState(serverState);
    }
}
