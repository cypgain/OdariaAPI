package com.odaria.api.spigot.gui.friends;

import com.odaria.api.spigot.friends.OpenFriendsGUI;
import com.odaria.api.spigot.friends.RemoveFriend;
import com.odaria.api.spigot.guimanager.GUI;
import com.odaria.api.spigot.guimanager.GUIManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class FriendActionGUI extends GUI {
    private String actionPlayerName;

    public FriendActionGUI(Player player, String actionPlayerName) {
        super(GUIManager.INSTANCE, player, false, 54, actionPlayerName);
        this.actionPlayerName = actionPlayerName;
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
                    OpenFriendsGUI.Action(player);
                    break;
                case WOOD_DOOR:
                    this.closeAndRemove();
                    break;
                case STAINED_CLAY:
                    this.closeAndRemove();
                    if(item.getData().getData() == 14) {
                        RemoveFriend.Action(player, event.getClickedInventory().getName());
                    }
                    break;
                case RAW_FISH:
                    //InvitePlayerPartySpigotSender.Action(getPlayer().getDisplayName(), event.getInventory().getName());
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
        getInventory().setItem(0, createGuiItem(Material.ARROW, "Retour", "§aRetour à la liste des amis"));
        getInventory().setItem(8, createGuiItem(Material.WOOD_DOOR, "Fermer", "§aFermer la fenetre actuelle"));
        getInventory().setItem(22, createGuiItem(Material.STAINED_CLAY, "§cSupprimer cet ami", 14));
        getInventory().setItem(31, createGuiItem(Material.RAW_FISH, "§aInviter cet ami dans le groupe", 3));
    }
}

