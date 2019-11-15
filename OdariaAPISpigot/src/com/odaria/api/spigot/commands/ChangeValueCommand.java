package com.odaria.api.spigot.commands;

import com.odaria.api.commons.data.management.redis.RedisAccess;
import com.odaria.api.commons.message.MessageAction;
import com.odaria.api.commons.message.RedisMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.redisson.api.RTopic;

public class ChangeValueCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(args.length == 3) {
            String arg = args[0];
            String playerName = args[1];
            String value = args[2];

            String json = new RedisMessage(MessageAction.CHANGE_VALUE_ACCOUNT)
                    .setParam("arg", arg)
                    .setParam("playerName", playerName)
                    .setParam("value", value)
                    .toJSON();
            RTopic channel = RedisAccess.INSTANCE.getRedissonClient().getTopic(RedisAccess.CHANNEL);
            channel.publish(json);
        }
        return false;
    }
}
