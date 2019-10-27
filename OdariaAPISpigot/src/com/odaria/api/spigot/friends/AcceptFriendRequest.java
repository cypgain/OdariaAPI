package com.odaria.api.spigot.friends;

import com.odaria.api.commons.core.Account;
import com.odaria.api.commons.core.FriendRequest;
import com.odaria.api.commons.data.management.redis.RedisAccess;
import com.odaria.api.commons.message.MessageAction;
import com.odaria.api.commons.message.RedisMessage;
import com.odaria.api.spigot.core.AccountProvider;
import org.bukkit.entity.Player;
import org.redisson.api.RTopic;

public class AcceptFriendRequest {
    public static final int MAX_FRIENDS = 45;

    public static void Action(Player player, String fromPlayer) {
        AccountProvider accountProvider = new AccountProvider(player.getDisplayName());
        Account account = accountProvider.getAccountFromRedis();

        if(account.getFriendsRequest().contains(fromPlayer)) {
            if(account.getFriends().size() < MAX_FRIENDS) {
                /* Delete friend request */
                for(FriendRequest friendRequest : account.getFriendsRequest()) {
                    if(friendRequest.getFromPlayer().equalsIgnoreCase(fromPlayer)) {
                        account.getFriendsRequest().remove(friendRequest);
                        break;
                    }
                }

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
}
