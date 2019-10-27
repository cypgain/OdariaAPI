package com.odaria.api.spigot.guimanager;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

public class GUIManager implements Listener {

    public static GUIManager INSTANCE;

    HashMap<Player, GUI> guis;

    public GUIManager() {
        INSTANCE = this;
        guis = new HashMap<>();
    }

    public boolean exist(Player player)
    {
        return guis.containsKey(player);
    }

    public GUI getGui(Player player)
    {
        return guis.get(player);
    }

    public void put(Player player, GUI gui)
    {
        guis.put(player, gui);
    }

    public void remove(Player player) {
        if(guis.containsKey(player)) {
            guis.remove(player);
        }
    }

    public HashMap<Player, GUI> getGUIs()
    {
        return guis;
    }

    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent e)
    {
        if(e.getWhoClicked() instanceof Player)
        {
            if(e.getCurrentItem() != null){
                Player player = (Player) e.getWhoClicked();
                if(exist(player))
                {
                    Inventory inv = getGui(player).getInventory();
                    if(inv == null)
                    {
                        e.setCancelled(true);
                    }

                    if(e.getClickedInventory().getName() != null && getGui(player).getInventory().getName().equals(e.getClickedInventory().getName()))
                    {
                        getGui(player).onInventoryClick(e);
                    } else {
                        if(getGui(player).isAllowOutsideClick())
                        {
                            getGui(player).onOtherInventoryClick(e);
                        } else {
                            e.setCancelled(true);
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onInventoryCloseEvent(InventoryCloseEvent e)
    {
        Player player =  (Player) e.getPlayer();

        if(exist(player) && getGui(player).getInventory().getName().equalsIgnoreCase(e.getInventory().getName()))
        {
            getGui(player).onInventoryClose();
            player.updateInventory();
            // guis.remove(player);
        }
    }
}

