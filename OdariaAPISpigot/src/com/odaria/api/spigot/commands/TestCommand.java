package com.odaria.api.spigot.commands;

import com.odaria.api.spigot.friends.OpenFriendsGUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.command.CommandExecutor;

public class TestCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;

            OpenFriendsGUI.Action(player);
        }
        return true;
    }
}
