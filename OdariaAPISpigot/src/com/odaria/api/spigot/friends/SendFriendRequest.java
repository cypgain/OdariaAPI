package com.odaria.api.spigot.friends;

import com.odaria.api.commons.core.Account;
import com.odaria.api.commons.core.FriendRequest;
import com.odaria.api.commons.data.management.redis.RedisAccess;
import com.odaria.api.commons.message.MessageAction;
import com.odaria.api.commons.message.RedisMessage;
import com.odaria.api.spigot.core.AccountProvider;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.redisson.api.RTopic;

public class SendFriendRequest {

    public static final int MAX_FRIENDS_REQUESTS = 30;
    public static final String REDIS_KEY = "account:";

    private RedisAccess redisAccess;
    private String fromPlayer;
    private String toPlayer;

    public SendFriendRequest(String fromPlayer, String toPlayer) {
        this.fromPlayer = fromPlayer;
        this.toPlayer = toPlayer;
        redisAccess = RedisAccess.INSTANCE;
    }

    public void sendFriendRequest() {
        Player player = Bukkit.getPlayer(fromPlayer);

        AccountProvider toPlayerAccountProvider = new AccountProvider(toPlayer);
        Account toPlayerAccount = toPlayerAccountProvider.getAccountFromRedis();
        if(toPlayerAccount != null) {
            AccountProvider fromPlayerAccountProvider = new AccountProvider(fromPlayer);
            Account fromPlayerAccount = fromPlayerAccountProvider.getAccountFromRedis();
            if(!(fromPlayerAccount.getFriends().contains(toPlayer))) {
                if(!(fromPlayerAccount.getFriendsRequest().contains(toPlayerAccount)) && !(toPlayerAccount.getFriendsRequest().contains(fromPlayerAccount))) {
                    if(toPlayerAccount.getFriendsRequest().size() < MAX_FRIENDS_REQUESTS) {
                        player.sendMessage("Vous avez envoyé une demande d'amis à " + toPlayer);
                        sendFriendRequestToBungee(toPlayer);
                        toPlayerAccount.getFriendsRequest().add(new FriendRequest(fromPlayer, toPlayer));
                        toPlayerAccountProvider.sendAccountToRedis(toPlayerAccount);
                    } else {
                        player.sendMessage(toPlayer + " possède trop de demande d'amis");
                    }
                } else {
                    player.sendMessage("Vous avez déjà envoyé une demande d'amis à " + toPlayer);
                }
            } else {
                player.sendMessage("Vous êtes déjà amis avec " + toPlayer);
            }
        } else {
            player.sendMessage(toPlayer + " n'est pas connecté");
        }
    }

    private void sendFriendRequestToBungee(String toPlayer) {
        String json = new RedisMessage(MessageAction.SEND_FRIEND_REQUEST)
                .setParam("toPlayer", toPlayer)
                .toJSON();
        RTopic channel = RedisAccess.INSTANCE.getRedissonClient().getTopic(RedisAccess.CHANNEL);
        channel.publish(json);
    }
}
