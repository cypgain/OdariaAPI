package com.odaria.api.bungee.listeners.redis.party;

import com.odaria.api.bungee.OdariaAPIBungee;
import com.odaria.api.bungee.core.PartyProvider;
import com.odaria.api.commons.core.Party;
import com.odaria.api.commons.message.RedisMessage;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.List;

public class TeleportPlayersPartyListener {
    public static void Action(RedisMessage redisMessage) {
        OdariaAPIBungee.INSTANCE.getProxy().getScheduler().runAsync(OdariaAPIBungee.INSTANCE, new Runnable() {
            @Override
            public void run() {
                OdariaAPIBungee odariaAPIBungee = OdariaAPIBungee.INSTANCE;
                String playerName = redisMessage.getParam("playerName");
                ProxiedPlayer player = odariaAPIBungee.getProxy().getPlayer(playerName);

                if(player != null) {
                    PartyProvider partyProvider = new PartyProvider(player);
                    Party party = partyProvider.getPlayerParty();

                    if(party != null) {
                        List<String> playersToTeleport = party.getPlayers();
                        playersToTeleport.remove(playerName);

                        ServerInfo serverInfo = player.getServer().getInfo();
                        for(String pName : playersToTeleport) {
                            ProxiedPlayer p = odariaAPIBungee.getProxy().getPlayer(pName);
                            if(p != null) {
                                p.connect(serverInfo);
                            }
                        }
                    }
                }
            }
        });
    }
}
