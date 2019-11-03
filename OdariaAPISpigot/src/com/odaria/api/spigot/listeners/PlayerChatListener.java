package com.odaria.api.spigot.listeners;

import com.odaria.api.commons.core.Account;
import com.odaria.api.commons.ranks.Ranks;
import com.odaria.api.spigot.core.AccountProvider;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChatListener implements Listener {

    @EventHandler
    public void onPlayerChatEvent(AsyncPlayerChatEvent e) {
        Player player = e.getPlayer();
        Account account = new AccountProvider(player.getDisplayName()).getAccountFromRedis();
        Ranks rank = Ranks.getById(account.getRankId());
        e.setFormat(rank.getName() + " " + player.getDisplayName() + "§7 > §f" + rank.getMessageFormat() + e.getMessage());
    }

}
