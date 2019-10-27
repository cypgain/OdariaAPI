package com.odaria.api.spigot.friends;

import com.odaria.api.commons.core.Account;
import com.odaria.api.commons.data.management.redis.RedisAccess;
import com.odaria.api.commons.message.MessageAction;
import com.odaria.api.commons.message.RedisMessage;
import com.odaria.api.spigot.OdariaAPISpigot;
import com.odaria.api.spigot.core.AccountProvider;
import org.bukkit.entity.Player;
import org.redisson.api.RTopic;

public class AcceptFriendRequest {
    public static final int MAX_FRIENDS = 45;

    public static void Action(Player player, String fromPlayer) {
        OdariaAPISpigot.INSTANCE.getServer().getScheduler().runTaskAsynchronously(OdariaAPISpigot.INSTANCE, new Runnable() {
            @Override
            public void run() {
                AccountProvider accountProvider = new AccountProvider(player.getDisplayName());
                Account account = accountProvider.getAccountFromRedis();

                if(account.getFriendsRequest().contains(fromPlayer)) {
                    if(account.getFriends().size() < MAX_FRIENDS) {
                        /* Delete friend request */
                        account.getFriendsRequest().remove(fromPlayer);

                        /* Add friend */
                        account.getFriends().add(fromPlayer);

                        /* Save to redis */
                        accountProvider.sendAccountToRedis(account);

                        player.sendMessage("Vous avez accepté la demande d'amis de " + fromPlayer);

                        /* Send to bungee */
                        String json = new RedisMessage(MessageAction.ACCEPT_FRIEND_REQUEST)
                                .setParam("toPlayer", player.getDisplayName())
                                .setParam("fromPlayer", fromPlayer)
                                .toJSON();
                        RTopic channel = RedisAccess.INSTANCE.getRedissonClient().getTopic(RedisAccess.CHANNEL);
                        channel.publish(json);
                    } else {
                        player.sendMessage("Vous avez trop d'amis");
                    }
                } else {
                    player.sendMessage("Vous n'avez pas reçu de demande d'amis de " + fromPlayer);
                }
            }
        });
    }
}
