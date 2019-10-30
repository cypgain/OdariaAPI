package com.odaria.api.spigot.gui.party;

import com.odaria.api.spigot.friends.OpenFriendsGUI;
import com.odaria.api.spigot.guimanager.GUI;
import com.odaria.api.spigot.guimanager.GUIManager;
import com.odaria.api.spigot.party.NewParty;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class NewPartyGUI extends GUI {
    public NewPartyGUI(Player player) {
        super(GUIManager.INSTANCE, player, false, 54, "Creation d'un groupe");
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
                case WOOD_DOOR:
                    this.closeAndRemove();
                    break;
                case STAINED_CLAY:
                    this.closeAndRemove();
                    if(item.getData().getData() == 5) {
                        NewParty.Action(player.getDisplayName());
                        OpenFriendsGUI.Action(player);
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
        getInventory().setItem(8, createGuiItem(Material.WOOD_DOOR, "Fermer", "§aFermer la fenetre actuelle"));
        getInventory().setItem(22, createGuiItem(Material.STAINED_CLAY, "§aNouveau groupe", 5, "Permet de créer un nouveau groupe"));
    }
}
