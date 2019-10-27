package com.odaria.api.spigot.coins;

import com.odaria.api.commons.core.Account;
import com.odaria.api.spigot.core.AccountProvider;
import org.bukkit.entity.Player;

public class GetCoins {
    public static int Action(Player player) {
        AccountProvider accountProvider = new AccountProvider(player.getDisplayName());
        Account account = accountProvider.getAccountFromRedis();

        return account.getCoins();
    }
}
