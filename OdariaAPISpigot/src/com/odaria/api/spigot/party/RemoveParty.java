package com.odaria.api.spigot.party;

import com.odaria.api.commons.core.Party;
import com.odaria.api.spigot.core.PartyProvider;

public class RemoveParty {
    public static void Action(String playerName) {
        PartyProvider partyProvider = new PartyProvider(playerName);
        Party party = partyProvider.getPlayerParty();
        if(party != null) {
            if(party.getLeader().equalsIgnoreCase(playerName)) {
                partyProvider.removeParty(party);
            }
        }
    }
}
