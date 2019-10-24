package com.odaria.api.commons.servers;

public enum ServerState {
    STARTING("starting"),
    OPEN("open"),
    RUNNING("running"),
    SHUTDOWN("shutdown");

    String name;

    ServerState(String name) {
        this.name = name;
    }

    public static ServerState getServerState(String name) {
        for(ServerState state : values()) {
            if(state.getName().equalsIgnoreCase(name)) {
                return state;
            }
        }
        return null;
    }

    public String getName() {
        return this.name;
    }
}
