package com.odaria.api.spigot.friends;

import com.odaria.api.commons.core.Account;
import com.odaria.api.commons.data.management.redis.RedisAccess;
import com.odaria.api.commons.message.MessageAction;
import com.odaria.api.commons.message.RedisMessage;
import com.odaria.api.spigot.OdariaAPISpigot;
import com.odaria.api.spigot.core.AccountProvider;
import org.bukkit.entity.Player;
import org.redisson.api.RTopic;

public class RemoveFriend {
    public static void Action(Player player, String playerToRemove) {
        OdariaAPISpigot.INSTANCE.getServer().getScheduler().runTaskAsynchronously(OdariaAPISpigot.INSTANCE, new Runnable() {
            @Override
            public void run() {
                AccountProvider accountProvider = new AccountProvider(player.getDisplayName());
                Account account = accountProvider.getAccountFromRedis();

                if(account != null) {
                    if(account.getFriends().contains(playerToRemove)) {
                        account.getFriends().remove(playerToRemove);
                        player.sendMessage("Vous avez supprimer de vos amis " + playerToRemove);
                        accountProvider.sendAccountToRedis(account);

                        /* Send to bungee */
                        String json = new RedisMessage(MessageAction.REMOVE_FRIEND)
                                .setParam("player", player.getDisplayName())
                                .setParam("playerToRemove", playerToRemove)
                                .toJSON();
                        RTopic channel = RedisAccess.INSTANCE.getRedissonClient().getTopic(RedisAccess.CHANNEL);
                        channel.publish(json);
                    } else {
                        player.sendMessage("Vous n'etes pas amis avec " + playerToRemove);
                    }
                }
            }
        });
    }
}
