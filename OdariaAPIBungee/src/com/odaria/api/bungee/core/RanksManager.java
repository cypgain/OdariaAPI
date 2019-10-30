package com.odaria.api.bungee.core;

import com.odaria.api.bungee.OdariaAPIBungee;
import com.odaria.api.bungee.data.management.sql.DatabaseManager;
import com.odaria.api.bungee.data.management.sql.DatabaseQuery;
import com.odaria.api.commons.core.Rank;
import com.odaria.api.commons.data.management.redis.RedisAccess;
import com.odaria.api.commons.ranks.Ranks;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RanksManager {

    public static final String REDIS_KEY = "rank:";

    public static RanksManager INSTANCE;

    private RedisAccess redisAccess;

    public RanksManager() {
        this.redisAccess = RedisAccess.INSTANCE;
        updateRanks();
        INSTANCE = this;
    }

    public void updateRanks() {
        OdariaAPIBungee.INSTANCE.getProxy().getScheduler().runAsync(OdariaAPIBungee.INSTANCE, new Runnable() {
            @Override
            public void run() {
                try {
                    final Connection connection = DatabaseManager.ODARIA_MYSQL.getDatabaseAccess().getConnection();
                    for(Ranks rank : Ranks.values()) {
                        Rank redisRank = new Rank(rank.getGroupId());
                        final PreparedStatement ps = new DatabaseQuery(connection)
                                .query("SELECT * FROM permissions_mc WHERE group_id=?;")
                                .setInt(1, rank.getGroupId())
                                .executeAndGet();
                        final ResultSet rs = ps.executeQuery();

                        while(rs.next()) {
                            redisRank.getPermissions().add(rs.getString("permission"));
                        }

                        sendRankToRedis(redisRank);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void sendRankToRedis(Rank rank) {
        final RedissonClient redissonClient = redisAccess.getRedissonClient();
        final String key = REDIS_KEY + rank.getGroupId();
        final RBucket<Rank> rankRBucket = redissonClient.getBucket(key);

        rankRBucket.set(rank);
    }

}
