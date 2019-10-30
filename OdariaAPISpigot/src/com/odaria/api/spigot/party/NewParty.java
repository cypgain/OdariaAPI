package com.odaria.api.spigot.party;

import com.odaria.api.commons.core.Party;
import com.odaria.api.spigot.core.PartyProvider;

public class NewParty {
    public static void Action(String playerName) {
        PartyProvider partyProvider = new PartyProvider(playerName);
        Party party = partyProvider.getPlayerParty();
        if(party == null) {
            partyProvider.createParty();
        }
    }
}
