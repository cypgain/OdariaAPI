package com.odaria.api.bungee.listeners;

import com.odaria.api.bungee.core.AccountProvider;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.sql.SQLException;

public class ProxyQuitListener implements Listener {

    @EventHandler
    public void onPlayerQuitProxy(PlayerDisconnectEvent event) throws SQLException {
        final AccountProvider accountProvider = new AccountProvider(event.getPlayer());
        accountProvider.saveAccountToDatabase();
        accountProvider.removeAccountFromRedis();
    }

}
