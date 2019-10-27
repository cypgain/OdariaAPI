package com.odaria.api.bungee.commands;

import com.odaria.api.bungee.servers.Server;
import com.odaria.api.bungee.servers.ServersManager;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.command.ConsoleCommandSender;

import java.util.List;

public class SeeAllServersCommand extends Command {
    public SeeAllServersCommand() {
        super("seeallservers");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof ConsoleCommandSender) {
            ServersManager serversManager = ServersManager.INSTANCE;
            List<Server> servers = serversManager.getServers();
            int i = 0;
            for(Server server : servers) {
                System.out.println(i + " : Name: " + server.getName() + " Port: " + server.getPort() + " MaxPlayers: " + server.getMaxPlayers() + " State: " + server.getState().getName());
                i++;
            }
        }
    }
}
