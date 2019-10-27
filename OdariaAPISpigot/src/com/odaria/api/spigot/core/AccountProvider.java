package com.odaria.api.spigot.core;

import com.odaria.api.commons.core.Account;
import com.odaria.api.commons.data.management.redis.RedisAccess;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;

public class AccountProvider {
    public static final String REDIS_KEY = "account:";

    private RedisAccess redisAccess;
    private String player;

    public AccountProvider(String player) {
        this.player = player;
        redisAccess = RedisAccess.INSTANCE;
    }

    public void sendAccountToRedis(Account account) {
        final RedissonClient redissonClient = redisAccess.getRedissonClient();
        final String key = REDIS_KEY + player;
        final RBucket<Account> accountRBucket = redissonClient.getBucket(key);

        accountRBucket.set(account);
    }

    public Account getAccountFromRedis() {
        final RedissonClient redissonClient = redisAccess.getRedissonClient();
        final String key = REDIS_KEY + player;
        final RBucket<Account> accountRBucket = redissonClient.getBucket(key);

        return accountRBucket.get();
    }
}
