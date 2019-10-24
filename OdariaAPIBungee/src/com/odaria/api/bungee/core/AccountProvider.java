package com.odaria.api.bungee.core;

import com.odaria.api.bungee.data.management.exceptions.AccountNotFoundException;
import com.odaria.api.bungee.data.management.sql.DatabaseManager;
import com.odaria.api.bungee.data.management.sql.DatabaseQuery;
import com.odaria.api.bungee.utils.ConsoleManager;
import com.odaria.api.commons.core.Account;
import com.odaria.api.commons.data.management.redis.RedisAccess;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountProvider {
    public static final String REDIS_KEY = "account:";
    public static final Account DEFAULT_ACCOUNT = new Account(0, "", 0);

    private RedisAccess redisAccess;
    private ProxiedPlayer player;

    public AccountProvider(ProxiedPlayer player) {
        this.player = player;

        this.redisAccess = RedisAccess.INSTANCE;
    }

    public Account getAccount() throws AccountNotFoundException {
        Account account = getAccountFromRedis();
        if(account == null) {
            account = getAccountFromDatabase();
            sendAccountToRedis(account);
        }
        return account;
    }

    public void sendAccountToRedis(Account account) {
        final RedissonClient redissonClient = redisAccess.getRedissonClient();
        final String key = REDIS_KEY + this.player.getDisplayName();
        final RBucket<Account> accountRBucket = redissonClient.getBucket(key);

        accountRBucket.set(account);
    }

    public Account getAccountFromRedis() {
        final RedissonClient redissonClient = redisAccess.getRedissonClient();
        final String key = REDIS_KEY + this.player.getDisplayName();
        final RBucket<Account> accountRBucket = redissonClient.getBucket(key);

        return accountRBucket.get();
    }

    public void removeAccountFromRedis() {
        final RedissonClient redissonClient = redisAccess.getRedissonClient();
        final String key = REDIS_KEY + this.player.getDisplayName();
        final RBucket<Account> accountRBucket = redissonClient.getBucket(key);

        accountRBucket.delete();
    }

    private Account getAccountFromDatabase() throws AccountNotFoundException {
        Account account = null;
        try {
            final Connection connection = DatabaseManager.ODARIA_MYSQL.getDatabaseAccess().getConnection();

            final PreparedStatement ps = new DatabaseQuery(connection)
                    .query("SELECT * FROM users WHERE username=?")
                    .setString(1, player.getDisplayName())
                    .executeAndGet();
            final ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                final int id = rs.getInt("id");
                final String username = rs.getString("username");
                final int coins = rs.getInt("coins");
                account = new Account(id, username, coins);


            } else {
                account = createNewAccount();
            }

            ps.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return account;
    }

    public void saveAccountToDatabase() throws SQLException {
        final Account account = getAccountFromRedis();
        final Connection connection = DatabaseManager.ODARIA_MYSQL.getDatabaseAccess().getConnection();

        new DatabaseQuery(connection)
                .query("UPDATE users SET coins=? WHERE id=?")
                .setInt(1, account.getCoins())
                .setInt(2, account.getId())
                .execute();

        connection.close();
    }

    private Account createNewAccount() throws SQLException {
        final Account account = DEFAULT_ACCOUNT.clone();
        final Connection connection = DatabaseManager.ODARIA_MYSQL.getDatabaseAccess().getConnection();

        new DatabaseQuery(connection)
                .query("INSERT INTO users (username, coins) VALUES (?, ?)")
                .setString(1, player.getDisplayName())
                .setInt(2, 0)
                .execute();

        final PreparedStatement ps = new DatabaseQuery(connection)
                .query("SELECT id, username, coins FROM users WHERE username=?")
                .setString(1, player.getDisplayName())
                .executeAndGet();
        final ResultSet rs = ps.executeQuery();

        if(rs.next()) {
            account.setId(rs.getInt("id"));
            account.setUsername(rs.getString("username"));
            account.setCoins(rs.getInt("coins"));
        }

        ps.close();
        connection.close();

        return account;
    }

}
