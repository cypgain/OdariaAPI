package com.odaria.api.spigot.coins;

import com.odaria.api.commons.core.Account;
import com.odaria.api.spigot.OdariaAPISpigot;
import com.odaria.api.spigot.core.AccountProvider;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class SetCoins {
    public static void Action(Player player, int amount) {
        Bukkit.getScheduler().runTaskAsynchronously(OdariaAPISpigot.INSTANCE, new Runnable() {
            @Override
            public void run() {
                AccountProvider accountProvider = new AccountProvider(player.getDisplayName());
                Account account = accountProvider.getAccountFromRedis();
                account.setCoins(amount);
                accountProvider.sendAccountToRedis(account);
            }
        });
    }
}
