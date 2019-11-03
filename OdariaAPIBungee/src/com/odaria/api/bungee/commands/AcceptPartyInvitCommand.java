package com.odaria.api.bungee.commands;

import com.odaria.api.bungee.OdariaAPIBungee;
import com.odaria.api.bungee.core.PartyProvider;
import com.odaria.api.bungee.listeners.redis.party.InvitePlayerPartyListener;
import com.odaria.api.commons.core.Party;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class AcceptPartyInvitCommand extends Command {

    public AcceptPartyInvitCommand() {
        super("acceptpartyinvit");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        OdariaAPIBungee.INSTANCE.getProxy().getScheduler().runAsync(OdariaAPIBungee.INSTANCE, new Runnable() {
            @Override
            public void run() {
                if(sender instanceof ProxiedPlayer) {
                    if(args.length == 1) {
                        ProxiedPlayer player = (ProxiedPlayer)sender;
                        String playerLeadName = args[0];

                        OdariaAPIBungee odariaAPIBungee = OdariaAPIBungee.INSTANCE;
                        if(odariaAPIBungee.playerHaveInvitation(player.getDisplayName(), playerLeadName)) {
                            ProxiedPlayer playerLead = OdariaAPIBungee.INSTANCE.getProxy().getPlayer(playerLeadName);
                            if(playerLead != null) {
                                PartyProvider partyProvider = new PartyProvider(playerLead);
                                Party party = partyProvider.getPlayerParty();
                                if(party != null) {
                                    if(party.getPlayers().size() < InvitePlayerPartyListener.MAX_PLAYERS_PARTY) {
                                        player.sendMessage(new TextComponent("Vous avez accepté l'invitation de groupe de " + playerLead));
                                        odariaAPIBungee.getPartyInvitation().remove(player.getDisplayName(), playerLead);
                                        party.getPlayers().add(player.getDisplayName());
                                        partyProvider.savePlayerParty(party);
                                        playerLead.sendMessage(new TextComponent(player.getDisplayName() + " a rejoint votre groupe"));

                                        player.connect(playerLead.getServer().getInfo());
                                    } else {
                                        player.sendMessage(new TextComponent("Il y a trop de joueur dans ce groupe"));
                                    }
                                } else {
                                    player.sendMessage(new TextComponent("Vous ne pouvez pas rejoindre un groupe inexistant"));
                                }
                            } else {
                                player.sendMessage(new TextComponent("Vous ne pouvez pas rejoindre un groupe inexistant"));
                            }
                        }
                    }
                }
            }
        });
    }

}

