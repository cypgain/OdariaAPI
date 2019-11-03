package com.odaria.api.spigot.party;

import com.odaria.api.commons.core.Party;
import com.odaria.api.spigot.OdariaAPISpigot;
import com.odaria.api.spigot.core.PartyProvider;

public class RemoveParty {
    public static void Action(String playerName) {
        OdariaAPISpigot.INSTANCE.getServer().getScheduler().runTaskAsynchronously(OdariaAPISpigot.INSTANCE, new Runnable() {
            @Override
            public void run() {
                PartyProvider partyProvider = new PartyProvider(playerName);
                Party party = partyProvider.getPlayerParty();
                if(party != null) {
                    if(party.getLeader().equalsIgnoreCase(playerName)) {
                        partyProvider.removeParty(party);
                    }
                }
            }
        });
    }
}
