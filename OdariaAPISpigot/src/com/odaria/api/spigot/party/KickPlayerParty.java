package com.odaria.api.spigot.party;

import com.odaria.api.commons.core.Party;
import com.odaria.api.spigot.core.PartyProvider;

public class KickPlayerParty {
    public static void Action(String playerName, String playerToKick) {
        PartyProvider partyProvider = new PartyProvider(playerName);
        Party party = partyProvider.getPlayerParty();

        if(party != null) {
            if(party.getLeader().equalsIgnoreCase(playerName)) {
                party.getPlayers().remove(playerToKick);
            }
        }
    }
}
