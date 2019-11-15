package com.odaria.api.bungee.core;

import com.odaria.api.bungee.data.management.exceptions.AccountNotFoundException;
import com.odaria.api.bungee.data.management.exceptions.GOOAccountNotFoundException;
import com.odaria.api.bungee.data.management.sql.DatabaseManager;
import com.odaria.api.bungee.data.management.sql.DatabaseQuery;
import com.odaria.api.commons.core.Account;
import com.odaria.api.commons.core.GOOAccount;
import com.odaria.api.commons.data.management.redis.RedisAccess;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GOOAccountProvider {
    public static final String REDIS_KEY = "gooaccount:";
    public static final GOOAccount DEFAULT_GOOACCOUNT = new GOOAccount(0, "", 0, 0, 0, 1, 0);

    private RedisAccess redisAccess;
    private ProxiedPlayer player;

    public GOOAccountProvider(ProxiedPlayer player) {
        this.player = player;
        this.redisAccess = RedisAccess.INSTANCE;
    }

    public GOOAccount getGOOAccount() throws GOOAccountNotFoundException {
        GOOAccount gooAccount = getGOOAccountFromRedis();
        if(gooAccount == null) {
            gooAccount = getGOOAccountFromDatabase();
            sendGOOAccountToRedis(gooAccount);
        }
        return gooAccount;
    }

    public void sendGOOAccountToRedis(GOOAccount gooAccount) {
        final RedissonClient redissonClient = redisAccess.getRedissonClient();
        final String key = REDIS_KEY + this.player.getDisplayName();
        final RBucket<GOOAccount> gooAccountRBucket = redissonClient.getBucket(key);

        gooAccountRBucket.set(gooAccount);
    }

    public GOOAccount getGOOAccountFromRedis() {
        final RedissonClient redissonClient = redisAccess.getRedissonClient();
        final String key = REDIS_KEY + this.player.getDisplayName();
        final RBucket<GOOAccount> gooAccountRBucket = redissonClient.getBucket(key);

        return gooAccountRBucket.get();
    }

    public void removeGOOAccountFromRedis() {
        final RedissonClient redissonClient = redisAccess.getRedissonClient();
        final String key = REDIS_KEY + this.player.getDisplayName();
        final RBucket<GOOAccount> gooAccountRBucket = redissonClient.getBucket(key);

        gooAccountRBucket.delete();
    }

    public GOOAccount getGOOAccountFromDatabase() throws GOOAccountNotFoundException {
        GOOAccount gooAccount = null;
        try {
            final Connection connection = DatabaseManager.ODARIA_MYSQL.getDatabaseAccess().getConnection();

            /* Load basics things goo account */
            final PreparedStatement ps = new DatabaseQuery(connection)
                    .query("SELECT * FROM goo_users WHERE username=?")
                    .setString(1, player.getDisplayName())
                    .executeAndGet();
            final ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                final int id = rs.getInt("id");
                final String username = rs.getString("username");
                final int kills = rs.getInt("kills");
                final int deaths = rs.getInt("deaths");
                final int exp = rs.getInt("exp");
                final int level = rs.getInt("level");
                final int box = rs.getInt("box");
                gooAccount = new GOOAccount(id, username, kills, deaths, exp, level, box);
            } else {
                gooAccount = createNewAccount();
            }

            ps.close();

            /* Load GOOKits */
            final PreparedStatement pss = new DatabaseQuery(connection)
                    .query("SELECT kit_id FROM goo_kits WHERE username=?;")
                    .setString(1, player.getDisplayName())
                    .executeAndGet();
            final ResultSet rss = pss.executeQuery();

            final List<Integer> gooKits = new ArrayList<>();
            while(rss.next()) {
                gooKits.add(rss.getInt("kit_id"));
            }
            gooAccount.setKits(gooKits);

            pss.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return gooAccount;
    }

    private GOOAccount createNewAccount() throws SQLException {
        final GOOAccount gooAccount = DEFAULT_GOOACCOUNT.clone();
        final Connection connection = DatabaseManager.ODARIA_MYSQL.getDatabaseAccess().getConnection();

        new DatabaseQuery(connection)
                .query("INSERT INTO goo_users (username, kills, deaths, exp, level, box) VALUES (?, 0, 0, 0, 1, 0)")
                .execute();

        final PreparedStatement ps = new DatabaseQuery(connection)
                .query("SELECT * FROM goo_users WHERE username=?")
                .setString(1, player.getDisplayName())
                .executeAndGet();
        final ResultSet rs = ps.executeQuery();

        if(rs.next()) {
            gooAccount.setId(rs.getInt("id"));
            gooAccount.setUsername(rs.getString("username"));
            gooAccount.setKills(rs.getInt("kills"));
            gooAccount.setDeaths(rs.getInt("deaths"));
            gooAccount.setExp(rs.getInt("exp"));
            gooAccount.setLevel(rs.getInt("level"));
            gooAccount.setBox(rs.getInt("box"));
        }

        ps.close();
        connection.close();

        return gooAccount;
    }

    public void saveGOOAccountToDatabase() throws SQLException {
        final GOOAccount gooAccount = getGOOAccountFromRedis();
        if(gooAccount != null) {
            final Connection connection = DatabaseManager.ODARIA_MYSQL.getDatabaseAccess().getConnection();

            /* Save goo account */
            new DatabaseQuery(connection)
                    .query("UPDATE goo_users SET kills=?, deaths=?, exp=?, level=?, box=? WHERE id=?")
                    .setInt(1, gooAccount.getKills())
                    .setInt(2, gooAccount.getDeaths())
                    .setInt(3, gooAccount.getExp())
                    .setInt(4, gooAccount.getLevel())
                    .setInt(5, gooAccount.getBox())
                    .setInt(6, gooAccount.getId())
                    .execute();

            /* Save Goo Kits */
            final PreparedStatement pss = new DatabaseQuery(connection)
                    .query("SELECT * FROM goo_kits WHERE username=?;")
                    .setString(1, player.getDisplayName())
                    .executeAndGet();
            final ResultSet rss = pss.executeQuery();

            final List<Integer> gooKits = new ArrayList<>();
            while(rss.next()) {
                gooKits.add(rss.getInt("friend"));
            }
            rss.close();

            final List<Integer> gooKitsToAdd = new ArrayList<>();
            for(Integer kit : gooAccount.getKits()) {
                if(!(gooKitsToAdd.contains(kit))) {
                    gooKitsToAdd.add(kit);
                }
            }

            for(Integer kit : gooKitsToAdd) {
                new DatabaseQuery(connection)
                        .query("INSERT INTO goo_kits (username, kit_id) VALUES(?, ?)")
                        .setString(1, player.getDisplayName())
                        .setInt(2, kit)
                        .execute();
            }

            connection.close();
        }
    }
}
