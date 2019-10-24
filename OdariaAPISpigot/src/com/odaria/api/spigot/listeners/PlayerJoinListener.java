package com.odaria.api.spigot.listeners;

import com.odaria.api.commons.core.Account;
import com.odaria.api.commons.data.management.redis.RedisAccess;
import com.odaria.api.spigot.OdariaAPISpigot;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Bukkit.getScheduler().runTaskAsynchronously(OdariaAPISpigot.INSTANCE, () -> {
            final RedisAccess redisAccess = RedisAccess.INSTANCE;
            final RedissonClient redissonClient = redisAccess.getRedissonClient();
            final RBucket<Account> accountRBucket = redissonClient.getBucket("account:" + event.getPlayer().getDisplayName());
            final Account account = accountRBucket.get();
        });
    }

}
