package com.odaria.api.spigot.senders;

import com.odaria.api.commons.core.Account;
import com.odaria.api.spigot.OdariaAPISpigot;
import com.odaria.api.spigot.core.AccountProvider;

public class ChangePlayerRank {
    public static void Action(String playerName, int rankId) {
        OdariaAPISpigot.INSTANCE.getServer().getScheduler().runTaskAsynchronously(OdariaAPISpigot.INSTANCE, new Runnable() {
            @Override
            public void run() {
                AccountProvider accountProvider = new AccountProvider(playerName);
                Account account = accountProvider.getAccountFromRedis();
                if(account != null) {
                    account.setRankId(rankId);
                    accountProvider.sendAccountToRedis(account);
                }
            }
        });
    }
}
