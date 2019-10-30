package com.odaria.api.bungee.listeners.redis.party;

import com.odaria.api.bungee.OdariaAPIBungee;
import com.odaria.api.bungee.core.PartyProvider;
import com.odaria.api.commons.core.Party;
import com.odaria.api.commons.message.RedisMessage;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class InvitePlayerPartyListener {
    public static final int MAX_PLAYERS_PARTY = 4;

    public static void Action(RedisMessage redisMessage) {
        OdariaAPIBungee.INSTANCE.getProxy().getScheduler().runAsync(OdariaAPIBungee.INSTANCE, new Runnable() {
            @Override
            public void run() {
                String playerName = redisMessage.getParam("playerName");
                String playerToInviteName = redisMessage.getParam("playerToInvite");

                ProxiedPlayer player = OdariaAPIBungee.INSTANCE.getProxy().getPlayer(playerName);
                if(player != null) {
                    ProxiedPlayer playerToInvite = OdariaAPIBungee.INSTANCE.getProxy().getPlayer(playerToInviteName);
                    if(playerToInvite != null) {
                        PartyProvider playerPartyProvider = new PartyProvider(player);
                        Party playerParty = playerPartyProvider.getPlayerParty();

                        if(playerParty != null && playerParty.getLeader().equalsIgnoreCase(playerName)) {
                            if(playerParty.getPlayers().size() < MAX_PLAYERS_PARTY) {
                                PartyProvider playerToInvitePartyProvider = new PartyProvider(playerToInvite);
                                Party playerToInviteParty = playerToInvitePartyProvider.getPlayerParty();

                                if(playerToInviteParty == null) {
                                    playerToInvite.sendMessage(new TextComponent("Vous avez reçu une invitation dans le groupe de " + playerName + ", cliquer pour effectuer une action"));
                                    TextComponent acceptMsg = new TextComponent(ChatColor.GREEN + "[ACCEPTER]");
                                    acceptMsg.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/acceptpartyinvit " + playerName));
                                    TextComponent denyMsg = new TextComponent(ChatColor.RED + "[REFUSER]");
                                    denyMsg.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/denypartyinvit " + playerName));
                                    playerToInvite.sendMessage(acceptMsg);
                                    playerToInvite.sendMessage(denyMsg);

                                    OdariaAPIBungee odariaAPIBungee = OdariaAPIBungee.INSTANCE;
                                    if(odariaAPIBungee.playerHaveInvitation(playerToInviteName, playerName)) {
                                        odariaAPIBungee.getPartyInvitation().remove(playerToInviteName, playerName);
                                    }
                                    odariaAPIBungee.getPartyInvitation().put(playerToInviteName, playerName);

                                    player.sendMessage(new TextComponent("Vous avez envoyé une invitation à " + playerToInviteName));
                                } else {
                                    player.sendMessage(new TextComponent(playerToInviteName + " est déjà dans un groupe"));
                                }
                            } else {
                                player.sendMessage(new TextComponent("Votre groupe est plein"));
                            }
                        } else {
                            player.sendMessage(new TextComponent("Vous n'etes pas chef d'un groupe"));
                        }
                    } else {
                        player.sendMessage(new TextComponent(playerToInviteName + " n'est pas connecté"));
                    }
                }
            }
        });
    }
}
