package com.odaria.api.bungee.commands;

import com.odaria.api.bungee.OdariaAPIBungee;
import com.odaria.api.bungee.core.PartyProvider;
import com.odaria.api.commons.core.Party;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class HubCommand extends Command {

    public HubCommand() {
        super("hub");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof ProxiedPlayer) {
            ProxiedPlayer player = (ProxiedPlayer)sender;
            PartyProvider partyProvider = new PartyProvider(player);
            Party party = partyProvider.getPlayerParty();
            if(party != null) {
                if(party.getLeader().equalsIgnoreCase(player.getDisplayName())){
                    for(String pName : party.getPlayers()) {
                        ProxiedPlayer p = OdariaAPIBungee.INSTANCE.getProxy().getPlayer(pName);
                        if(p != null) {
                            p.connect(ProxyServer.getInstance().getServerInfo("hub"));
                            p.sendMessage(new TextComponent("Vous allez être téléporté au HUB"));
                        }
                    }
                } else {
                    player.sendMessage(new TextComponent("Uniquement le chef du groupe peux effectuer cette commande"));
                }
            } else {
                player.sendMessage(new TextComponent("Vous allez être téléporté au HUB"));
                player.connect(ProxyServer.getInstance().getServerInfo("hub"));
            }
        }
    }

}

