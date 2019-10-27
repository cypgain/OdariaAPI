package com.odaria.api.bungee.commands;

import com.odaria.api.bungee.servers.Server;
import com.odaria.api.bungee.servers.ServersManager;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.command.ConsoleCommandSender;

import java.util.List;

public class CloseAllServersCommand extends Command {
    public CloseAllServersCommand() {
        super("closeallservers");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof ConsoleCommandSender) {
            ServersManager serversManager = ServersManager.INSTANCE;
            List<Server> servers = serversManager.getServers();
            for(Server server : servers) {
                serversManager.closeServer(server.getName());
                System.out.println("Closing server " + server.getName());
            }
            serversManager.resetServersList();
        }
    }
}
