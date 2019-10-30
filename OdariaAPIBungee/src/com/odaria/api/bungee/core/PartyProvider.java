package com.odaria.api.bungee.core;

import com.odaria.api.commons.core.Account;
import com.odaria.api.commons.core.Party;
import com.odaria.api.commons.data.management.redis.RedisAccess;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;

public class PartyProvider {
    public static final String REDIS_KEY = "party:";

    private RedisAccess redisAccess;
    private ProxiedPlayer player;

    public PartyProvider(ProxiedPlayer player) {
        this.player = player;
        redisAccess = RedisAccess.INSTANCE;
    }

    public Party getPlayerParty() {
        Account account = new AccountProvider(player).getAccountFromRedis();
        String leader = account.getPartyLeader();

        final RedissonClient redissonClient = redisAccess.getRedissonClient();
        final String key = REDIS_KEY + leader;
        final RBucket<Party> partyRBucket = redissonClient.getBucket(key);

        return partyRBucket.get();
    }

    public void savePlayerParty(Party party) {
        Account account = new AccountProvider(player).getAccountFromRedis();
        String leader = account.getPartyLeader();

        final RedissonClient redissonClient = redisAccess.getRedissonClient();
        final String key = REDIS_KEY + leader;
        final RBucket<Party> partyRBucket = redissonClient.getBucket(key);

        partyRBucket.set(party);
    }

    public void removeParty(Party party) {
        final RedissonClient redissonClient = redisAccess.getRedissonClient();
        final String key = REDIS_KEY + party.getLeader();
        final RBucket<Party> partyRBucket = redissonClient.getBucket(key);

        partyRBucket.delete();
    }
}
