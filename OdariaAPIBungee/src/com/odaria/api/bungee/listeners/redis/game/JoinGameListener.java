package com.odaria.api.bungee.listeners.redis.game;

import com.odaria.api.bungee.OdariaAPIBungee;
import com.odaria.api.bungee.core.PartyProvider;
import com.odaria.api.bungee.listeners.redis.party.TeleportPlayersPartyListener;
import com.odaria.api.bungee.servers.Server;
import com.odaria.api.bungee.servers.ServersManager;
import com.odaria.api.commons.core.Party;
import com.odaria.api.commons.message.MessageAction;
import com.odaria.api.commons.message.RedisMessage;
import com.odaria.api.commons.servers.ServerState;
import com.odaria.api.commons.servers.ServerType;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class JoinGameListener {
    public static void Action(RedisMessage redisMessage) {
        OdariaAPIBungee.INSTANCE.getProxy().getScheduler().runAsync(OdariaAPIBungee.INSTANCE, new Runnable() {
            @Override
            public void run() {
                OdariaAPIBungee odariaAPIBungee = OdariaAPIBungee.INSTANCE;

                ServerType type = ServerType.getByName(redisMessage.getParam("serverType"));
                String playerName = redisMessage.getParam("player");
                ProxiedPlayer player = odariaAPIBungee.getProxy().getPlayer(playerName);
                int maxPlayers = Integer.parseInt(redisMessage.getParam("maxPlayers"));

                if(player != null) {
                    PartyProvider partyProvider = new PartyProvider(player);
                    Party party = partyProvider.getPlayerParty();

                    if(party == null || party.getLeader().equalsIgnoreCase(player.getDisplayName())) {
                        ServersManager serversManager = ServersManager.INSTANCE;
                        Server server;

                        boolean full = true;

                        int playersCount = party == null ? 1 : party.getPlayers().size();

                        for(Server s : serversManager.getServers()) {
                            if(
                                s.getType() == type &&
                                s.getState() == ServerState.OPEN &&
                                odariaAPIBungee.getProxy().getServers().get(s.getName()).getPlayers().size() + playersCount <= s.getMaxPlayers()
                              ) {

                                player.connect(odariaAPIBungee.getProxy().getServers().get(s.getName()));
                                full = false;

                                break;
                            } else if(
                                    s.getState() == ServerState.STARTING &&
                                    s.getType() == type &&
                                    s.currentPlayerWaiting + playersCount <= s.getMaxPlayers()
                            ) {

                                s.currentPlayerWaiting += playersCount;
                                new java.util.Timer().schedule(
                                        new java.util.TimerTask() {
                                            @Override
                                            public void run() {
                                                if(party != null) {
                                                    teleportPlayersParty(player.getDisplayName(), party, s.getName());
                                                } else {
                                                    if(odariaAPIBungee.getProxy().getPlayers().contains(player)) {
                                                        player.connect(odariaAPIBungee.getProxy().getServers().get(s.getName()));
                                                    }
                                                }
                                            }
                                        },
                                        10000
                                );
                                full = false;
                                break;

                            }
                        }

                        if(full) {
                            int minRam = Integer.parseInt(redisMessage.getParam("minRam"));
                            int maxRam = Integer.parseInt(redisMessage.getParam("maxRam"));
                            server = serversManager.addServer(type, maxPlayers, minRam, maxRam);
                            player.sendMessage(new TextComponent("Veuillez patienter lors de la connexion au serveur..."));
                            server.currentPlayerWaiting += playersCount;
                            new java.util.Timer().schedule(
                                    new java.util.TimerTask() {
                                        @Override
                                        public void run() {
                                            if(party != null) {
                                                teleportPlayersParty(player.getDisplayName(), party, server.getName());
                                            } else {
                                                if(odariaAPIBungee.getProxy().getPlayers().contains(player)) {
                                                    player.connect(odariaAPIBungee.getProxy().getServers().get(server.getName()));
                                                }
                                            }
                                        }
                                    },
                                    10000
                            );
                        }
                    } else {
                        player.sendMessage(new TextComponent("Vous n'etes pas chef du groupe"));
                    }

                }

            }
        });
    }

    public static void teleportPlayersParty(String player, Party party, String serverName) {
        OdariaAPIBungee odariaAPIBungee = OdariaAPIBungee.INSTANCE;
        if(player.equalsIgnoreCase(party.getLeader())) {
            for(String pName : party.getPlayers()) {
                ProxiedPlayer p = odariaAPIBungee.getProxy().getPlayer(pName);
                if(p != null) {
                    p.connect(odariaAPIBungee.getProxy().getServers().get(serverName));
                }
            }
        }
    }
}
