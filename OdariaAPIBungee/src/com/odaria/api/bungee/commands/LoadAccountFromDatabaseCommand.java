package com.odaria.api.bungee.commands;

import com.odaria.api.bungee.OdariaAPIBungee;
import com.odaria.api.bungee.data.management.exceptions.AccountNotFoundException;
import com.odaria.api.bungee.listeners.redis.account.LoadAccountFromDatabaseListener;
import com.odaria.api.commons.message.MessageAction;
import com.odaria.api.commons.message.RedisMessage;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.command.ConsoleCommandSender;

public class LoadAccountFromDatabaseCommand extends Command {
    public LoadAccountFromDatabaseCommand() {
        super("loadaccountfromdatabase");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        OdariaAPIBungee.INSTANCE.getProxy().getScheduler().runAsync(OdariaAPIBungee.INSTANCE, new Runnable() {
            @Override
            public void run() {
                if(sender instanceof ConsoleCommandSender) {
                    if(args.length != 1) {
                        try {
                            RedisMessage redisMessage = new RedisMessage(MessageAction.LOAD_ACCOUNT_FROM_DATABASE);
                            redisMessage.setParam("playerName", args[0]);
                            LoadAccountFromDatabaseListener.Action(redisMessage);
                            sender.sendMessage(new TextComponent("Account " + args[0] + " loaded"));
                        } catch (AccountNotFoundException e) {
                            e.printStackTrace();
                        }
                    } else {
                        sender.sendMessage(new TextComponent("Usage : /loadaccountfromdatabase <username>"));
                    }
                }
            }
        });
    }
}
