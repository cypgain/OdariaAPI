package com.odaria.api.spigot.friends;

import com.odaria.api.commons.core.Account;
import com.odaria.api.commons.core.FriendRequest;
import com.odaria.api.commons.data.management.redis.RedisAccess;
import com.odaria.api.commons.message.MessageAction;
import com.odaria.api.commons.message.RedisMessage;
import com.odaria.api.spigot.core.AccountProvider;
import org.bukkit.entity.Player;
import org.redisson.api.RTopic;

public class DenyFriendRequest {
    public static void Action(Player player, String fromPlayer) {
        AccountProvider accountProvider = new AccountProvider(player.getDisplayName());
        Account account = accountProvider.getAccountFromRedis();

        if(account.getFriendsRequest().contains(fromPlayer)) {
            /* Delete friend request */
            for(FriendRequest friendRequest : account.getFriendsRequest()) {
                if(friendRequest.getFromPlayer().equalsIgnoreCase(fromPlayer)) {
                    account.getFriendsRequest().remove(friendRequest);
                    break;
                }
            }

            /* Save to redis */
            accountProvider.sendAccountToRedis(account);

            player.sendMessage("Vous avez refusé la demande d'amis de " + fromPlayer);

            /* Send to bungee */
            String json = new RedisMessage(MessageAction.DENY_FRIEND_REQUEST)
                    .setParam("toPlayer", player.getDisplayName())
                    .setParam("fromPlayer", fromPlayer)
                    .toJSON();
            RTopic channel = RedisAccess.INSTANCE.getRedissonClient().getTopic(RedisAccess.CHANNEL);
            channel.publish(json);
        } else {
            player.sendMessage("Vous n'avez pas reçu de demande d'amis de " + fromPlayer);
        }
    }
}
