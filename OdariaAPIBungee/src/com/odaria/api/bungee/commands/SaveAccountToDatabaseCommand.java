package com.odaria.api.bungee.commands;

import com.odaria.api.bungee.data.management.exceptions.AccountNotFoundException;
import com.odaria.api.bungee.listeners.redis.account.LoadAccountFromDatabaseListener;
import com.odaria.api.bungee.listeners.redis.account.SaveAccountToDatabaseListener;
import com.odaria.api.commons.message.MessageAction;
import com.odaria.api.commons.message.RedisMessage;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.command.ConsoleCommandSender;

import java.sql.SQLException;

public class SaveAccountToDatabaseCommand extends Command {
    public SaveAccountToDatabaseCommand() {
        super("saveaccounttodatabase");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof ConsoleCommandSender) {
            if(args.length != 1) {
                try {
                    RedisMessage redisMessage = new RedisMessage(MessageAction.SAVE_ACCOUNT_TO_DATABASE);
                    redisMessage.setParam("playerName", args[0]);
                    SaveAccountToDatabaseListener.Action(redisMessage);
                    sender.sendMessage(new TextComponent("Account " + args[0] + " saved"));
                } catch (AccountNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                sender.sendMessage(new TextComponent("Usage : /saveaccounttodatabase <username>"));
            }
        }
    }
}
