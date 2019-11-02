package com.odaria.api.bungee.servers;

import com.odaria.api.commons.servers.ServerState;

import java.util.ArrayList;
import java.util.List;

public class CloseServersThread extends Thread {
    public static CloseServersThread INSTANCE = null;

    private boolean running = true;

    public CloseServersThread() {
        if(INSTANCE != null)
            INSTANCE.running = false;
        INSTANCE = this;
    }

    @Override
    public void run() {
        while(running) {
            ServersManager serversManager = ServersManager.INSTANCE;
            List<Server> serverToBeRemoved = new ArrayList<>();
            for(Server server : serversManager.getServers()) {
                if(server.getState() == ServerState.SHUTDOWN && !(server.getType().isHub())) {
                    serversManager.closeServer(server.getName());
                    System.out.println("Closing server " + server.getName());
                    serverToBeRemoved.add(server);
                }
            }

            for(Server server : serverToBeRemoved) {
                serversManager.getServers().remove(server);
            }

            serverToBeRemoved.clear();

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}