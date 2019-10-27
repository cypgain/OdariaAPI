package com.odaria.api.bungee.listeners.redis.server;

import com.odaria.api.bungee.OdariaAPIBungee;
import com.odaria.api.bungee.servers.Server;
import com.odaria.api.bungee.servers.ServersManager;
import com.odaria.api.commons.message.RedisMessage;
import com.odaria.api.commons.servers.ServerState;
import net.md_5.bungee.BungeeCord;

public class ChangeServerStateListener {
    public static void Action(RedisMessage message) {
        BungeeCord.getInstance().getScheduler().runAsync(OdariaAPIBungee.INSTANCE, new Runnable() {
            @Override
            public void run() {
                ServerState serverState = ServerState.getServerState(message.getParam("state"));
                int port = Integer.parseInt(message.getParam("port"));

                Server server = ServersManager.INSTANCE.getServer(port);
                if(server != null) {
                    server.setState(serverState);
                }
            }
        });
    }
}
