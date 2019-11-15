package com.odaria.api.spigot.commands;

import com.odaria.api.commons.core.Account;
import com.odaria.api.spigot.core.AccountProvider;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CheckOnlineCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(args.length == 1) {
            AccountProvider accountProvider = new AccountProvider(args[0]);
            Account account = accountProvider.getAccountFromRedis();
            String message = (account != null) ? "true" : "false";
            sender.sendMessage(message);
        } else {
            sender.sendMessage("Usage: /checkonline <player>");
        }
        return false;
    }
}
