package com.odaria.api.spigot.party;

import com.odaria.api.commons.core.Party;
import com.odaria.api.commons.data.management.redis.RedisAccess;
import com.odaria.api.commons.message.MessageAction;
import com.odaria.api.commons.message.RedisMessage;
import com.odaria.api.spigot.OdariaAPISpigot;
import com.odaria.api.spigot.core.PartyProvider;
import org.redisson.api.RTopic;

public class InvitePlayerParty {
    public static void Action(String playerName, String playerToInvite) {
        OdariaAPISpigot.INSTANCE.getServer().getScheduler().runTaskAsynchronously(OdariaAPISpigot.INSTANCE, new Runnable() {
            @Override
            public void run() {
                PartyProvider partyProvider = new PartyProvider(playerName);
                Party party = partyProvider.getPlayerParty();

                if(party != null) {
                    if(party.getLeader().equalsIgnoreCase(playerName)) {
                        String json = new RedisMessage(MessageAction.INVITE_PLAYER_PARTY)
                                .setParam("playerName", playerName)
                                .setParam("playerToInvite", playerToInvite)
                                .toJSON();
                        RTopic channel = RedisAccess.INSTANCE.getRedissonClient().getTopic(RedisAccess.CHANNEL);
                        channel.publish(json);
                    }
                }
            }
        });
    }
}
