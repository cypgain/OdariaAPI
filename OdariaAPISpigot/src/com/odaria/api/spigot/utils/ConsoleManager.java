package com.odaria.api.spigot.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class ConsoleManager {

    public static void debugMessage(String message) {
        Bukkit.getConsoleSender().sendMessage(ChatColor.LIGHT_PURPLE + message);
    }

    public static void successMessage(String message) {
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + message);
    }

    public static void warningMessage(String message) {
        Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + message);
    }

    public static void errorMessage(String message) {
        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + message);
    }

    public static void infoMessage(String message) {
        Bukkit.getConsoleSender().sendMessage(ChatColor.BLUE + message);
    }

}
