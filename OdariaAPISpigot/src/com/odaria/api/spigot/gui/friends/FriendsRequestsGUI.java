package com.odaria.api.spigot.gui.friends;

import com.odaria.api.commons.core.FriendRequest;
import com.odaria.api.spigot.core.AccountProvider;
import com.odaria.api.spigot.guimanager.GUI;
import com.odaria.api.spigot.guimanager.GUIManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class FriendsRequestsGUI extends GUI {
    private List<FriendRequest> requests;

    public FriendsRequestsGUI(Player player) {
        super(GUIManager.INSTANCE, player, false, 54, "Demandes d'amis");
        AccountProvider accountProvider = new AccountProvider(player.getPlayerListName());
        requests = accountProvider.getAccountFromRedis().getFriendsRequest();
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
                    //OpenFriendsGUISpigotSender.Action(player);
                    break;
                case PAPER:
                    this.closeAndRemove();
                    new FriendRequestAcceptGUI(player, item.getItemMeta().getDisplayName()).openInventory();
                    break;
                case WOOD_DOOR:
                    this.closeAndRemove();
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
        getInventory().setItem(8, createGuiItem(Material.WOOD_DOOR, "Fermer  ", "§aFermer la fenetre actuelle"));

        int x = 9;

        for(int i = 0; i < requests.size(); i++) {
            if(x < 44) {
                FriendRequest request = requests.get(i);
                getInventory().setItem(x, createGuiItem(Material.PAPER, request.getFromPlayer(), "§aCliquer pour effectuer une action"));
            }
            x++;
        }

    }
}
