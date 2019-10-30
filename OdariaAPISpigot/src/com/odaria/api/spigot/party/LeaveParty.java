package com.odaria.api.spigot.party;

import com.odaria.api.commons.core.Party;
import com.odaria.api.spigot.core.PartyProvider;
import org.bukkit.entity.Player;

public class LeaveParty {
    public static void Action(Player player) {
        PartyProvider partyProvider = new PartyProvider(player.getDisplayName());
        Party party = partyProvider.getPlayerParty();

        if(party != null) {
            if(!(party.getLeader().equalsIgnoreCase(player.getDisplayName()))) {
                party.getPlayers().remove(player.getDisplayName());
            }
        }
    }
}
