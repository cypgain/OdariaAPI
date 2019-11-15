package com.odaria.api.bungee.listeners.game;

import com.odaria.api.bungee.OdariaAPIBungee;
import com.odaria.api.bungee.core.AccountProvider;
import com.odaria.api.bungee.core.GOOAccountProvider;
import com.odaria.api.bungee.data.management.exceptions.AccountNotFoundException;
import com.odaria.api.bungee.data.management.exceptions.GOOAccountNotFoundException;
import com.odaria.api.commons.core.Account;
import com.odaria.api.commons.core.GOOAccount;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ProxyJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoinProxy(PostLoginEvent event) {
        final ProxiedPlayer player = event.getPlayer();

        BungeeCord.getInstance().getScheduler().runAsync(OdariaAPIBungee.INSTANCE, () ->
        {
            try {
                final AccountProvider accountProvider = new AccountProvider(player);
                final Account account = accountProvider.getAccount();

            } catch (AccountNotFoundException e) {
                e.printStackTrace();
                player.disconnect(new TextComponent("Votre compte n'a pas été créé ou trouvé"));
            }

            try {
                final GOOAccountProvider gooAccountProvider = new GOOAccountProvider(player);
                final GOOAccount gooAccount = gooAccountProvider.getGOOAccount();

            } catch (GOOAccountNotFoundException e) {
                e.printStackTrace();
                player.disconnect(new TextComponent("Votre compte n'a pas été créé ou trouvé"));
            }
        });
    }

}
