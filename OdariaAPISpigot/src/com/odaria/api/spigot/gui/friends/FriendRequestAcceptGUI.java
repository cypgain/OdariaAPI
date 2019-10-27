package com.odaria.api.spigot.gui.friends;

import com.odaria.api.spigot.friends.AcceptFriendRequest;
import com.odaria.api.spigot.friends.DenyFriendRequest;
import com.odaria.api.spigot.guimanager.GUI;
import com.odaria.api.spigot.guimanager.GUIManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class FriendRequestAcceptGUI extends GUI {

    private String acceptPlayerName;

    public FriendRequestAcceptGUI(Player player, String acceptPlayerName) {
        super(GUIManager.INSTANCE, player, false, 54, acceptPlayerName);
        this.acceptPlayerName = acceptPlayerName;
    }

    @Override
    public void onOtherInventoryClick(InventoryClickEvent event) {

    }

    @Override
    public void onInventoryClick(InventoryClickEvent event) {
        if(event.getCurrentItem() != null) {
            Player player = (Player) event.getWhoClicked();
            Material mat = event.getCurrentItem().getType();
            ItemStack item = event.getCurrentItem();
            switch (mat) {
                case ARROW:
                    this.closeAndRemove();
                    new FriendsRequestsGUI(player).openInventory();
                    break;
                case WOOD_DOOR:
                    this.closeAndRemove();
                    break;
                case STAINED_CLAY:
                    this.closeAndRemove();
                    if(item.getData().getData() == 5) {
                        AcceptFriendRequest.Action(player, event.getClickedInventory().getName());
                    } else {
                        DenyFriendRequest.Action(player, event.getClickedInventory().getName());
                    }
                    break;
            }
        }
        event.setCancelled(true);
    }

    @Override
    public void onInventoryClose() {

    }

    @Override
    public void onGuiRemove() {

    }

    @Override
    public void initializeItems() {
        getInventory().setItem(0, createGuiItem(Material.ARROW, "Retour", "§aRetour à la liste des demandes d'amis"));
        getInventory().setItem(8, createGuiItem(Material.WOOD_DOOR, "Fermer", "§aFermer la fenetre actuelle"));
        getInventory().setItem(22, createGuiItem(Material.STAINED_CLAY, "§aAccepter", 5, "Accepter la demande d'amis"));
        getInventory().setItem(31, createGuiItem(Material.STAINED_CLAY, "§CRefuser", 14, "Refuser la demande d'amis"));
    }

}
