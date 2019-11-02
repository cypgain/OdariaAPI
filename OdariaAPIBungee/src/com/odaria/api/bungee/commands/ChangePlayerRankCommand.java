package com.odaria.api.bungee.commands;

import com.odaria.api.bungee.OdariaAPIBungee;
import com.odaria.api.bungee.core.AccountProvider;
import com.odaria.api.bungee.data.management.sql.DatabaseAccess;
import com.odaria.api.bungee.data.management.sql.DatabaseManager;
import com.odaria.api.bungee.data.management.sql.DatabaseQuery;
import com.odaria.api.commons.core.Account;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.command.ConsoleCommandSender;

import java.sql.Connection;

public class ChangePlayerRankCommand extends Command {
    public ChangePlayerRankCommand() {
        super("changeplayerrank");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof ConsoleCommandSender) {
            if(args.length == 2) {
                try {
                    String playerName = args[0];
                    int rankId = Integer.parseInt(args[1]);

                    ProxiedPlayer player = OdariaAPIBungee.INSTANCE.getProxy().getPlayer(playerName);
                    if(player != null) {
                        AccountProvider accountProvider = new AccountProvider(player);
                        Account account = accountProvider.getAccountFromRedis();
                        account.setRankId(rankId);
                        accountProvider.sendAccountToRedis(account);
                    } else {
                        final Connection connection = DatabaseManager.ODARIA_MYSQL.getDatabaseAccess().getConnection();

                        new DatabaseQuery(connection)
                                .query("UPDATE users SET group_id=? WHERE username=?")
                                .setInt(1, rankId)
                                .setString(2, playerName)
                                .execute();
                    }

                    sender.sendMessage(new TextComponent("Vous avez changer le rang de "+ playerName));
                } catch(Exception e) {
                    sender.sendMessage(new TextComponent("Probleme lors de l'execution : " + e.getMessage()));
                }
            } else {
                sender.sendMessage(new TextComponent("Usage: /changeplayerrank <playerName> <rankId>"));
            }
        }
    }
}
