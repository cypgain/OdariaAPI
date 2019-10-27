package com.odaria.api.spigot.friends;

import com.odaria.api.spigot.OdariaAPISpigot;
import com.odaria.api.spigot.anvilgui.AnvilGUI;
import org.bukkit.entity.Player;

public class NewFriendRequest {
    public static void Action(Player player) {
        OdariaAPISpigot.INSTANCE.getServer().getScheduler().runTaskAsynchronously(OdariaAPISpigot.INSTANCE, new Runnable() {
            @Override
            public void run() {
                new AnvilGUI.Builder()
                        .onComplete((p, text) -> {
                            new SendFriendRequest(player.getDisplayName(), text).sendFriendRequest();
                            return AnvilGUI.Response.close();
                        })
                        .text("Nom du joueur")
                        .plugin(OdariaAPISpigot.INSTANCE)
                        .open(player);
            }
        });
    }
}
