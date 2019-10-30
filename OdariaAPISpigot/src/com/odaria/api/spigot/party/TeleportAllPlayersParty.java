package com.odaria.api.spigot.party;

import com.odaria.api.commons.core.Party;
import com.odaria.api.commons.data.management.redis.RedisAccess;
import com.odaria.api.commons.message.MessageAction;
import com.odaria.api.commons.message.RedisMessage;
import com.odaria.api.spigot.core.PartyProvider;
import org.bukkit.entity.Player;
import org.redisson.api.RTopic;

public class TeleportAllPlayersParty {
    public static void Action(Player player) {
        PartyProvider partyProvider = new PartyProvider(player.getDisplayName());
        Party party = partyProvider.getPlayerParty();

        if(party != null) {
            if(party.getLeader().equalsIgnoreCase(player.getDisplayName())) {
                String json = new RedisMessage(MessageAction.TELEPORT_PLAYERS_PARTY)
                        .setParam("playerName", player.getDisplayName())
                        .toJSON();
                RTopic channel = RedisAccess.INSTANCE.getRedissonClient().getTopic(RedisAccess.CHANNEL);
                channel.publish(json);
            } else {
                player.sendMessage("Vous n'etes pas chef du groupe");
            }
        }
    }
}
