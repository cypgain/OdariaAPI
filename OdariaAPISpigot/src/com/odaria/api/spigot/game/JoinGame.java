package com.odaria.api.spigot.game;

import com.odaria.api.commons.core.Party;
import com.odaria.api.commons.data.management.redis.RedisAccess;
import com.odaria.api.commons.message.MessageAction;
import com.odaria.api.commons.message.RedisMessage;
import com.odaria.api.commons.servers.ServerType;
import com.odaria.api.spigot.core.PartyProvider;
import org.bukkit.entity.Player;
import org.redisson.api.RTopic;

public class JoinGame {
    public static void Action(ServerType serverType, Player player, int minRam, int maxRam, int maxPlayers) {
        PartyProvider partyProvider = new PartyProvider(player.getDisplayName());
        Party party = partyProvider.getPlayerParty();

        if(party == null || party.getLeader().equalsIgnoreCase(player.getDisplayName())) {
            String json = new RedisMessage(MessageAction.JOIN_GAME)
                    .setParam("serverType", serverType.getName())
                    .setParam("player", player.getDisplayName())
                    .setParam("minRam", String.valueOf(minRam))
                    .setParam("maxRam", String.valueOf(maxRam))
                    .setParam("maxPlayers", String.valueOf(maxPlayers))
                    .toJSON();
            RTopic channel = RedisAccess.INSTANCE.getRedissonClient().getTopic(RedisAccess.CHANNEL);
            channel.publish(json);
        }
    }
}
