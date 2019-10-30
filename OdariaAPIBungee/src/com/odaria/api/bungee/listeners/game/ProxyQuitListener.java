package com.odaria.api.bungee.listeners.game;

import com.odaria.api.bungee.OdariaAPIBungee;
import com.odaria.api.bungee.core.AccountProvider;
import com.odaria.api.bungee.core.PartyProvider;
import com.odaria.api.commons.core.Party;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.sql.SQLException;

public class ProxyQuitListener implements Listener {

    @EventHandler
    public void onPlayerQuitProxy(PlayerDisconnectEvent event) throws SQLException {
        BungeeCord.getInstance().getScheduler().runAsync(OdariaAPIBungee.INSTANCE, () ->
        {
            final ProxiedPlayer player = event.getPlayer();

            final PartyProvider partyProvider = new PartyProvider(player);
            final Party party = partyProvider.getPlayerParty();
            if(party.getLeader().equalsIgnoreCase(player.getDisplayName())) {
                partyProvider.removeParty(party);
            } else {
                party.getPlayers().remove(player.getDisplayName());
                partyProvider.savePlayerParty(party);
            }

            final AccountProvider accountProvider = new AccountProvider(player);

            try {
                accountProvider.saveAccountToDatabase();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            accountProvider.removeAccountFromRedis();

        });
    }

}
