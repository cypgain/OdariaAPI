package com.odaria.api.spigot.core;

import com.odaria.api.commons.core.Account;
import com.odaria.api.commons.core.GOOAccount;
import com.odaria.api.commons.data.management.redis.RedisAccess;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;

public class GOOAccountProvider {
    public static final String REDIS_KEY = "gooaccount:";

    private RedisAccess redisAccess;
    private String player;

    public GOOAccountProvider(String player) {
        this.player = player;
        redisAccess = RedisAccess.INSTANCE;
    }

    public void sendGOOAccountToRedis(GOOAccount gooAccount) {
        final RedissonClient redissonClient = redisAccess.getRedissonClient();
        final String key = REDIS_KEY + player;
        final RBucket<GOOAccount> gooAccountRBucket = redissonClient.getBucket(key);

        gooAccountRBucket.set(gooAccount);
    }

    public GOOAccount getGOOAccountFromRedis() {
        final RedissonClient redissonClient = redisAccess.getRedissonClient();
        final String key = REDIS_KEY + player;
        final RBucket<GOOAccount> gooAccountRBucket = redissonClient.getBucket(key);

        return gooAccountRBucket.get();
    }

}
