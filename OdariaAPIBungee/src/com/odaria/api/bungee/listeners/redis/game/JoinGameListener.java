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



                if(player != null) {
                    PartyProvider playerPartyProvider = new PartyProvider(player);
                    Party playerParty = playerPartyProvider.getPlayerParty();

                    if(playerParty == null || playerParty.getLeader().equalsIgnoreCase(playerName)) {
                        ServersManager serversManager = ServersManager.INSTANCE;
                        Server server;

                        boolean full = true;
                        for(Server s : serversManager.getServers()) {
                            if(playerParty != null) {
                                if(s.getType() == type && s.getState() == ServerState.OPEN &&  odariaAPIBungee.getProxy().getServers().get(s.getName()).getPlayers().size() + playerParty.getPlayers().size() <= s.getMaxPlayers()) {
                                    player.connect(odariaAPIBungee.getProxy().getServers().get(s.getName()));
                                    teleportPlayersParty(playerName);
                                    full = false;
                                    break;
                                } else if(s.getState() == ServerState.STARTING &&  s.getType() == type && s.currentPlayerWaiting + playerParty.getPlayers().size() <= s.getMaxPlayers()) {
                                    s.currentPlayerWaiting++;
                                    new java.util.Timer().schedule(
                                            new java.util.TimerTask() {
                                                @Override
                                                public void run() {
                                                    ProxiedPlayer p = odariaAPIBungee.getProxy().getPlayer(player.getDisplayName());
                                                    if(p != null) {
                                                        p.connect(odariaAPIBungee.getProxy().getServers().get(s.getName()));
                                                        teleportPlayersParty(playerName);
                                                    }
                                                }
                                            },
                                            10000
                                    );
                                    full = false;
                                    break;
                                }
                            } else {
                                if(s.getType() == type && s.getState() == ServerState.OPEN &&  odariaAPIBungee.getProxy().getServers().get(s.getName()).getPlayers().size() < s.getMaxPlayers()) {
                                    player.connect(odariaAPIBungee.getProxy().getServers().get(s.getName()));
                                    full = false;
                                    break;
                                } else if(s.getState() == ServerState.STARTING &&  s.getType() == type && s.currentPlayerWaiting < s.getMaxPlayers()) {
                                    s.currentPlayerWaiting++;
                                    new java.util.Timer().schedule(
                                            new java.util.TimerTask() {
                                                @Override
                                                public void run() {
                                                    ProxiedPlayer p = odariaAPIBungee.getProxy().getPlayer(player.getDisplayName());
                                                    if(p != null) {
                                                        p.connect(odariaAPIBungee.getProxy().getServers().get(s.getName()));
                                                    }
                                                }
                                            },
                                            10000
                                    );
                                    full = false;
                                    break;
                                }
                            }
                        }

                        if(full) {
                            int minRam = Integer.parseInt(redisMessage.getParam("minRam"));
                            int maxRam = Integer.parseInt(redisMessage.getParam("maxRam"));
                            int maxPlayers = Integer.parseInt(redisMessage.getParam("maxPlayers"));
                            server = serversManager.addServer(type, minRam, maxRam, maxPlayers);
                            player.sendMessage(new TextComponent("Veuillez patienter lors de la connexion au serveur..."));
                            if(playerParty != null) {
                                server.currentPlayerWaiting += playerParty.getPlayers().size();
                            } else {
                                server.currentPlayerWaiting++;
                            }
                            new java.util.Timer().schedule(
                                    new java.util.TimerTask() {
                                        @Override
                                        public void run() {
                                            if(odariaAPIBungee.getProxy().getPlayers().contains(player)) {
                                                player.connect(odariaAPIBungee.getProxy().getServers().get(server.getName()));
                                                teleportPlayersParty(playerName);
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

    public static void teleportPlayersParty(String playerName) {
        RedisMessage message = new RedisMessage(MessageAction.JOIN_GAME);
        message.setParam("playerName", playerName);
        TeleportPlayersPartyListener.Action(message);
    }
}
