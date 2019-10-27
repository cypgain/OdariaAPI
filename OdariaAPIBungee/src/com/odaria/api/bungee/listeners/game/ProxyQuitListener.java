package com.odaria.api.bungee.listeners.game;

import com.odaria.api.bungee.OdariaAPIBungee;
import com.odaria.api.bungee.core.AccountProvider;
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
            ProxiedPlayer player = event.getPlayer();
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
