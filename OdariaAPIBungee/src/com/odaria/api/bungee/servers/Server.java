package com.odaria.api.bungee.servers;

import com.odaria.api.commons.servers.ServerState;
import com.odaria.api.commons.servers.ServerType;

public class Server {
    private String name;
    private ServerType type;
    private ServerState state;
    private int port;
    private int maxPlayers;

    public Server(String name, ServerType type, ServerState state, int port) {
        this.name = name;
        this.type = type;
        this.state = state;
        this.port = port;
        maxPlayers = 0;
    }

    public String getName() {
        return name;
    }

    public ServerType getType() {
        return type;
    }

    public int getPort() {
        return port;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public ServerState getState() {
        return state;
    }

    public void setState(ServerState state) {
        this.state = state;
    }
}
