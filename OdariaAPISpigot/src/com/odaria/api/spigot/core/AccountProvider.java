package com.odaria.api.spigot.core;

import com.odaria.api.commons.core.Account;
import com.odaria.api.commons.core.Rank;
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

    public Rank getRankFromRedis(int rankId) {
        final RedissonClient redissonClient = redisAccess.getRedissonClient();
        final String key = "rank:" + rankId;
        final RBucket<Rank> rankRBucket = redissonClient.getBucket(key);

        return rankRBucket.get();
    }

    public boolean hasPermission(String permission) {
        Account account = getAccountFromRedis();
        if(account != null) {
            int rankId = account.getRankId();
            Rank rank = getRankFromRedis(rankId);
            return rank.getPermissions().contains(permission);
        }
        return false;
    }

    public void addOdabox(int amount) {
        Account account = getAccountFromRedis();
        if(account != null) {
            account.setOdaBox(account.getOdaBox() + amount);
            sendAccountToRedis(account);
        }
    }

    public void removeOdabox(int amount) {
        Account account = getAccountFromRedis();
        if(account != null) {
            account.setOdaBox(account.getOdaBox() - amount);
            sendAccountToRedis(account);
        }
    }

    public int getOdabox() {
        Account account = getAccountFromRedis();
        if(account != null) {
            return account.getOdaBox();
        }
        return 0;
    }
}
