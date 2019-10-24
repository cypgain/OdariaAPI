package com.odaria.api.bungee.utils;

import com.odaria.api.bungee.OdariaAPIBungee;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;

public class ConsoleManager {

    public static void debugMessage(String message) {
        BungeeCord.getInstance().getConsole().sendMessage(new TextComponent(ChatColor.LIGHT_PURPLE + message));
    }

    public static void successMessage(String message) {
        BungeeCord.getInstance().getConsole().sendMessage(new TextComponent(ChatColor.GREEN + message));
    }

    public static void warningMessage(String message) {
        BungeeCord.getInstance().getConsole().sendMessage(new TextComponent(ChatColor.YELLOW + message));
    }

    public static void errorMessage(String message) {
        BungeeCord.getInstance().getConsole().sendMessage(new TextComponent(ChatColor.RED + message));
    }

    public static void infoMessage(String message) {
        BungeeCord.getInstance().getConsole().sendMessage(new TextComponent(ChatColor.BLUE + message));
    }

}
