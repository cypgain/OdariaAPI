package com.odaria.api.bungee.servers;

import com.odaria.api.bungee.OdariaAPIBungee;
import com.odaria.api.commons.servers.ServerState;
import com.odaria.api.commons.servers.ServerType;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ServersManager {

    public static ServersManager INSTANCE;
    private static String OS = System.getProperty("os.name").toLowerCase();

    private List<Server> servers;

    public ServersManager() {
        INSTANCE = this;
        servers = new ArrayList<Server>();

        CloseServersThread serversThread = new CloseServersThread();
        serversThread.start();
    }

    public Server addServer(ServerType type, int minRam, int maxRam) {
            /* Generate the server */
            int port = generatePort();
            String name = "mc" + port;

            InetSocketAddress address = new InetSocketAddress(OdariaAPIBungee.IP, port);
            UUID uuid = UUID.randomUUID();
            ServerInfo info = ProxyServer.getInstance().constructServerInfo(uuid.toString(), address, name, false);
            OdariaAPIBungee.INSTANCE.getProxy().getServers().put(name, info);

            Server server = new Server(name, type, ServerState.STARTING, port);
            servers.add(server);

            /* Execute script newserver */
            String newServerPath = System.getProperty("user.dir").replace("bungeecord", "").replace("\\", "/") + "scripts/newserver" + getScriptExtension() + " " + type.getTemplate() + " " + name;
            try {
                Process p = Runtime.getRuntime().exec(newServerPath);
                p.waitFor();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }

            /* Update server.properties */
            String serverPropertiesPath = System.getProperty("user.dir").replace("\\", "/").replace("bungeecord", "") + "servers/" + name + "/server.properties";
            Path path = Paths.get(serverPropertiesPath);
            List<String> lines = null;
            try {
                lines = Files.readAllLines(path, StandardCharsets.UTF_8);
            } catch (IOException e) {
                e.printStackTrace();
            }
            lines.set(9, "server-port=" + port);
            lines.set(15, "server-ip=" + OdariaAPIBungee.IP);
            try {
                Files.write(path, lines, StandardCharsets.UTF_8);
            } catch (IOException e) {
                e.printStackTrace();
            }

            /* Launch the server */
            launchServer(name, minRam, maxRam);

            return server;
    }

    private void launchServer(String name, int minRam, int maxRam) {
        /* Execute script runserver */
        String runServerPath = System.getProperty("user.dir").replace("bungeecord", "").replace("\\", "/") + "scripts/runserver" + getScriptExtension() + " " + name + " " + minRam + " " + maxRam;
        try {
            Process p = Runtime.getRuntime().exec(runServerPath);
            p.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void closeAndRemoveServer(String name) {
        /* Execute script removeserver */
        String newServerPath = System.getProperty("user.dir").replace("bungeecord", "").replace("\\", "/") + "scripts/removeserver" + getScriptExtension() + " " + name;
        try {
            Process p = Runtime.getRuntime().exec(newServerPath);
            p.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        /* Remove server from the list */
        Server server = getServer(name);
        servers.remove(server);
    }

    public void closeServer(String name) {
        /* Execute script removeserver */
        String newServerPath = System.getProperty("user.dir").replace("bungeecord", "").replace("\\", "/") + "scripts/removeserver" + getScriptExtension() + " " + name;
        try {
            Process p = Runtime.getRuntime().exec(newServerPath);
            p.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private int generatePort() {
        boolean exits = true;
        int port = 0;
        while(exits) {
            int random = 28000 + (int)(Math.random() * ((35000 - 28000) + 1));
            if(!portExist(random)) {
                port = random;
                exits = false;
            }
        }
        return port;
    }

    private boolean portExist(int port) {
        for(Server server : servers) {
            if(server.getPort() == port) {
                return true;
            }
        }
        return false;
    }

    public List<Server> getServers() {
        return servers;
    }

    public Server getServer(int port) {
        for(Server server : servers) {
            if(server.getPort() == port) {
                return server;
            }
        }
        return null;
    }

    public Server getServer(String name) {
        for(Server server : servers) {
            if(server.getName().equalsIgnoreCase(name)) {
                return server;
            }
        }
        return null;
    }

    private String getScriptExtension() {
        if(OS.contains("win")) {
            return ".bat";
        } else {
            return ".sh";
        }
    }

    public void resetServersList() {
        servers.clear();
    }
}
