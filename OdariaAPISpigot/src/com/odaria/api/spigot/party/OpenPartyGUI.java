package com.odaria.api.spigot.party;

import com.odaria.api.commons.core.Party;
import com.odaria.api.spigot.OdariaAPISpigot;
import com.odaria.api.spigot.core.PartyProvider;
import com.odaria.api.spigot.gui.party.NewPartyGUI;
import com.odaria.api.spigot.gui.party.PartyGUI;
import org.bukkit.entity.Player;

public class OpenPartyGUI {
    public static void Action(Player player) {
        OdariaAPISpigot.INSTANCE.getServer().getScheduler().runTaskAsynchronously(OdariaAPISpigot.INSTANCE, new Runnable() {
            @Override
            public void run() {
                PartyProvider partyProvider = new PartyProvider(player.getDisplayName());
                Party party = partyProvider.getPlayerParty();

                if(party != null) {
                    new PartyGUI(player, party).openInventory();
                } else {
                    new NewPartyGUI(player).openInventory();
                }
            }
        });
    }
}
