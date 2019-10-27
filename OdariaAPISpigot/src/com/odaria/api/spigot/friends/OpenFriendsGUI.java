package com.odaria.api.spigot.friends;

import com.odaria.api.commons.core.Account;
import com.odaria.api.commons.data.management.redis.RedisAccess;
import com.odaria.api.spigot.OdariaAPISpigot;
import com.odaria.api.spigot.core.AccountProvider;
import com.odaria.api.spigot.gui.friends.FriendsGUI;
import org.bukkit.entity.Player;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;

import java.util.HashMap;
import java.util.Map;

public class OpenFriendsGUI {
    public static final String REDIS_KEY = "account:";

    public static void Action(Player player) {
        OdariaAPISpigot.INSTANCE.getServer().getScheduler().runTaskAsynchronously(OdariaAPISpigot.INSTANCE, new Runnable() {
            @Override
            public void run() {
                Map<String, String> friends = new HashMap<>();

                AccountProvider accountProvider = new AccountProvider(player.getDisplayName());
                Account account = accountProvider.getAccountFromRedis();

                RedisAccess redisAccess = RedisAccess.INSTANCE;

                for(String friend : account.getFriends()) {
                    Account currentFriendAccount = getAccountFromRedis(redisAccess, friend);
                    if(currentFriendAccount != null) {
                        friends.put(friend, "En ligne");
                    } else {
                        friends.put(friend, "Hors ligne");
                    }
                }

                new FriendsGUI(player, friends).openInventory();
            }
        });
    }

    public static Account getAccountFromRedis(RedisAccess redisAccess, String playerName) {
        final RedissonClient redissonClient = redisAccess.getRedissonClient();
        final String key = REDIS_KEY + playerName;
        final RBucket<Account> accountRBucket = redissonClient.getBucket(key);

        return accountRBucket.get();
    }
}
